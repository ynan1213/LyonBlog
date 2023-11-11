package com.ynan.dictionary;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yuannan
 */
public enum LocationTypeEnum {

	ALL("ALL", "通配", false),
	RECEIVE_STAGING("RST", "收货过渡库位", false),
	PICK_STAGING("PST", "拣货过渡库位", false),
	SORTING_STAGING("SST", "分拣过渡库位", false),
	MATERIAL_FEED_STAGING("MST", "投料暂存位", false),
	PRO_STAGING("PRT", "生产暂存位", false),
	EACH_PICK_LOCATION("EAP", "零拣位（废弃）", false),
	RESERVE_LOCATION("RES", "零拣位", true),
	DAMAGE_LOCATION("DMG", "坏品库位", true),
	CROSS_LOCATION("CRS", "越库位", true),
	STORAGE_LOCATION("STG", "存储位", false),
	AP_VIRTUAL_LOCATION("AVL", "水产虚拟库位", false),
	REVERSE_PRO_FEED_LOCATION("RPT", "反加工投料暂存位", false),
	FEED_RECEIVE_LOCATION("FRL", "线边领用暂存位", false),
	PROD_LINE_LOCATION("PLL", "线边库位", true),
	;

	private static final EnumSet<LocationTypeEnum> canReturnLocs = EnumSet.of(LocationTypeEnum.RESERVE_LOCATION,
			LocationTypeEnum.CROSS_LOCATION, LocationTypeEnum.STORAGE_LOCATION);

	private static final EnumSet<LocationTypeEnum> canSortReturnPickLocs = EnumSet.of(LocationTypeEnum.RESERVE_LOCATION,
			LocationTypeEnum.STORAGE_LOCATION, LocationTypeEnum.CROSS_LOCATION);

	private static final EnumSet<LocationTypeEnum> ALLOW_CREATE_LOC_TYPE = EnumSet
			.of(LocationTypeEnum.RESERVE_LOCATION, LocationTypeEnum.DAMAGE_LOCATION,
					LocationTypeEnum.PROD_LINE_LOCATION, LocationTypeEnum.FEED_RECEIVE_LOCATION);

	@Getter
	private final String code;
	@Getter
	private final String desc;
	@Getter
	private final boolean enableAllocate;

	LocationTypeEnum(String code, String desc, boolean enableAllocate) {
		this.code = code;
		this.desc = desc;
		this.enableAllocate = enableAllocate;
	}

	public static LocationTypeEnum fromCode(String code) {
		if (code == null) {
			return null;
		}
		for (LocationTypeEnum it : LocationTypeEnum.values()) {
			if (it.code.equals(code)) {
				return it;
			}
		}
		return null;
	}

	public static LocationTypeEnum fromDesc(String desc) {
		if (desc == null) {
			return null;
		}
		for (LocationTypeEnum it : LocationTypeEnum.values()) {
			if (it.desc.equals(desc.trim())) {
				return it;
			}
		}
		return null;
	}

	public static boolean isShelfGoodLocationType(LocationTypeEnum locationType) {
		return SHELF_GOOD_LOCATION_TYPES.contains(locationType);
	}

	public static boolean isShelfBadLocationType(LocationTypeEnum locationType) {
		return SHELF_RES_BAD_LOCATION_TYPES.contains(locationType);
	}

	/**
	 * 零拣好品库位类型.
	 */
	public static List<LocationTypeEnum> SHELF_RES_GOOD_LOCATION_TYPES = Lists.newArrayList(
			LocationTypeEnum.RESERVE_LOCATION, LocationTypeEnum.EACH_PICK_LOCATION, LocationTypeEnum.CROSS_LOCATION);

	public static List<LocationTypeEnum> SHELF_RES_LOCATION_TYPES = Lists.newArrayList(
			LocationTypeEnum.RESERVE_LOCATION, LocationTypeEnum.EACH_PICK_LOCATION, LocationTypeEnum.CROSS_LOCATION,
			LocationTypeEnum.DAMAGE_LOCATION);

