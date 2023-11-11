package com.ynan.dictionary;

import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yuannan
 */
public enum StockOperandTypeEnum {

	//库存操作对象
	PENDING_OPERAND("PENDING_OPERAND", "在途库存", StockCounterTypeEnum.OPERATIONAL_COUNTER),
	SHELF_AVAILABLE_OPERAND("SHELF_AVAILABLE_OPERAND", "货架可用库存", StockCounterTypeEnum.OPERATIONAL_COUNTER),
	SHELF_ALLOCATE_OPERAND("SHELF_ALLOCATE_OPERAND", "货架分配库存", StockCounterTypeEnum.ALLOCATE_COUNTER),
	SHELF_HOLD_OPERAND("SHELF_HOLD_OPERAND", "货架冻结库存", StockCounterTypeEnum.HOLD_COUNTER),
	SHELF_LOCK_OPERAND("SHELF_LOCK_OPERAND", "货架锁定库存", StockCounterTypeEnum.LOCK_COUNTER),
	PICKED_OPERAND("PICKED_OPERAND", "拣货库存", StockCounterTypeEnum.OPERATIONAL_COUNTER),
	PICK_LOCKED_OPERAND("PICK_LOCKED_OPERAND", "拣货锁定库存", StockCounterTypeEnum.OPERATIONAL_COUNTER),
	SORTED_OPERAND("SORTED_OPERAND", "分拣库存", StockCounterTypeEnum.OPERATIONAL_COUNTER),
	PICKED_HOLD_OPERAND("PICKING_HOLD_OPERAND", "拣货区冻结库存", StockCounterTypeEnum.HOLD_COUNTER),
	FEEDING_OPERAND("FEEDING_OPERAND", "投料区库存", StockCounterTypeEnum.OPERATIONAL_COUNTER),
	PRODUCTION_OPERAND("PRODUCTION_OPERAND", "生产区库存", StockCounterTypeEnum.OPERATIONAL_COUNTER),
	SORTED_LOCK_OPERAND("SORTED_LOCK_OPERAND", "分拣锁定库存", StockCounterTypeEnum.OPERATIONAL_COUNTER),
	PROD_LINE_OPERAND("PROD_LINE_OPERAND", "线边仓库存", StockCounterTypeEnum.ALLOCATE_COUNTER),
	STOCK_OUTER_TOTAL_OPERAND("STOCK_OUTER_TOTAL_OPERAND", "外部总库存", StockCounterTypeEnum.OPERATIONAL_COUNTER);

	public static List<StockOperandTypeEnum> OUT_BOUND_OPERAND_LIST = Lists
			.newArrayList(SHELF_ALLOCATE_OPERAND, SHELF_HOLD_OPERAND, PICKED_OPERAND, SORTED_OPERAND,
					PICKED_HOLD_OPERAND,
					FEEDING_OPERAND, PRODUCTION_OPERAND);

	StockOperandTypeEnum(String code, String desc, StockCounterTypeEnum counterType) {
		this.code = code;
		this.desc = desc;
		this.counterType = counterType;
	}

	/**
	 * code
	 */
	public String code;
	/**
	 * 描述
	 */
	public String desc;
	public StockCounterTypeEnum counterType;

	private static Map<LocationTypeEnum, Map<StockCounterTypeEnum, StockOperandTypeEnum>> routeByLocationMap;


