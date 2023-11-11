package com.ynan.service;

import com.google.common.collect.Lists;
import com.ynan.dictionary.*;
import com.ynan.dto.*;
import com.ynan.model.BusinessContext;
import com.ynan.request.*;
import com.ynan.response.AllocateStockResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author yuannan
 */
@Slf4j
@Service
public class StockOperationServiceImpl implements StockOperationService {

	@Autowired
	private ExecutorGatewayService executorGatewayService;

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public boolean addStock(AddStockRequest addStockRequest, BusinessContext businessContext) {
		// 校验入参
		businessContext = businessContext.copy();
		validateBusinessContextBasic(businessContext);
		//		validateAddStockRequest(addStockRequest);
		Assert.notNull(businessContext.getDocId(), "StockExceptionCode.BUSINESS_CONTEXT_ERROR");
		Assert.notNull(businessContext.getDocLineId(), "StockExceptionCode.BUSINESS_CONTEXT_ERROR");
		// 追加库存
		StockOperation addStockOperation = buildAddStockOperation(addStockRequest, businessContext);
		executorGatewayService.doStockOperate(addStockOperation, businessContext);
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public boolean reduceStock(ReduceStockRequest reduceStockRequest, BusinessContext businessContext) {
		businessContext = businessContext.copy();
		//校验
		validateBusinessContextBasic(businessContext);
		//扣减
		StockOperation reduceStockOperation = buildReduceStockOperation(reduceStockRequest, businessContext);
		executorGatewayService.doStockOperate(reduceStockOperation, businessContext);
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public boolean transferStock(TransferStockRequest transferStockRequest, BusinessContext businessContext) {
		businessContext = businessContext.copy();
		//校验
		validateBusinessContextBasic(businessContext);
		// validateTransferRequest(transferStockRequest);
		//扣减源库存
		StockOperation transferStockOperation = buildTransferStockOperation(transferStockRequest, businessContext);
		executorGatewayService.doStockOperate(transferStockOperation, businessContext);
		return true;
	}

	@Override
	public boolean transferStockByLock(TransferStockByAllocateRequest request,
			BusinessContext businessContext) {
		return false;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public AllocateStockResult allocateStock(AllocateStockRequest allocateRequest, BusinessContext businessContext) {
		businessContext = businessContext.copy();
		//校验
		validateBusinessContextBasic(businessContext);
		// validateAllocateRequest(allocateRequest, businessContext);
		//操作库存
		StockOperation stockOperation = buildAllocateStockOperation(allocateRequest, businessContext);
		// StockOperationResult.init();
		executorGatewayService.doStockOperate(stockOperation, businessContext);
		//		if (StockOperationResult.getResultData() == null) {
		//			throw new RuntimeException("StockExceptionCode.ALLOCATE_FAIL");
		//		}
		//		Long allocateId = (Long) StockOperationResult.getResultData();
		AllocateStockResult result = AllocateStockResult.success(null)
				.setLocator(allocateRequest.getLocator()).setAllocateNum(allocateRequest.getLockNum());
		return result;
	}

	@Override
	public boolean releaseStock(ReleaseStockRequest releaseStockRequest, BusinessContext businessContext) {
		return false;
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public boolean commonTransferStock(CommonTransferStockRequest request, BusinessContext businessContext) {
		businessContext = businessContext.copy();
		//校验
		validateBusinessContextBasic(businessContext);
		// validateCommonTransferRequest(request);
		//初始化
		//		StockOperationResult.init();
		//构造请求
		StockOperation stockOperation = buildCommonTransferOperation(request);
		executorGatewayService.doStockOperate(stockOperation, businessContext);
		return true;
	}

	@Override
	public boolean commonAddStock(CommonAddStockRequest request, BusinessContext businessContext) {
		return false;
	}

	@Override
	public boolean commonReduceStock(CommonReduceStockRequest request, BusinessContext businessContext) {
		return false;
	}

	private void validateBusinessContextBasic(BusinessContext businessContext) {
		Assert.notNull(businessContext.getUserId(), "StockExceptionCode.STOCK_OPERATOR_NULL");
		Assert.notNull(businessContext.getProcessType(), "StockExceptionCode.CONTEXT_PROCESS_TYPE_NULL");
		Assert.notNull(businessContext.getWarehouseId(), "StockExceptionCode.CONTEXT_WAREHOUSE_NULL");
	}

	private StockOperation buildAddStockOperation(AddStockRequest addStockRequest,
			BusinessContext businessContext) {
		StockProcessTypeEnum processType = businessContext.getProcessType();
		Assert.notNull(processType.to, "StockExceptionCode.PROCESS_TYPE_ERROR");
		StockLocator locator = addStockRequest.getLocator();

		StockOperation stockOperation = new StockOperation();
		stockOperation.setToOperand(processType.to);
		stockOperation.setTo(
				new Position()
						.setLocId(locator.getLocId())
						.setLpnNo(locator.getLpnNo())
		);
		stockOperation.setSkuId(locator.getSkuId());
		stockOperation.setLotId(locator.getLotId());
		stockOperation.setQty(addStockRequest.getAddNum());
		return stockOperation;
	}

	private StockOperation buildReduceStockOperation(ReduceStockRequest reduceStockRequest,
			BusinessContext businessContext) {
		StockProcessTypeEnum processType = businessContext.getProcessType();
		Assert.notNull(processType.from, "StockExceptionCode.PROCESS_TYPE_ERROR");
		StockLocator locator = reduceStockRequest.getLocator();

		StockOperation stockOperation = new StockOperation();
		stockOperation.setFromOperand(processType.from);
		stockOperation.setFrom(
				new Position()
						.setLocId(locator.getLocId())
						.setLpnNo(locator.getLpnNo())
		);
		stockOperation.setSkuId(locator.getSkuId());
		stockOperation.setLotId(locator.getLotId());
		stockOperation.setQty(reduceStockRequest.getReduceNum());
		return stockOperation;
	}

	private StockOperation buildTransferStockOperation(TransferStockRequest transferStockRequest,
			BusinessContext businessContext) {
		StockOperation stockOperation = new StockOperation();
		stockOperation.setFromOperand(getFromOperandNullNot(businessContext));
		stockOperation.setToOperand(getToOperandNullNot(businessContext));
		stockOperation.setQty(transferStockRequest.getTransferNum());
		stockOperation.setFrom(transferStockRequest.getFrom());
		stockOperation.setTo(transferStockRequest.getTo());
		stockOperation.setSkuId(transferStockRequest.getSkuId());
		stockOperation.setLotId(transferStockRequest.getLotId());
		return stockOperation;
	}

	private StockOperandTypeEnum getFromOperandNullNot(BusinessContext businessContext) {
		StockProcessTypeEnum processType = businessContext.getProcessType();
		StockOperandTypeEnum from = StockOperandTypeEnum
				.fromProcessType(processType, StockSingleOperationTypeEnum.REDUCE);
		Assert.notNull(from, "StockExceptionCode.PROCESS_TYPE_ERROR");
		return from;
	}

	private StockOperandTypeEnum getToOperandNullNot(BusinessContext businessContext) {
		StockProcessTypeEnum processType = businessContext.getProcessType();
		StockOperandTypeEnum to = StockOperandTypeEnum.fromProcessType(processType, StockSingleOperationTypeEnum.PLUS);
		Assert.notNull(to, "StockExceptionCode.PROCESS_TYPE_ERROR");
		return to;
	}

	private StockOperation buildAllocateStockOperation(AllocateStockRequest allocateStockRequest,
			BusinessContext businessContext) {
		StockLocator locator = allocateStockRequest.getLocator();

		StockOperation stockOperation = new StockOperation();
		stockOperation.setFromOperand(getFromOperandNullNot(businessContext));
		stockOperation.setToOperand(getToOperandNullNot(businessContext));
		stockOperation.setSkuId(locator.getSkuId());
		stockOperation.setLotId(locator.getLotId());
		//分配库存的 from和to是同一个位置
		stockOperation.setFrom(
				new Position()
						.setLocId(locator.getLocId())
						.setLpnNo(locator.getLpnNo())
		);
		stockOperation.setTo(
				new Position()
						.setLocId(locator.getLocId())
						.setLpnNo(locator.getLpnNo())
		);
		stockOperation.setQty(allocateStockRequest.getLockNum());
		stockOperation.setVerifyQty(allocateStockRequest.getVerifyNum());
		return stockOperation;
	}

	private StockOperation buildCommonTransferOperation(CommonTransferStockRequest request) {
		StockOperation stockOperation = new StockOperation();
		stockOperation.setTo(request.getTo());
		stockOperation.setQty(request.getTransferNum());
		stockOperation.setSkuId(request.getSkuId());
		stockOperation.setLotId(request.getLotId());
		if (request.isFromAllocate()) {
			stockOperation.setFrom(IdentityPosition.of(request.getAllocateId()));
		} else {
			stockOperation.setFrom(request.getFrom());
		}
		Pair<StockOperandTypeEnum, StockOperandTypeEnum> fromAndToOperand = getFromAndToOperandNullNot(request);
		stockOperation.setFromOperand(fromAndToOperand.getLeft());
		stockOperation.setToOperand(fromAndToOperand.getRight());
		return stockOperation;
	}

	private Pair<StockOperandTypeEnum, StockOperandTypeEnum> getFromAndToOperandNullNot(
			CommonTransferStockRequest request) {
		List<Long> locIds = Lists.newArrayList(request.getTo().getLocId(), request.getFrom().getLocId());
		//		Map<Long, LocationTypeEnum> locTypeMap = locationService.multiGetLocType(locIds);
		Map<Long, LocationTypeEnum> locTypeMap = new HashMap<>();
		CounterPosition from = request.getFrom();
		CounterPosition to = request.getTo();
		LocationTypeEnum fromLocType = locTypeMap.get(from.getLocId());
		StockCounterTypeEnum fromCountType = from.getCounterType();
		LocationTypeEnum toLocType = locTypeMap.get(to.getLocId());
		StockCounterTypeEnum toCountType = to.getCounterType();

		StockOperandTypeEnum fromOperandType = getOperandTypeByLocTypeAndCountType(fromLocType, fromCountType);
		StockOperandTypeEnum toOperandType = getOperandTypeByLocTypeAndCountType(toLocType, toCountType);
		Assert.notNull(fromOperandType, "StockExceptionCode.CAN_NOT_LOCATE_OPERAND_TYPE");
		Assert.notNull(toOperandType, "StockExceptionCode.CAN_NOT_LOCATE_OPERAND_TYPE");
		return Pair.of(fromOperandType, toOperandType);
	}

	private StockOperandTypeEnum getOperandTypeByLocTypeAndCountType(LocationTypeEnum locType,
			StockCounterTypeEnum countType) {
		return StockOperandTypeEnum.fromLocationType(locType, countType);
	}
}