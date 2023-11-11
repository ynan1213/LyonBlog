package com.ynan.response;

import com.ynan.dto.StockLocator;
import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yuannan
 */
@Accessors(chain = true)
@Data
public class AllocateStockResult {

	public static final int SUCCESS = 0;
	public static final int UNKNOWN = 99;
	/**
	 * 0-预占成功
	 * 1-部分可用
	 * 2-无可用商品
	 */
	private int code;
	/**
	 * 预占明细
	 */
	private Long allocateId;

	private StockLocator locator;

	private BigDecimal allocateNum;

	public boolean isSuccess() {
		return code == SUCCESS;
	}

	public static final AllocateStockResult success(Long allocateId) {
		AllocateStockResult result = new AllocateStockResult();
		result.setAllocateId(allocateId);
		result.setCode(SUCCESS);
		return result;
	}

	public static final AllocateStockResult fail(int code) {
		AllocateStockResult result = new AllocateStockResult();
		result.setCode(code);
		return result;
	}

}
