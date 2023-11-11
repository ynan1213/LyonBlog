package com.ynan.event;

import com.ynan.dto.StockOperation;
import com.ynan.model.BusinessContext;
import lombok.Data;

/**
 * @author yuannan
 */
@Data
public class StockChangeEvent {

	private StockOperation stockOperation;
	private BusinessContext businessContext;
	private String traceId;
}
