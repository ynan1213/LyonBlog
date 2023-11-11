package com.ynan.dictionary;

import lombok.Getter;

/**
 * @author yuannan
 */
public enum DocTypeEnum {

	ASN(DocCode.ASN, "入库单"),
	FP_ASN(DocCode.FP_ASN, "成品入库单"),
	SP_ASN(DocCode.SP_ASN, "半成品入库单"),
	DO(DocCode.DO, "出库单"),
	WAVE(DocCode.WAVE, "波次单"),
	COUNT(DocCode.COUNT, "盘点单"),
	PROFIT_LOSS(DocCode.INCOME, "损益单"),
	PRO_REQ(DocCode.PRO_REQ, "领料单"),
	AGRICULTURAL_MAT_REQ(DocCode.AGR_MAT_REQ, "物资领用单"),
	PRO_TASK_REQ(DocCode.PRO_TASK_REQ, "生产任务领用单"),
	PRO_CON_REQ(DocCode.CON_REQ, "物料领用单"),
	SORTING(DocCode.SORTING, "分拣任务单"),
	INVOICE(DocCode.INVOICE, "发货单"),
	STK_LOCKED(DocCode.STOCK_LOCKED, "库存锁定单"),
	TS_BOX(DocCode.TS_BOX, "转移整箱"),
	TS_SUB(DocCode.TS_SUB, "转移件数"),
	PICKING(DocCode.PICKING, "拣货"),
	IN_LOSS(DocCode.IN_LOSS, "损耗单"),
	OUT_LOSS(DocCode.OUT_LOSS, "损耗出库单"),
	MV(DocCode.MV, "移位单");

	DocTypeEnum(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	@Getter
	public String code;
	@Getter
	public String desc;

	public static DocTypeEnum fromCode(String code) {
		for (DocTypeEnum docTypeEnum : DocTypeEnum.values()) {
			if (docTypeEnum.code.equals(code)) {
				return docTypeEnum;
			}
		}
		return null;
	}
}
