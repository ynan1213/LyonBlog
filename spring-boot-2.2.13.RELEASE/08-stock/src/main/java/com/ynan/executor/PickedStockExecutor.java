package com.ynan.executor;

import com.ynan.dto.StockLocator;
import com.ynan.entity.PickedStock;
import com.ynan.mappers.PickedStockMapper;
import com.ynan.model.BusinessContext;
import com.ynan.operation.PlusOperation;
import com.ynan.operation.ReduceOperation;
import java.math.BigDecimal;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

/**
 * @author yuannan
 */
@Slf4j
@Component
public class PickedStockExecutor implements StockExecutor {

	@Autowired
	private PickedStockMapper pickedStockMapper;

	@Override
	public boolean reduce(ReduceOperation reduceOperation, BusinessContext businessContext) {

		boolean success;
		StockLocator locator = reduceOperation.getLocator();

		if (reduceOperation.getVerifyNum() == null) {
			success = pickedStockMapper.reduce(locator, reduceOperation.getReduceNum()) > 0;
		} else {
			success = pickedStockMapper
					.verifyAndReduce(locator, reduceOperation.getReduceNum(), reduceOperation.getVerifyNum()) > 0;
		}

		if (!success) {
			log.error("[StockOperationError] fail to reduce picked qty" +
					", operation:{}, businessContext:{}", reduceOperation, businessContext);
			PickedStock exist = pickedStockMapper.selectByLocator(locator);
			if (exist == null) {
				log.error("[StockOperationError] because there has no such stock record");
			} else {
				log.error("[StockOperationError] current picked qty:{}," +
						" reduceQty:{}", exist.getQtyPicked(), reduceOperation.getReduceNum());
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

			boolean success = plusInternal(locator, plusOperation.getPlusNum());

			if (!success) {
				log.error("[StockOperationError] fail to plus picked qty, and exist record " +
						"operation:{}, businessContext:{}", plusOperation, businessContext);
			} else {
				log.info("[StockOperationSuccess] success plus picked qty, " +
						"operation:{}, businessContext:{}", plusOperation, businessContext);
			}

			return success;
		} else {
			PickedStock pickedStock = build(plusOperation, businessContext);
			try {
				boolean success = pickedStockMapper.insertSelective(pickedStock) > 0;

				log.info("[StockOperationSuccess] success plus picked qty, " +
						"operation:{}, businessContext:{}", plusOperation, businessContext);

				return success;
			} catch (DuplicateKeyException ex) {
				log.warn("并发写入冲突", ex);
				boolean success = plusInternal(locator, plusOperation.getPlusNum());

				if (!success) {
					log.error("[StockOperationError] fail to plus picked qty, RepeatedFlag" +
							"operation:{}, businessContext:{}", plusOperation, businessContext);
				} else {
					log.info("[StockOperationSuccess] success plus picked qty, RepeatedFlag" +
							"operation:{}, businessContext:{}", plusOperation, businessContext);
				}

				return success;
			}
		}
	}

	private boolean isStockExist(StockLocator stockLocator) {
		PickedStock stock = pickedStockMapper.selectByLocator(stockLocator);
		return stock != null;
	}

	private boolean plusInternal(StockLocator locator, BigDecimal plusNum) {
		return pickedStockMapper.plus(locator, plusNum) > 0;
	}

	private PickedStock build(PlusOperation plusOperation, BusinessContext businessContext) {
		Date now = new Date();
		StockLocator locator = plusOperation.getLocator();
		PickedStock pickedStock = new PickedStock().setSkuId(locator.getSkuId())
				.setLotId(locator.getLotId()).setLocId(locator.getLocId())
				.setLpnNo(locator.getLpnNo()).setQtyPicked(plusOperation.getPlusNum())
				//				.setWarehouseId(businessContext.getWarehouseId()).setVersion(Constants.DATA_INIT_VERSION)
				.setQtyHold(BigDecimal.ZERO);
		//		pickedStock.setId(SnowflakeIdUtil.generateId());
		//		pickedStock.setCreateBy(businessContext.getUserId())
		//				.setUpdateBy(businessContext.getUserId()).setCreateTime(now)
		//				.setUpdateTime(now).setIsDeleted(DataDeleteFlagEnum.NOT_DELETED.code);

		return pickedStock;
	}
}
