package com.ynan.request;

import com.ynan.dto.StockLocator;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @author yuannan
 */
@Data
public class ReduceStockRequest {

	private StockLocator locator;
	/**
	 * 扣减数量
	 */
	private BigDecimal reduceNum;

}
