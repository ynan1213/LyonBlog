package com.ynan.dto;

import com.ynan.dictionary.StockOperandTypeEnum;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @author yuannan
 */
@Data
public class StockOperation {

	/**
	 * 来源操作对象
	 */
	private StockOperandTypeEnum fromOperand;
	/**
	 * 目标操作对象
	 */
	private StockOperandTypeEnum toOperand;
	/**
	 * 数量
	 */
	private BigDecimal qty;
	/**
	 * 校验值
	 */
	private BigDecimal verifyQty;
	/**
	 * 位置来源
	 */
	private StockPosition from;
	/**
	 * 位置目标
	 */
	private StockPosition to;
	/**
	 * skuId
	 * byAllocate 为true时, 忽略
	 */
	private Long skuId;
	/**
	 * 批次id
	 * byAllocate 为true时, 忽略
	 */
	private Long lotId;

}
