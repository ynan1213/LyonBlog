package com.ynan.dto;

import com.ynan.dictionary.StockCounterTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yuannan
 */
@Data
@Accessors(chain = true)
public class CounterStockLocator extends StockLocator {

	private StockCounterTypeEnum counterType;

	public static CounterStockLocator from(StockLocator locator, StockCounterTypeEnum stockCounterType) {
		CounterStockLocator counterStockLocator = new CounterStockLocator();
		counterStockLocator.setLocId(locator.getLocId());
		counterStockLocator.setSkuId(locator.getSkuId());
		counterStockLocator.setLotId(locator.getLotId());
		counterStockLocator.setLpnNo(locator.getLpnNo());
		counterStockLocator.setCounterType(stockCounterType);
		return counterStockLocator;
	}
}
