package com.ynan.request;

import com.ynan.dto.StockLocator;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @author yuannan
 */
@Data
public class AllocateStockRequest {

	/**
	 * 库存locator
	 */
	private StockLocator locator;

	/**
	 * 锁定数量
	 */
	private BigDecimal lockNum;

	/**
	 * 校验数量
	 */
	private BigDecimal verifyNum;
}
