package com.ynan.executor;

import com.ynan.dto.StockLocator;
import com.ynan.entity.PendingStock;
import com.ynan.mappers.PendingStockMapper;
import com.ynan.model.BusinessContext;
import com.ynan.operation.PlusOperation;
import com.ynan.operation.ReduceOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

/**
 * @author yuannan
 */
@Component
@Slf4j
public class PendingStockExecutor implements StockExecutor {

	@Autowired
	private PendingStockMapper pendingStockMapper;

	@Override
	public boolean reduce(ReduceOperation reduceOperation, BusinessContext businessContext) {

		StockLocator locator = reduceOperation.getLocator();

		boolean success;
		if (reduceOperation.getVerifyNum() == null) {
			success = pendingStockMapper.reduce(locator, reduceOperation.getReduceNum()) > 0;
		} else {
			success = pendingStockMapper
					.verifyAndReduce(locator, reduceOperation.getReduceNum(), reduceOperation.getVerifyNum()) > 0;
		}

		if (!success) {
			log.error("[StockOperationError] fail to reduce pending qty" +
					", operation:{}, businessContext:{}", reduceOperation, businessContext);
			PendingStock exist = pendingStockMapper.selectByLocator(locator);
			if (exist == null) {
				log.error("[StockOperationError] because there has no such stock record");
			} else {
				log.error("[StockOperationError] current pending qty:{}," +
						" reduceQty:{}", exist.getQtyPending(), reduceOperation.getReduceNum());
			}
		} else {
			log.info("[StockOperationSuccess] success reduce pending qty," +
					" operation:{}, businessContext:{}", reduceOperation, businessContext);
		}

		return success;
	}

	@Override
	public boolean plus(PlusOperation plusOperation, BusinessContext businessContext) {
		StockLocator locator = plusOperation.getLocator();
		if (isStockExist(locator)) {
			boolean success = plusInternal(plusOperation);
			if (!success) {
				log.error("[StockOperationError] fail to plus pending qty, and exist record " +
						"operation:{}, businessContext:{}", plusOperation, businessContext);
			} else {
				log.info("[StockOperationSuccess] success plus pending qty, " +
						"operation:{}, businessContext:{}", plusOperation, businessContext);
			}
			return success;
		} else {
			PendingStock pendingStock = buildNewRecord(plusOperation, businessContext);
			try {
				boolean success = pendingStockMapper.insertSelective(pendingStock) > 0;
				log.info("[StockOperationSuccess] success plus pending qty, " +
						"operation:{}, businessContext:{}", plusOperation, businessContext);
				return success;
			} catch (DuplicateKeyException ex) {
				log.warn("重复记录插入冲突", ex);
				boolean success = plusInternal(plusOperation);
				if (!success) {
					log.error("[StockOperationError] fail to plus pending qty, RepeatedFlag" +
							"operation:{}, businessContext:{}", plusOperation, businessContext);
				} else {
					log.info("[StockOperationSuccess] success plus pending qty, RepeatedFlag" +
							"operation:{}, businessContext:{}", plusOperation, businessContext);
				}
				return success;
			}
		}
	}

	private boolean isStockExist(StockLocator locator) {
		PendingStock stock = pendingStockMapper.selectByLocator(locator);
		return stock != null;
	}

	private boolean plusInternal(PlusOperation plusOperation) {
		return pendingStockMapper.plus(plusOperation.getLocator(), plusOperation.getPlusNum()) > 0;
	}

	private PendingStock buildNewRecord(PlusOperation plusOperation, BusinessContext businessContext) {
		StockLocator locator = plusOperation.getLocator();
		PendingStock pendingStock = new PendingStock().setSkuId(locator.getSkuId())
				.setLotId(locator.getLotId()).setLpnNo(locator.getLpnNo())
				.setQtyPending(plusOperation.getPlusNum()).setLocId(locator.getLocId())
				.setWarehouseId(businessContext.getWarehouseId());
		//		pendingStock.setId(SnowflakeIdUtil.generateId());
		//		pendingStock.setIsDeleted(YesNo.NO.asLong());
		//		pendingStock.setCreateBy(businessContext.getUserId());
		//		pendingStock.setUpdateBy(businessContext.getUserId());
		//		pendingStock.setVersion(Constants.DATA_INIT_VERSION);
		return pendingStock;
	}
}
