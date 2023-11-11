package com.ynan.dictionary;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

/**
 * @author yuannan
 */
public enum StockProcessTypeEnum {
	MANAGER_FIX("MANAGER_FIX", "管理员修复", null, null, true),
	//出库入库
	//采购入库_收货： from -> null, to -> 在途库存
	PURCHASE_IN("PO", "采购入库_收货", null, StockOperandTypeEnum.PENDING_OPERAND, true),
	//退货入库_收货:  from -> null, to -> 在途库存
	RETURN_IN("RS", "退货入库_收货", null, StockOperandTypeEnum.PENDING_OPERAND, true),
	//调拨入库_收货: from -> null, to -> 在途库存
	TRANSFER_IN("TI", "调拨入库_收货", null, StockOperandTypeEnum.PENDING_OPERAND, true),
	//物料入库_收货：from->null, to-> 在途库存
	MATERIAL_IN("MI", "调拨入库_收货", null, StockOperandTypeEnum.PENDING_OPERAND, true),
	//成品入库_收货 from->null, to-> 拣货暂存库位
	PRODUCT_IN("PI", "成品入库_收货", null, StockOperandTypeEnum.PENDING_OPERAND, true),
	//B端入库_收货 from->null, to-> 拣货暂存库位
	B_SIDE("BS", "成品入库_收货", null, StockOperandTypeEnum.PENDING_OPERAND, true),
	// 转运入库_收货 from->null,to->货架可用库存
	TRANSFER_SKU("TS", "转运入库_收货", null, StockOperandTypeEnum.PENDING_OPERAND, true),
	// 转运入库_收货 from->null,to->货架可用库存
	TRANSFER_MATERIAL("TM", "物料转运_收货", null, StockOperandTypeEnum.PENDING_OPERAND, true),
	// 转运入库_收货 from->null,to->在途库存
	TRANSPORT_IN("TSI", "转运入库_收货", null, StockOperandTypeEnum.PENDING_OPERAND, true),
	SALE_RETURN_IN("SRT", "内部销售退回_收货", null, StockOperandTypeEnum.PENDING_OPERAND, true),
	OUT_SALE_RETURN_IN("OSRT", "渠道销售退回_收货", null, StockOperandTypeEnum.PENDING_OPERAND, true),
	MES_RT_BAD_IN("MRB", "二级品_收货", null, StockOperandTypeEnum.PENDING_OPERAND, true),
	MES_BPE_BAD_IN("BPE", "反加工_二级品_收货", null, StockOperandTypeEnum.PENDING_OPERAND, true),
	MES_BPY_IN("BPY", "反加工_原料入库_收货", null, StockOperandTypeEnum.PENDING_OPERAND, true),
	FM_IN("FM", "采收入库_收货", null, StockOperandTypeEnum.PENDING_OPERAND, true),
	TURNOVER_BASKET("TB", "周转筐入库_收货", null, StockOperandTypeEnum.PENDING_OPERAND, true),
	PURCHASE_IN_WEIGHED("PO_WEIGHED", "采购入库_称重收货", null, null, true),
	PURCHASE_IN_WEIGHED_LABEL_DELETE("PO_WEIGHED_LABEL_DELETE", "采购入库_称重收货_删除标签", StockOperandTypeEnum.PENDING_OPERAND,
			null, false),


	//上架: from -> 在途库存, to -> 货架可用库存
	PUT_AWAY("PA", "上架", StockOperandTypeEnum.PENDING_OPERAND, StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND, true),
	//出库预占库存: from -> 货架可用库存, to -> 货架分配库存
	PICK_ALLOCATE("HD", "出库预占库存", StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND,
			StockOperandTypeEnum.SHELF_ALLOCATE_OPERAND, false),

