package com.ynan.request;

import com.ynan.dto.CounterStockLocator;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @author yuannan
 */
@Data
public class CommonAddStockRequest {

	/**
	 * 库存定位(精确到库存类型 - 冻结、分配、可操作)
	 */
	private CounterStockLocator locator;
	/**
	 * 增加数量
	 */
	private BigDecimal addNum;
}
