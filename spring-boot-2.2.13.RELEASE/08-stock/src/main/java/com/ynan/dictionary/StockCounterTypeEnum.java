package com.ynan.dictionary;

import org.apache.commons.lang3.StringUtils;

/**
 * @author yuannan
 */
public enum StockCounterTypeEnum {

	OPERATIONAL_COUNTER("OPERATIONAL_COUNTER", "可操作库存"),
	HOLD_COUNTER("HOLD_COUNTER", "冻结库存"),
	ALLOCATE_COUNTER("ALLOCATE_COUNTER", "分配库存"),
	LOCK_COUNTER("LOCK_COUNTER", "锁定库存"),
	;

	StockCounterTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String code;
	public String desc;

	public static final StockCounterTypeEnum fromCode(String code) {
		if (StringUtils.isEmpty(code)) {
			return null;
		}
		for (StockCounterTypeEnum stockCounterTypeEnum : StockCounterTypeEnum.values()) {
			if (stockCounterTypeEnum.code.equals(code)) {
				return stockCounterTypeEnum;
			}
		}
		return null;
	}

	public static final StockCounterTypeEnum fromCodeNullNot(String code) {
		StockCounterTypeEnum stockCounterType = fromCode(code);
		if (stockCounterType == null) {
			throw new RuntimeException("xxx");
		}
		return stockCounterType;
	}
}
