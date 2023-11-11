package com.ynan.operation;

import com.ynan.dto.StockLocator;
import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yuannan
 */
@Data
@Accessors(chain = true)
public class ReduceOperation {

	private StockLocator locator;
	private Long allocateId;
	private BigDecimal reduceNum;
	/**
	 * 库存前值校验
	 */
	private BigDecimal verifyNum;
	private boolean fromAllocate;
}
