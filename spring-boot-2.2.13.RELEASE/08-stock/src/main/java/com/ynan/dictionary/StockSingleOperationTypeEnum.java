package com.ynan.dictionary;

/**
 * @author yuannan
 */
public enum StockSingleOperationTypeEnum {
	/**
	 * 操作类型
	 */
	PLUS("PLUS", "追加"),
	REDUCE("REDUCE", "扣减"),
	;

	StockSingleOperationTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public String code;
	public String desc;
}