	/**
	 * 生产领用单在货架可分配的库位
	 */
	public static final List<LocationTypeEnum> SHELF_LOCATION_TYPES_PROD_ALLOCATE = Lists.newArrayList(
			LocationTypeEnum.RESERVE_LOCATION, LocationTypeEnum.EACH_PICK_LOCATION, LocationTypeEnum.CROSS_LOCATION,
			LocationTypeEnum.PROD_LINE_LOCATION);

	/**
	 * 存储好品库位类型.
	 */
	public static List<LocationTypeEnum> SHELF_STG_GOOD_LOCATION_TYPES = Lists
			.newArrayList(LocationTypeEnum.STORAGE_LOCATION);
	/**
	 * 零拣坏品库位类型.
	 */
	public static List<LocationTypeEnum> SHELF_RES_BAD_LOCATION_TYPES = Lists
			.newArrayList(LocationTypeEnum.DAMAGE_LOCATION);
	/**
	 * 货架好品库位类型
	 */
	public static List<LocationTypeEnum> SHELF_GOOD_LOCATION_TYPES = Lists.newArrayList(
			LocationTypeEnum.RESERVE_LOCATION,
			LocationTypeEnum.EACH_PICK_LOCATION,
			LocationTypeEnum.CROSS_LOCATION,
			LocationTypeEnum.STORAGE_LOCATION
	);

	public static List<LocationTypeEnum> PENDING_LOCATION_TYPES = Lists.newArrayList(
			LocationTypeEnum.RECEIVE_STAGING,
			LocationTypeEnum.CROSS_LOCATION
	);

	/**
	 * 货架库位类型.
	 */
	public static final List<LocationTypeEnum> SHELF_LOCATION_TYPES = Lists.newArrayList(
			LocationTypeEnum.RESERVE_LOCATION, LocationTypeEnum.EACH_PICK_LOCATION, LocationTypeEnum.CROSS_LOCATION,
			LocationTypeEnum.STORAGE_LOCATION, LocationTypeEnum.DAMAGE_LOCATION, LocationTypeEnum.PROD_LINE_LOCATION);

	/**
	 * 标签调整库位类型
	 */
	public static List<LocationTypeEnum> LABEL_CHANGE_STOCK_LOCATION_TYPES = Lists
			.newArrayList(LocationTypeEnum.RESERVE_LOCATION, LocationTypeEnum.EACH_PICK_LOCATION,
					LocationTypeEnum.CROSS_LOCATION,
					LocationTypeEnum.STORAGE_LOCATION,
					LocationTypeEnum.DAMAGE_LOCATION, LocationTypeEnum.PROD_LINE_LOCATION);

	/**
	 * 允许分割标签的库位
	 */
	public static final List<LocationTypeEnum> ENABLE_SPLIT_LOC_TYPES = Lists.newArrayList(
			LocationTypeEnum.DAMAGE_LOCATION, LocationTypeEnum.CROSS_LOCATION, LocationTypeEnum.STORAGE_LOCATION,
			LocationTypeEnum.RESERVE_LOCATION, LocationTypeEnum.MATERIAL_FEED_STAGING,
			LocationTypeEnum.PROD_LINE_LOCATION, LocationTypeEnum.FEED_RECEIVE_LOCATION);

	/**
	 * 线边仓的库位
	 */
	public static List<LocationTypeEnum> PROD_LINE_LOCATION_TYPES = Lists
			.newArrayList(LocationTypeEnum.PROD_LINE_LOCATION, LocationTypeEnum.FEED_RECEIVE_LOCATION);

	/**
	 * 生产区域库位类型
	 */
	public static List<LocationTypeEnum> PRODUCTION_LOCATION_TYPES = Lists
			.newArrayList(MATERIAL_FEED_STAGING, PRO_STAGING);


	/**
	 * 批属性调整库位类型
	 */
	public static List<LocationTypeEnum> LOT_CHANGE_TYPES = Lists
			.newArrayList(RESERVE_LOCATION, CROSS_LOCATION, DAMAGE_LOCATION, STORAGE_LOCATION);

	public static List<LocationTypeEnum> SHELF_QUICK_GOOD_LOCATION_TYPES = Lists.newArrayList(
			STORAGE_LOCATION, RESERVE_LOCATION
	);

	/**
	 * 是否是生产库位类型
	 */
	public static boolean isProductionLocationType(String code) {
		return PRODUCTION_LOCATION_TYPES.stream().anyMatch(e -> e.code.equals(code));
	}