	//拣货扣减预占：from -> 货架分配库存, to -> 拣货库存
	PICK_REDUCE_ALLOCATE("TR", "拣货扣减预占", StockOperandTypeEnum.SHELF_ALLOCATE_OPERAND,
			StockOperandTypeEnum.PICKED_OPERAND, true),
	REVOKE_PICK_TASK_RELEASE("REP", "撤销拣货任务-释放库存", StockOperandTypeEnum.SHELF_ALLOCATE_OPERAND,
			StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND, false),
	REVOKE_PICK_TASK_RETURN("RER", "撤销拣货任务-归还库存", StockOperandTypeEnum.PICKED_OPERAND,
			StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND, true),
	//释放预占: from -> 货架分配库存, to -> 货架可用库存
	PICK_RELEASE_ALLOCATE("RL", "拣货释放预占", StockOperandTypeEnum.SHELF_ALLOCATE_OPERAND,
			StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND, false),
	//短拣冻结: from -> 货架冻结库存, to -> 货架分配库存
	SHORT_PICK_HOLD("PICK_HOLD", "短拣冻结", StockOperandTypeEnum.SHELF_ALLOCATE_OPERAND,
			StockOperandTypeEnum.SHELF_HOLD_OPERAND, false),
	//分拣: from -> 拣货库存, to -> 分拣库存
	SORTING("SS", "分拣", StockOperandTypeEnum.PICKED_OPERAND, StockOperandTypeEnum.SORTED_OPERAND, true),
	//分拣回退: from -> 拣货库存, to -> 分拣库存
	SORTING_BACK("SS_BK", "分拣调整", StockOperandTypeEnum.SORTED_OPERAND, StockOperandTypeEnum.PICKED_OPERAND, true),
	//短分冻结：from -> 拣货库存 -> 拣货冻结库存
	SORTING_HOLD("SORT_HOLD", "短分冻结", StockOperandTypeEnum.PICKED_OPERAND, StockOperandTypeEnum.PICKED_HOLD_OPERAND,
			false),
	//发货： from -> 分拣库存, to -> null
	SEND_OUT("SO", "发货", StockOperandTypeEnum.SORTED_OPERAND, null, true),
	//发货： from ->拣货库存, to -> null
	PICK_SEND_OUT("PICK_SO", "发货", StockOperandTypeEnum.PICKED_OPERAND, null, true),
	// 出库：from -> 货架分配库存, to -> null
	OUTBOUND_FROM_SHELF("OUTBOUND_FROM_SHELF", "直接扣减货架分配库存", StockOperandTypeEnum.SHELF_ALLOCATE_OPERAND, null, true),

	//库内

	//货架库存移位：from -> 货架可用库存, to -> 货架可用库存
	SHELF_MOVE("MV", "移位", StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND, StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND,
			true),
	//货架库存变更批次
	SHELF_SWITCH_LOT("CL", "批次变更", null, null, false),
	//释放冻结: 使用通用转移
	RELEASE_HOLD("RH", "释放冻结", null, null, true),
	SHELF_COUNT_STOCK("CC", "货架盘点", StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND,
			StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND, true),
	PROFIT_LOSS("AD", "损益", null, null, true),
	REPLENISHMENT("RP", "补货", null, null, true),
	SHIP_INTERCEPT("SHIP_INTERCEPT", "出库拦截库存转移", null, null, true),
	PICK_SHIP_INTERCEPT("PICK_SHIP_INTERCEPT", "出库拦截库存转移", null, null, true),
	CONVERT("CONVERT", "库存转换", null, null, false),


	//制造相关
	MATERIAL_ALLOCATE("MA", "领料预占", StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND,
			StockOperandTypeEnum.SHELF_ALLOCATE_OPERAND, false),
	MATERIAL_ALLOCATE_PROD_LINE("MAPL", "领料预占-线边仓", StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND,
			StockOperandTypeEnum.PROD_LINE_OPERAND, false),
	MATERIAL_RELEASE("MR", "原料释放预占", StockOperandTypeEnum.SHELF_ALLOCATE_OPERAND,
			StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND, false),
	MATERIAL_RELEASE_PROD_LINE("MRPL", "原料释放预占-线边仓", StockOperandTypeEnum.PROD_LINE_OPERAND,
			StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND, false),
	PA_FROM_PROD_LINE("PA_FROM_PROD_LINE", "线边仓入库上架", StockOperandTypeEnum.PROD_LINE_OPERAND,
			StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND, false),
	MATERIAL_TAKE_ALLOCATE("TM", "领料", null, null, true),
	LB_SPLIT("LB_SPLIT", "标签分割", null, null, false),
	MV_LABEL("MV_LABEL", "移动标签", null, null, true),

