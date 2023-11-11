package com.ynan.request;

import com.ynan.dto.StockLocator;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @author yuannan
 */
@Data
public class AddStockRequest {

	/**
	 * 库存定位
	 */
	private StockLocator locator;
	/**
	 * 增加数量
	 */
	private BigDecimal addNum;
}

