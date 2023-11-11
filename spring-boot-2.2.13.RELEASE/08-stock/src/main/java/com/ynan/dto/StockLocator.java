package com.ynan.dto;

import java.io.Serializable;
import java.util.Objects;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yuannan
 */
@Data
@Accessors(chain = true)
public class StockLocator implements Comparable<StockLocator>, Serializable {

	public static final String LPN_NULL_VALUE = "NONE";
	public static final String LPN_NULL_SHOW_VALUE = "--";

	private Long skuId;
	private Long lotId;
	private Long locId;
	/**
	 * LPN空时 默认值
	 */
	private String lpnNo;

	@Override
	public int hashCode() {
		return Objects.hash(skuId, lotId, locId, lpnNo);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof StockLocator)) {
			return false;
		}
		StockLocator locator = (StockLocator) obj;
		boolean skuSame = false;
		boolean locSame = false;
		boolean lotSame = false;
		boolean lpnSame = false;
		if (skuId != null) {
			skuSame = skuId.equals(locator.getSkuId());
		}
		if (lotId != null) {
			lotSame = lotId.equals(locator.getLotId());
		}
		if (locId != null) {
			locSame = locId.equals(locator.getLocId());
		}
		if (lpnNo != null) {
			lpnSame = lpnNo.equals(locator.getLpnNo());
		}
		return skuSame && locSame && lotSame && lpnSame;
	}

	@Override
	public int compareTo(StockLocator o) {

		if (o == null) {
			return -1;
		}
		int r = valueCompare(skuId, o.getSkuId());
		if (r != 0) {
			return r;
		}
		r = valueCompare(lotId, o.getLotId());
		if (r != 0) {
			return r;
		}
		r = valueCompare(locId, o.getLocId());
		if (r != 0) {
			return r;
		}
		return valueCompare(lpnNo, o.getLpnNo());
	}

	private int valueCompare(Comparable v1, Comparable v2) {
		if (v1 == null && v2 == null) {
			return 0;
		} else if (v1 != null && v2 != null) {
			return v1.compareTo(v2);
		} else if (v2 == null) {
			return -1;
		} else {
			return 1;
		}
	}

	//默认值和显示默认值转换
	public static String converterToShowValue(String value) {
		if (StringUtils.isEmpty(value) || LPN_NULL_VALUE.equals(value)) {
			return LPN_NULL_SHOW_VALUE;
		}
		return value;
	}

	public static String converterToDefaultValue(String value) {
		if (StringUtils.isEmpty(value) || LPN_NULL_SHOW_VALUE.equals(value)) {
			return LPN_NULL_VALUE;
		}
		return value;
	}
}