package com.ynan.request;

import com.ynan.dto.CounterStockLocator;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @author yuannan
 */
@Data
public class CommonReduceStockRequest {

	private CounterStockLocator locator;
	private BigDecimal reduceNum;
	private BigDecimal verifyNum;
}
