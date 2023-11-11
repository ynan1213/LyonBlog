package com.ynan.request;

import com.ynan.dto.CounterPosition;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @author yuannan
 */
@Data
public class CommonTransferStockRequest {

	/**
	 * 从哪
	 */
	private CounterPosition from;
	/**
	 * 到哪
	 */
	private CounterPosition to;
	/**
	 * sku id
	 */
	private Long skuId;
	/**
	 * 批次 id
	 */
	private Long lotId;
	/**
	 * 转移数量
	 */
	private BigDecimal transferNum;
	/**
	 * 分配id
	 */
	private Long allocateId;
	/**
	 * from分配库存
	 * 当fromAllocate = true时，忽略from字段
	 */
	private boolean fromAllocate;
}
