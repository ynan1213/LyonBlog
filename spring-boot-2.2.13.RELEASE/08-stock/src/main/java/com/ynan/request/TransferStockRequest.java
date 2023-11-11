package com.ynan.request;

import com.ynan.dto.Position;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @author yuannan
 */
@Data
public class TransferStockRequest {

	/**
	 * 从哪
	 */
	private Position from;
	/**
	 * 到哪
	 */
	private Position to;
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

}
