package com.sqsoft.typeHandler;

public enum ArticleTypeEnum {
	JAVA(1),DUBBO(2),SPRING(4),MYBATIS(8);
	
	private int code;

	private ArticleTypeEnum(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
	
	public static ArticleTypeEnum find(int code) {
		for(ArticleTypeEnum a : ArticleTypeEnum.values()) {
			if(a.getCode() == code) {
				return a;
			}
		}
		return null;
	}
}
