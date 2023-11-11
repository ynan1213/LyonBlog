package com.ynan.executor;

import com.ynan.dto.StockLocator;
import com.ynan.entity.Stock;
import com.ynan.mappers.StockMapper;
import com.ynan.model.BusinessContext;
import com.ynan.operation.PlusOperation;
import com.ynan.operation.ReduceOperation;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

/**
 * @author yuannan
 */
@Slf4j
@Component
public class ShelfAvailableStockExecutor implements StockExecutor {

	@Autowired
	private StockMapper stockMapper;

	@Override
	public boolean reduce(ReduceOperation reduceOperation, BusinessContext businessContext) {
		StockLocator locator = reduceOperation.getLocator();
		boolean success;
		if (reduceOperation.getVerifyNum() != null) {
			success = stockMapper.verifyAndReduceStockAvailableNum(
					locator, reduceOperation.getReduceNum(), reduceOperation.getVerifyNum()) > 0;
		} else {
			success = stockMapper.reduceStockAvailableNum(locator, reduceOperation.getReduceNum()) > 0;
		}
		if (!success) {
			log.error("[StockOperationError] fail to reduce shelf available qty" +
					", operation:{}, businessContext:{}", reduceOperation, businessContext);
			Stock exist = stockMapper.selectByLocator(locator);
			if (exist == null) {
				log.error("[StockOperationError] because there has no such stock record");
			} else {
				log.error("[StockOperationError] current available qty:{}," +
						" reduceQty:{}", exist.getQtyAvailable(), reduceOperation.getReduceNum());
			}
		} else {
			log.info("[StockOperationSuccess] success reduce available qty," +
					" operation:{}, businessContext:{}", reduceOperation, businessContext);
		}

		return success;
	}

	@Override
	public boolean plus(PlusOperation plusOperation, BusinessContext businessContext) {
		if (isStockExist(plusOperation.getLocator())) {
			boolean success = plusAvailableInternal(plusOperation, businessContext);

			if (!success) {
				log.error("[StockOperationError] fail to plus shelf available qty, and exist record " +
						"operation:{}, businessContext:{}", plusOperation, businessContext);
			} else {
				log.info("[StockOperationSuccess] success plus shelf available qty, " +
						"operation:{}, businessContext:{}", plusOperation, businessContext);
			}

			return success;
		} else {
			Stock stock = buildNewStockRecord(plusOperation, businessContext);
			try {
				boolean success = insertNewStockRecord(stock);

				log.info("[StockOperationSuccess] success plus shelf available qty, " +
						"operation:{}, businessContext:{}", plusOperation, businessContext);

				return success;
			} catch (DuplicateKeyException ex) {
				log.warn("重复记录插入冲突", ex);
				boolean success = plusAvailableInternal(plusOperation, businessContext);

				if (!success) {
					log.error("[StockOperationError] fail to plus shelf available qty, RepeatedFlag" +
							"operation:{}, businessContext:{}", plusOperation, businessContext);
				} else {
					log.info("[StockOperationSuccess] success plus shelf available qty, RepeatedFlag" +
							"operation:{}, businessContext:{}", plusOperation, businessContext);
				}

				return success;
			}
		}

	}

	private boolean isStockExist(StockLocator locator) {
		Stock stock = stockMapper.selectByLocator(locator);
		return stock != null;
	}

	private boolean plusAvailableInternal(PlusOperation plusOperation, BusinessContext businessContext) {
		StockLocator locator = plusOperation.getLocator();
		return stockMapper.plusStockAvailableNum(locator, plusOperation.getPlusNum()) > 0;
	}

	private boolean reduceAvailableInternal(StockLocator locator, BigDecimal reduceNum,
			BusinessContext businessContext) {
		return stockMapper.reduceStockAvailableNum(locator, reduceNum) > 0;
	}

	private boolean insertNewStockRecord(Stock stock) {
		return stockMapper.insertSelective(stock) > 0;
	}

	private Stock buildNewStockRecord(PlusOperation plusOperation, BusinessContext businessContext) {
		Stock stock = new Stock();
		StockLocator locator = plusOperation.getLocator();
		//		stock.setId(SnowflakeIdUtil.generateId());
		stock.setSkuId(locator.getSkuId());
		stock.setLotId(locator.getLotId());
		stock.setLocId(locator.getLocId());
		stock.setLpnNo(locator.getLpnNo());
		stock.setQtyHold(BigDecimal.ZERO);
		stock.setQtyAvailable(plusOperation.getPlusNum());
		stock.setWarehouseId(businessContext.getWarehouseId());
		//		stock.setCreateBy(businessContext.getUserId());
		//		stock.setUpdateBy(businessContext.getUserId());
		//		stock.setVersion(Constants.DATA_INIT_VERSION);
		//		stock.setIsDeleted(DataDeleteFlagEnum.NOT_DELETED.code);
		//		Date now = new Date();
		//		stock.setCreateTime(now);
		//		stock.setUpdateTime(now);
		return stock;
	}
}