	public static boolean isPendingLocationType(String code) {
		return PENDING_LOCATION_TYPES.stream().anyMatch(e -> e.code.equals(code));
	}

	/**
	 * 质检库位类型
	 */
	public static List<LocationTypeEnum> QUALITY_LOCATION_TYPES = Lists.newArrayList(
			LocationTypeEnum.RESERVE_LOCATION,
			LocationTypeEnum.EACH_PICK_LOCATION,
			LocationTypeEnum.STORAGE_LOCATION,
			LocationTypeEnum.CROSS_LOCATION,
			LocationTypeEnum.PROD_LINE_LOCATION,
			LocationTypeEnum.DAMAGE_LOCATION
	);

	/**
	 * 库位类型允许移位
	 */
	public static List<LocationTypeEnum> MOVE_LOC_TYPES = Lists.newArrayList(
			LocationTypeEnum.RESERVE_LOCATION,
			LocationTypeEnum.STORAGE_LOCATION,
			LocationTypeEnum.CROSS_LOCATION,
			LocationTypeEnum.DAMAGE_LOCATION
	);

	/**
	 * 是否允许移位
	 */
	public static boolean isMoveType(String code) {
		return MOVE_LOC_TYPES.stream().anyMatch(e -> e.code.equals(code));
	}

	/**
	 * 备货好品
	 */
	private static final List<LocationTypeEnum> STOCK_UP_GOOD_LOC_LIST = ImmutableList
			.of(LocationTypeEnum.RESERVE_LOCATION,
					LocationTypeEnum.CROSS_LOCATION, LocationTypeEnum.STORAGE_LOCATION);

	public static List<LocationTypeEnum> getStockUpGoodLocList() {
		return STOCK_UP_GOOD_LOC_LIST;
	}

	public static EnumSet<LocationTypeEnum> getCanReturnLocs() {
		return canReturnLocs;
	}

	public static EnumSet<LocationTypeEnum> getCanSortReturnPickLocs() {
		return canSortReturnPickLocs;
	}

	public static EnumSet<LocationTypeEnum> getCanCreateLocs() {
		return ALLOW_CREATE_LOC_TYPE;
	}

	/**
	 * 农资领用单 返库库位
	 */
	public static Set<LocationTypeEnum> getAgriculturalMaterialReturnLoc() {
		return EnumSet.of(LocationTypeEnum.RESERVE_LOCATION,
				LocationTypeEnum.CROSS_LOCATION,
				LocationTypeEnum.STORAGE_LOCATION);
	}


	/**
	 * "RESERVE_LOCATION,DAMAGE_LOCATION"  ->  "零拣位,坏品库位"
	 */
	public static String fromCodeToDesc(String code) {
		if (StringUtils.isBlank(code)) {
			return "";
		}
		List<String> codeList = Splitter.on(',').omitEmptyStrings().splitToList(code);
		return codeList.stream().map(s -> fromCode(s) == null ? "" : fromCode(s).getDesc())
				.collect(Collectors.joining(","));
	}

	private static List<LocationTypeEnum> FIN_PROD_SCAN_TYPES = ImmutableList.of(LocationTypeEnum.RESERVE_LOCATION,
			LocationTypeEnum.EACH_PICK_LOCATION,
			LocationTypeEnum.STORAGE_LOCATION);

	public static boolean finProdCanScanType(LocationTypeEnum typeEnum) {
		return FIN_PROD_SCAN_TYPES.contains(typeEnum);
	}

	public static List<String> GOOD_STOCK_SNAPSHOT_TYPES = ImmutableList.of(LocationTypeEnum.RESERVE_LOCATION.getCode(),
			LocationTypeEnum.CROSS_LOCATION.getCode(), LocationTypeEnum.STORAGE_LOCATION.getCode(),
			LocationTypeEnum.PROD_LINE_LOCATION.code,
			LocationTypeEnum.PICK_STAGING.code, LocationTypeEnum.SORTING_STAGING.code,
			LocationTypeEnum.MATERIAL_FEED_STAGING.code, LocationTypeEnum.PRO_STAGING.code,
			LocationTypeEnum.RECEIVE_STAGING.code);
}
