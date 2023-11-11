package com.ynan.service;

import com.google.common.collect.Lists;
import com.ynan.dictionary.StockOperandTypeEnum;
import com.ynan.dto.*;
import com.ynan.event.StockChangeEvent;
import com.ynan.executor.PendingStockExecutor;
import com.ynan.executor.PickedStockExecutor;
import com.ynan.executor.ShelfAvailableStockExecutor;
import com.ynan.executor.StockExecutor;
import com.ynan.interceptor.StockOperationInterceptor;
import com.ynan.model.BusinessContext;
import com.ynan.operation.PlusOperation;
import com.ynan.operation.ReduceOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author yuannan
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExecutorGatewayServiceImpl implements ExecutorGatewayService, ApplicationContextAware, InitializingBean {

	//	private final StockAllocatedMapper stockAllocatedMapper;
	//	private final SortingLockedStockMapper sortingLockedStockMapper;
	//	private final ShelfLockedStockMapper shelfLockedStockMapper;
	//	private final PickLockedStockMapper pickLockedStockMapper;
	//	private final StockProdLineMapper stockProdLineMapper;
	//	private final StockTransactionLogMapper stockTransactionLogMapper;
	//	private final DataSyncGssService dataSyncGssService;
	private final List<StockOperationInterceptor> stockOperationInterceptors;
	private Map<StockOperandTypeEnum, StockExecutor> stockExecutorMap;
	private ApplicationContext ac = null;

	@Resource
	private String thorEventPublisher;

	@Override
	public void doStockOperate(StockOperation stockOperation, BusinessContext businessContext) {

		StockExecutor reduceExecutor;
		StockExecutor plusExecutor;
		boolean success;

		if (stockOperation.getFromOperand() == null && stockOperation.getToOperand() == null) {
			throw new RuntimeException("xxx");
		}

		setRealPosition2Context(stockOperation, businessContext);

		doIntercept(stockOperation, businessContext);

		if (stockOperation.getFromOperand() != null) {
			reduceExecutor = getExecutor(stockOperation.getFromOperand());
			ReduceOperation reduceOperation = generateReduceOperation(stockOperation, businessContext);
			success = reduceExecutor.reduce(reduceOperation, businessContext);
			if (!success) {
				log.error("ExecutorGatewayServiceImpl doStockOperate call reduceExecutor->reduce failed. " +
						"reduceExecutor: {}, reduceOperation: {}, businessContext: {}.");
				throw new RuntimeException("StockExceptionCode.STOCK_UPDATE_FAIL");
			}
		}

		if (stockOperation.getToOperand() != null) {
			plusExecutor = getExecutor(stockOperation.getToOperand());
			PlusOperation plusOperation = generatePlusOperation(stockOperation, businessContext);
			log.info("ExecutorGatewayServiceImpl doStockOperate call plusExecutor->plus start. " +
					"plusExecutor: {}, plusOperation: {}, businessContext: {}.");
			success = plusExecutor.plus(plusOperation, businessContext);
			if (!success) {
				log.error("ExecutorGatewayServiceImpl doStockOperate call plusExecutor->plus failed. " +
						"plusExecutor: {}, plusOperation: {}, businessContext: {}.");
				throw new RuntimeException("StockExceptionCode.STOCK_UPDATE_FAIL");
			}
		}
		if (!logAlreadySaved(businessContext)) {
			Long logId = saveLog(stockOperation, businessContext);
			businessContext.getExtParams().put("BusinessContextParamEnum.TRANSACTION_LOG_ID.code", logId);
		}

		//同步触发库存变更时间
		syncTriggerStockChangeEvent(stockOperation, businessContext);
	}

	private boolean logAlreadySaved(BusinessContext businessContext) {
		return businessContext.getExtParams().containsKey("BusinessContextParamEnum.TRANSACTION_LOG_INSERT_FLAG.code");
	}

	private Long saveLog(StockOperation stockOperation, BusinessContext businessContext) {
		/*
		StockTransactionLog stockTransactionLog = StockTransactionLogUtils.newInitializeLog();
		stockTransactionLog.setSkuId(stockOperation.getSkuId());
		if (null != stockOperation.getFrom()) {
			Position frmPos = (Position) businessContext.getExtParams()
					.get(BusinessContextParamEnum.REAL_FROM_POSITION.code);
			stockTransactionLog.setFrmLotId(stockOperation.getLotId());
			stockTransactionLog.setFrmLocId(frmPos.getLocId());
			stockTransactionLog.setFrmLpnNo(frmPos.getLpnNo());
			stockTransactionLog.setFrmQty(stockOperation.getQty());
		}
		if (null != stockOperation.getTo()) {
			Position toPos = (Position) businessContext.getExtParams()
					.get(BusinessContextParamEnum.REAL_TO_POSITION.code);
			stockTransactionLog.setToLotId(stockOperation.getLotId());
			stockTransactionLog.setToLocId(toPos.getLocId());
			stockTransactionLog.setToLpnNo(toPos.getLpnNo());
			stockTransactionLog.setToQty(stockOperation.getQty());
		}
		fillContextInfo(stockTransactionLog, businessContext);
		boolean success = stockTransactionLogMapper.insertSelective(stockTransactionLog) > 0;
		if (!success) {
			throw new RuntimeException(StockExceptionCode.LOG_SAVE_FAIL);
		}
		if (businessContext.isCtrlNotifyGss()) {
			//GSS不影响主流程
			dataSyncGssService
					.saveGssSyncTaskByTransactionLogId(stockTransactionLog.getId(), businessContext.getWarehouseId());
		}
		return stockTransactionLog.getId();
		 */
		return null;
	}

	private void doIntercept(StockOperation stockOperation, BusinessContext businessContext) {
		doFinalIntercept(stockOperation, businessContext, stockOperationInterceptors);
	}

	private void doFinalIntercept(StockOperation stockOperation, BusinessContext businessContext,
			List<StockOperationInterceptor> stockOperationInterceptors) {
		for (StockOperationInterceptor stockOperationInterceptor : stockOperationInterceptors) {
			stockOperationInterceptor.doIntercept(stockOperation, businessContext);
		}
	}

	private void setRealPosition2Context(StockOperation stockOperation, BusinessContext businessContext) {
		if (stockOperation.getFrom() != null) {
			Position fromPosition = getRealPositionOrSetSku(stockOperation.getFrom(), stockOperation,
					stockOperation.getFromOperand());
			businessContext.putParam("BusinessContextParamEnum.REAL_FROM_POSITION.code", fromPosition);
		}
		if (stockOperation.getTo() != null) {
			Position toPosition = getRealPositionOrSetSku(stockOperation.getTo(), stockOperation,
					stockOperation.getToOperand());
			businessContext.putParam("BusinessContextParamEnum.REAL_TO_POSITION.code", toPosition);
		}
	}

	private void syncTriggerStockChangeEvent(StockOperation stockOperation, BusinessContext businessContext) {
		StockChangeEvent ignoreExceptionStockChangeEvent = new StockChangeEvent();
		ignoreExceptionStockChangeEvent.setStockOperation(stockOperation);
		ignoreExceptionStockChangeEvent.setBusinessContext(businessContext);
		ignoreExceptionStockChangeEvent.setTraceId(UUID.randomUUID().toString().replace("-", Strings.EMPTY));

		// 同步发送库存变更消息
		// thorEventPublisher.syncPublish(ignoreExceptionStockChangeEvent);

		// 异步发送库存变更消息
		// 不影响主业务
		// thorEventPublisher.asyncPublish(new AsyncStockChangeEvent(new Date(), ignoreExceptionStockChangeEvent),
		// businessContext.getWarehouseId());
	}

	private ReduceOperation generateReduceOperation(StockOperation stockOperation, BusinessContext businessContext) {
		ReduceOperation reduceOperation = new ReduceOperation();
		if (stockOperation.getFrom() instanceof IdentityPosition) {
			IdentityPosition identityPosition = (IdentityPosition) stockOperation.getFrom();
			reduceOperation.setFromAllocate(true);
			reduceOperation.setAllocateId(identityPosition.getId());
		} else {
			Position fromPosition = (Position) businessContext.getExtParams()
					.get("BusinessContextParamEnum.REAL_FROM_POSITION.code");
			StockLocator locator = new StockLocator()
					.setSkuId(stockOperation.getSkuId())
					.setLotId(stockOperation.getLotId());
			if (fromPosition != null) {
				locator.setLocId(fromPosition.getLocId());
				if (StringUtils.isNotEmpty(fromPosition.getLpnNo())) {
					locator.setLpnNo(fromPosition.getLpnNo());
				} else {
					locator.setLpnNo(StockLocator.LPN_NULL_VALUE);
				}
			}
			reduceOperation.setLocator(locator);
		}
		reduceOperation.setReduceNum(stockOperation.getQty());
		reduceOperation.setVerifyNum(stockOperation.getVerifyQty());
		return reduceOperation;
	}

	private PlusOperation generatePlusOperation(StockOperation stockOperation, BusinessContext businessContext) {
		PlusOperation plusOperation = new PlusOperation();
		Position toPosition = (Position) businessContext.getExtParams()
				.get("BusinessContextParamEnum.REAL_TO_POSITION.code");
		StockLocator locator = new StockLocator();
		locator.setSkuId(stockOperation.getSkuId());
		locator.setLotId(stockOperation.getLotId());
		if (toPosition != null) {
			locator.setLocId(toPosition.getLocId());
			if (StringUtils.isNotEmpty(toPosition.getLpnNo())) {
				locator.setLpnNo(toPosition.getLpnNo());
			} else {
				locator.setLpnNo(StockLocator.LPN_NULL_VALUE);
			}
		}
		plusOperation.setLocator(locator);
		plusOperation.setPlusNum(stockOperation.getQty());
		return plusOperation;
	}

	private StockExecutor getExecutor(StockOperandTypeEnum operandType) {
		StockExecutor stockExecutor = stockExecutorMap.get(operandType);
		Assert.notNull(stockExecutor, "StockExceptionCode.STOCK_EXECUTOR_NOT_EXIST");
		return stockExecutor;
	}

	private Position getRealPositionOrSetSku(StockPosition stockPosition, StockOperation stockOperation,
			StockOperandTypeEnum stockOperandTypeEnum) {
		if (stockPosition instanceof Position) {
			return (Position) stockPosition;
		}
		IdentityPosition identityPosition = (IdentityPosition) stockPosition;
		if (StockOperandTypeEnum.SORTED_LOCK_OPERAND == stockOperandTypeEnum) {
			//			SortingLockedStock sortingLockedStock = sortingLockedStockMapper.selectByLockId(identityPosition.getId());
			//			stockOperation.setSkuId(sortingLockedStock.getSkuId());
			//			stockOperation.setLotId(sortingLockedStock.getLotId());
			//			return new Position(sortingLockedStock.getLocId(), sortingLockedStock.getLpnNo());
		} else if (StockOperandTypeEnum.SHELF_ALLOCATE_OPERAND == stockOperandTypeEnum) {
			//			StockAllocated stockAllocated = stockAllocatedMapper.getById(identityPosition.getId());
			//			stockOperation.setSkuId(stockAllocated.getSkuId());
			//			stockOperation.setLotId(stockAllocated.getLotId());
			//			return new Position(stockAllocated.getLocId(), stockAllocated.getLpnNo());
		} else if (StockOperandTypeEnum.SHELF_LOCK_OPERAND == stockOperandTypeEnum) {
			//			ShelfLockedStock shelfLockedStock = shelfLockedStockMapper.selectByLockId(identityPosition.getId());
			//			stockOperation.setSkuId(shelfLockedStock.getSkuId());
			//			stockOperation.setLotId(shelfLockedStock.getLotId());
			//			return new Position(shelfLockedStock.getLocId(), shelfLockedStock.getLpnNo());
		} else if (StockOperandTypeEnum.PICK_LOCKED_OPERAND == stockOperandTypeEnum) {
			//			PickLockedStock pickLockedStock = pickLockedStockMapper.selectByLockId(identityPosition.getId());
			//			stockOperation.setSkuId(pickLockedStock.getSkuId());
			//			stockOperation.setLotId(pickLockedStock.getLotId());
			//			return new Position(pickLockedStock.getLocId(), pickLockedStock.getLpnNo());
		} else if (StockOperandTypeEnum.PROD_LINE_OPERAND == stockOperandTypeEnum) {
			//			StockProdLine stockProdLine = stockProdLineMapper.queryById(identityPosition.getId());
			//			stockOperation.setSkuId(stockProdLine.getSkuId());
			//			stockOperation.setLotId(stockProdLine.getLotId());
			//			return new Position(stockProdLine.getLocId(), stockProdLine.getLpnNo());
		} else {
			throw new RuntimeException("StockExceptionCode.NOT_SUPPORTED_POSITION_TYPE");
		}
		return null;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ac = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		stockExecutorMap = new HashMap<>();
		for (ExecutorRoute route : routeList) {
			StockExecutor stockExecutor = ac.getBean(route.executorClass);
			stockExecutorMap.put(route.operationType, stockExecutor);
		}
	}


	@Data
	static class ExecutorRoute {

		public ExecutorRoute(StockOperandTypeEnum stockOperationType,
				Class<? extends StockExecutor> executorClass) {
			this.operationType = stockOperationType;
			this.executorClass = executorClass;
		}

		private StockOperandTypeEnum operationType;
		private Class<? extends StockExecutor> executorClass;

		public static ExecutorRoute of(StockOperandTypeEnum stockOperationTypeEnum,
				Class<? extends StockExecutor> executorClass) {
			return new ExecutorRoute(stockOperationTypeEnum, executorClass);
		}
	}

	private static final List<ExecutorRoute> routeList = Lists.newArrayList(
			ExecutorRoute.of(StockOperandTypeEnum.PENDING_OPERAND, PendingStockExecutor.class),
			ExecutorRoute.of(StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND, ShelfAvailableStockExecutor.class),
			//			ExecutorRoute.of(StockOperandTypeEnum.SHELF_HOLD_OPERAND, ShelfHoldStockExecutor.class),
			ExecutorRoute.of(StockOperandTypeEnum.PICKED_OPERAND, PickedStockExecutor.class)
			//			ExecutorRoute.of(StockOperandTypeEnum.SORTED_OPERAND, SortingStockExecutor.class),
			//			ExecutorRoute.of(StockOperandTypeEnum.SHELF_ALLOCATE_OPERAND, ShelfAllocateStockExecutor.class),
			//			ExecutorRoute.of(StockOperandTypeEnum.PICKED_HOLD_OPERAND, PickedHoldStockExecutor.class),
			//			ExecutorRoute.of(StockOperandTypeEnum.FEEDING_OPERAND, FeedingStockExecutor.class),
			//			ExecutorRoute.of(StockOperandTypeEnum.PRODUCTION_OPERAND, ProductionStockExecutor.class),
			//			ExecutorRoute.of(StockOperandTypeEnum.SORTED_LOCK_OPERAND, SortingLockedStockExecutor.class),
			//			ExecutorRoute.of(StockOperandTypeEnum.SHELF_LOCK_OPERAND, ShelfLockedStockExecutor.class),
			//			ExecutorRoute.of(StockOperandTypeEnum.PICK_LOCKED_OPERAND, PickLockedStockExecutor.class),
			//			ExecutorRoute.of(StockOperandTypeEnum.PROD_LINE_OPERAND, ProdLineStockExecutor.class)
	);


}
