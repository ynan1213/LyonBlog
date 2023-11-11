package com.ynan.request;

import java.math.BigDecimal;
import lombok.Data;

/**
 * @author yuannan
 */
@Data
public class TransferStockByAllocateRequest {

	/**
	 * 预占库存id
	 */
	private Long allocateId;

	/**
	 * 目标库位
	 */
	private Long destLocId;

	/**
	 * 容器编号
	 */
	private String destLpnNo;
	/**
	 * 数量
	 */
	private BigDecimal transferNum;
}