	static {
		routeByLocationMap = new HashMap<>();
		// 收货过渡库位 + 可操作库存 -> 在途库存
		put(LocationTypeEnum.RECEIVE_STAGING, StockCounterTypeEnum.OPERATIONAL_COUNTER,
				StockOperandTypeEnum.PENDING_OPERAND);

		//零拣位 + 可操作库存 -> 货架可用库存
		put(LocationTypeEnum.RESERVE_LOCATION, StockCounterTypeEnum.OPERATIONAL_COUNTER,
				StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND);
		//零拣位 + 分配库存 -> 货架分配库存
		put(LocationTypeEnum.RESERVE_LOCATION, StockCounterTypeEnum.ALLOCATE_COUNTER,
				StockOperandTypeEnum.SHELF_ALLOCATE_OPERAND);
		//零拣位 + 冻结库存 -> 货架冻结库存
		put(LocationTypeEnum.RESERVE_LOCATION, StockCounterTypeEnum.HOLD_COUNTER,
				StockOperandTypeEnum.SHELF_HOLD_OPERAND);

		//拣货位 + 可操作库存 -> 拣货库存
		put(LocationTypeEnum.PICK_STAGING, StockCounterTypeEnum.OPERATIONAL_COUNTER,
				StockOperandTypeEnum.PICKED_OPERAND);
		//拣货位 + 冻结库存 -> 拣货冻结库存
		put(LocationTypeEnum.PICK_STAGING, StockCounterTypeEnum.HOLD_COUNTER, StockOperandTypeEnum.PICKED_HOLD_OPERAND);
		//分拣位 + 可操作库存 -> 分拣库存
		put(LocationTypeEnum.SORTING_STAGING, StockCounterTypeEnum.OPERATIONAL_COUNTER,
				StockOperandTypeEnum.SORTED_OPERAND);
		//分拣位 + 冻结库存 -> 分拣锁定库存
		put(LocationTypeEnum.SORTING_STAGING, StockCounterTypeEnum.ALLOCATE_COUNTER,
				StockOperandTypeEnum.SORTED_LOCK_OPERAND);

		//越库位 + 可操作库存 -> 货架可用库存
		put(LocationTypeEnum.CROSS_LOCATION, StockCounterTypeEnum.OPERATIONAL_COUNTER,
				StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND);
		//越库位 + 冻结库存 -> 货架冻结库存
		put(LocationTypeEnum.CROSS_LOCATION, StockCounterTypeEnum.HOLD_COUNTER,
				StockOperandTypeEnum.SHELF_HOLD_OPERAND);
		//越库位 + 分配库存 -> 货架分配库存
		put(LocationTypeEnum.CROSS_LOCATION, StockCounterTypeEnum.ALLOCATE_COUNTER,
				StockOperandTypeEnum.SHELF_ALLOCATE_OPERAND);

		//坏品位 + 可操作库存 -> 货架可用库存
		put(LocationTypeEnum.DAMAGE_LOCATION, StockCounterTypeEnum.OPERATIONAL_COUNTER,
				StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND);
		//坏品位 + 冻结库存 -> 货架冻结库存
		put(LocationTypeEnum.DAMAGE_LOCATION, StockCounterTypeEnum.HOLD_COUNTER,
				StockOperandTypeEnum.SHELF_HOLD_OPERAND);
		//坏品位 + 分配库存 -> 货架分配库存
		put(LocationTypeEnum.DAMAGE_LOCATION, StockCounterTypeEnum.ALLOCATE_COUNTER,
				StockOperandTypeEnum.SHELF_ALLOCATE_OPERAND);

		//存储位 + 可操作库存 -> 货架可用库存
		put(LocationTypeEnum.STORAGE_LOCATION, StockCounterTypeEnum.OPERATIONAL_COUNTER,
				StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND);
		//存储位 + 可操作库存 -> 货架分配库存
		put(LocationTypeEnum.STORAGE_LOCATION, StockCounterTypeEnum.ALLOCATE_COUNTER,
				StockOperandTypeEnum.SHELF_ALLOCATE_OPERAND);
		//存储位 + 冻结库存 -> 货架冻结库存
		put(LocationTypeEnum.STORAGE_LOCATION, StockCounterTypeEnum.HOLD_COUNTER,
				StockOperandTypeEnum.SHELF_HOLD_OPERAND);

		// 投料暂存位 + 可操作库存 -> 投料区库存
		put(LocationTypeEnum.MATERIAL_FEED_STAGING, StockCounterTypeEnum.OPERATIONAL_COUNTER,
				StockOperandTypeEnum.FEEDING_OPERAND);
		// 生产暂存位 + 可操作库存 -> 生产区库存
		put(LocationTypeEnum.PRO_STAGING, StockCounterTypeEnum.OPERATIONAL_COUNTER,
				StockOperandTypeEnum.PRODUCTION_OPERAND);
		// 反加工投料暂存位 + 可操作库存 -> 投料区库存
		put(LocationTypeEnum.REVERSE_PRO_FEED_LOCATION, StockCounterTypeEnum.OPERATIONAL_COUNTER,
				StockOperandTypeEnum.FEEDING_OPERAND);

		// 线边领用暂存位 + 可操作库存 -> 投料区库存
		put(LocationTypeEnum.FEED_RECEIVE_LOCATION, StockCounterTypeEnum.OPERATIONAL_COUNTER,
				StockOperandTypeEnum.FEEDING_OPERAND);

		// 线边仓库位 + 可操作库存 -> 货架可用库存
		put(LocationTypeEnum.PROD_LINE_LOCATION, StockCounterTypeEnum.OPERATIONAL_COUNTER,
				StockOperandTypeEnum.SHELF_AVAILABLE_OPERAND);
		// 线边仓库位 + 分配库存 -> 线边仓分配库存
		put(LocationTypeEnum.PROD_LINE_LOCATION, StockCounterTypeEnum.ALLOCATE_COUNTER,
				StockOperandTypeEnum.PROD_LINE_OPERAND);
		// 线边仓库位 + 锁定库存 -> 货架锁定库存
		put(LocationTypeEnum.PROD_LINE_LOCATION, StockCounterTypeEnum.LOCK_COUNTER,
				StockOperandTypeEnum.SHELF_LOCK_OPERAND);
	}

	/**
	 *
	 */
	public static StockOperandTypeEnum fromProcessType(StockProcessTypeEnum processType,
			StockSingleOperationTypeEnum operationType) {
		if (processType == null || operationType == null) {
			return null;
		}
		if (StockSingleOperationTypeEnum.REDUCE.equals(operationType)) {
			return processType.from;
		} else {
			return processType.to;
		}
	}

	public static StockOperandTypeEnum fromLocationType(
			LocationTypeEnum locationTypeEnum, StockCounterTypeEnum counterTypeEnum) {
		if (locationTypeEnum == null || counterTypeEnum == null) {
			return null;
		}
		if (!routeByLocationMap.containsKey(locationTypeEnum)) {
			return null;
		}
		return routeByLocationMap.get(locationTypeEnum).get(counterTypeEnum);
	}

	private static void put(LocationTypeEnum locationTypeEnum,
			StockCounterTypeEnum counterTypeEnum,
			StockOperandTypeEnum operandTypeEnum) {
		if (!routeByLocationMap.containsKey(locationTypeEnum)) {
			routeByLocationMap.put(locationTypeEnum, new HashMap<>());
		}
		Map<StockCounterTypeEnum, StockOperandTypeEnum> map2 = routeByLocationMap.get(locationTypeEnum);
		map2.put(counterTypeEnum, operandTypeEnum);
	}

	public static StockOperandTypeEnum fromCode(String code) {
		for (StockOperandTypeEnum stockOperandTypeEnum : StockOperandTypeEnum.values()) {
			if (stockOperandTypeEnum.code.equals(code)) {
				return stockOperandTypeEnum;
			}
		}
		return null;
	}
}
