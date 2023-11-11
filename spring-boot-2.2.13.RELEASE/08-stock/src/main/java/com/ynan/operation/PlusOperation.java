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
public class PlusOperation {

	private StockLocator locator;
	private BigDecimal plusNum;
}