	MATERIAL_FEED("PRO_FEED", "投料", StockOperandTypeEnum.FEEDING_OPERAND, StockOperandTypeEnum.PRODUCTION_OPERAND,
			true),
	NEW_MATERIAL_FEED("NEW_PRO_FEED", "新投料", StockOperandTypeEnum.FEEDING_OPERAND, null, true),
	MATERIAL_FEED_PROD_LINE("PRO_FEED_PROD_LINE", "线边仓投料", StockOperandTypeEnum.PROD_LINE_OPERAND, null, true),
	AGR_FEED_PROD_LINE("AGR_FEED_PROD_LINE", "包耗材线边仓投料", StockOperandTypeEnum.PROD_LINE_OPERAND, null, true),
	MATERIAL_DRAW("PRO_DRAW", "领料", StockOperandTypeEnum.SHELF_ALLOCATE_OPERAND, StockOperandTypeEnum.FEEDING_OPERAND,
			true),
	MATERIAL_DRAW_ACCEPT("PRO_DRAW_ACCEPT", "领用接收", StockOperandTypeEnum.FEEDING_OPERAND,
			StockOperandTypeEnum.PROD_LINE_OPERAND, true),
	AGR_MAT_DRAW("AGR_MAT_DRAW", "物资领料", StockOperandTypeEnum.SHELF_ALLOCATE_OPERAND, null, true),
	AGR_MAT_DRAW_PROD_LINE("AGR_MAT_DRAW_PROD_LINE", "物资领料到线边仓", StockOperandTypeEnum.SHELF_ALLOCATE_OPERAND,
			StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND, true),
	AGR_BACK_FLUSH_PROD_LINE("AGR_BACK_FLUSH_PROD_LINE", "物资领用包材倒冲", StockOperandTypeEnum.PROD_LINE_OPERAND,
			null, true),
	AGR_LOSS_OUT_PROD_LINE("AGR_LOSS_OUT_PROD_LINE", "物资领用损耗出库", StockOperandTypeEnum.PROD_LINE_OPERAND,
			null, true),

	MATERIAL_BACK("PRO_MTL_BACK", "原料退料", null, null, true),
	MATERIAL_BACK_PROD_LINE("PRO_MTL_BACK_PROD_LINE", "线边仓退料", null, StockOperandTypeEnum.PROD_LINE_OPERAND, true),
	AGR_BACK_PROD_LINE("AGR_BACK_PROD_LINE", "包耗材线边仓退料", null, StockOperandTypeEnum.PROD_LINE_OPERAND, true),
	SEMI_PROD_IN("SEMI_PROD_IN", "半成品仓库报工", null, StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND, true),
	SEMI_PROD_IN_PROD_LINE("SEMI_PROD_IN_PROD_LINE", "半成品线边报工", null, StockOperandTypeEnum.PROD_LINE_OPERAND, true),
	SEMI_PROD_RETURN_PROD_LINE("SEMI_PROD_RETURN_PROD_LINE", "半成品报工回退", StockOperandTypeEnum.PROD_LINE_OPERAND, null,
			true),
	REVERSE_PRO_BACK("REVERSE_PRO_BACK", "反加工成品退料", null, null, true),
	MATERIAL_RETURN("PRO_MTL_RETURN", "原料返库", StockOperandTypeEnum.FEEDING_OPERAND,
			StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND, true),
	AGRICULTURAL_MATERIAL_RETURN("AGR_MAT_RETURN", "物资退料", null, null, true),
	IN_CHECK_LOSS("IN_CHECK_LOSS", "损耗入库", null, null, true),
	OUT_CHECK_LOSS("OUT_CHECK_LOSS", "损耗出库", null, null, true),
	MATERIAL_OUT_BOUND("MATERIAL_OUT_BOUND", "原料出库", null, null, true),
	CONSUMABLES_DRAW("PRO_CON_DRAW", "物料领用", null, null, true),
	CONSUMABLES_RETURN("PRO_CON_RETURN", "物料返库", null, null, true),
	REQ_FREEZE_LABEL("REQ_FREEZE_LABEL", "冻结标签", StockOperandTypeEnum.SHELF_ALLOCATE_OPERAND,
			StockOperandTypeEnum.SHELF_HOLD_OPERAND, false),
	REQ_SHORT_FREEZE_STOCK("REQ_SHORT_FREEZE_STOCK", "短领冻结库存", StockOperandTypeEnum.SHELF_ALLOCATE_OPERAND,
			StockOperandTypeEnum.SHELF_HOLD_OPERAND, false),
	PICK_RETURN("PICK_RETURN", "返拣", null, null, true),
	// 反加工相关
	REVERSE_MATERIAL_DRAW("REVERSE_PRO_DRAW", "反加工领料", StockOperandTypeEnum.SHELF_ALLOCATE_OPERAND,
			StockOperandTypeEnum.FEEDING_OPERAND, true),
	REVERSE_MATERIAL_RELEASE("REVERSE_MATERIAL_RELEASE", "反加工投料库存释放", StockOperandTypeEnum.FEEDING_OPERAND,
			StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND, true),
	REVERSE_MATERIAL_FEED("REVERSE_PRO_FEED", "反加工投料", null, null, true),
	REVERSE_MATERIAL_RETURN("REVERSE_MATERIAL_RETURN", "反加工原料返库", StockOperandTypeEnum.FEEDING_OPERAND,
			StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND, true),
	// 发货单出库-分拣区
	OUTBOUND_IVO("OUTBOUND_IVO", "发货单出库", StockOperandTypeEnum.SORTED_LOCK_OPERAND, null, true),
	INVOICE_LOCK("INVOICE_LOCK", "发货单锁定库存", StockOperandTypeEnum.SORTED_OPERAND,
			StockOperandTypeEnum.SORTED_LOCK_OPERAND, false),
	INVOICE_UNLOCK("INVOICE_UNLOCK", "发货单释放库存", StockOperandTypeEnum.SORTED_LOCK_OPERAND,
			StockOperandTypeEnum.SORTED_OPERAND, false),
	// 发货单出库-拣货区
	OUTBOUND_IVO_PICK("OUTBOUND_IVO_PICK", "发货单出库", StockOperandTypeEnum.PICK_LOCKED_OPERAND, null, true),
	INVOICE_LOCK_PICK("INVOICE_LOCK_PICK", "发货单锁定库存", StockOperandTypeEnum.PICKED_OPERAND,
			StockOperandTypeEnum.PICK_LOCKED_OPERAND, false),
	INVOICE_UNLOCK_PICK("INVOICE_UNLOCK_PICK", "发货单释放库存", StockOperandTypeEnum.PICK_LOCKED_OPERAND,
			StockOperandTypeEnum.PICKED_OPERAND, false),

	REVOKE_RECEIVE_DELETE("REVOKE_RECEIVE_DELETE", "撤销收货-扣除在途库存",
			StockOperandTypeEnum.PENDING_OPERAND, null, true),
	/** 库存锁定 **/
	STOCK_LOCK("STOCK_LOCK", "库存锁定", StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND,
			StockOperandTypeEnum.SHELF_LOCK_OPERAND, false),
	STOCK_UNLOCK("STOCK_UNLOCK", "库存解锁", StockOperandTypeEnum.SHELF_LOCK_OPERAND,
			StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND, false),
	//库存关联标签
	STOCK_RELATE_TO_LABEL("RTL", "关联标签", null, null, false),
	// 关联标签(LPN)
	STOCK_RELATE_TO_LPN("RELATE_LPN", "绑定标签", null, null, false),

	/**
	 * 外部库存操作流程节点
	 */
	ASN_TB_FINISH("ASN_TB_FINISH", "周转筐入库完成", StockOperandTypeEnum.STOCK_OUTER_TOTAL_OPERAND, null, true),
	DO_DOB_FINISH("DO_DOB_FINISH", "周转筐出库完成", null, StockOperandTypeEnum.STOCK_OUTER_TOTAL_OPERAND, true),
	ADJUST_FROM("ADJUST_FROM", "外部库存调整减少", StockOperandTypeEnum.STOCK_OUTER_TOTAL_OPERAND, null, true),
	ADJUST_TO("ADJUST_TO", "外部库存调整增加", null, StockOperandTypeEnum.STOCK_OUTER_TOTAL_OPERAND, true);

	@Getter
	public String code;
	@Getter
	public String desc;
	public StockOperandTypeEnum from;
	public StockOperandTypeEnum to;
	/**
	 * 是否发生库存数量变化
	 * 锁定/冻结 为 false
	 * 出库/入库/盘盈亏/转移等 为 true
	 */
	@Getter
	private final boolean totalQtyChange;

	StockProcessTypeEnum(String code, String desc, StockOperandTypeEnum from, StockOperandTypeEnum to,
			boolean totalQtyChange) {
		this.code = code;
		this.desc = desc;
		this.from = from;
		this.to = to;
		this.totalQtyChange = totalQtyChange;
	}

	public static Map<String, StockProcessTypeEnum> toMap() {
		Map<String, StockProcessTypeEnum> map = new HashMap<>();
		for (StockProcessTypeEnum processTypeEnum : StockProcessTypeEnum.values()) {
			map.put(processTypeEnum.code, processTypeEnum);
		}
		return map;
	}

	public static StockProcessTypeEnum fromCode(String type) {
		for (StockProcessTypeEnum typeEnum : StockProcessTypeEnum.values()) {
			if (typeEnum.code.equals(type)) {
				return typeEnum;
			}
		}
		return null;
	}
}
