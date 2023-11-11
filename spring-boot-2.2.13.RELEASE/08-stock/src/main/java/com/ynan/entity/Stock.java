package com.ynan.entity;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yuannan
 */
@Data
@Accessors(chain = true)
@Table(name = "stk_lot_lpn_loc")
public class Stock {

	@Id
	private Long id;

	/**
	 * 商品ID
	 */
	@Column(name = "sku_id")
	private Long skuId;

	/**
	 * 库位ID
	 */
	@Column(name = "loc_id")
	private Long locId;

	/**
	 * 批次ID
	 */
	@Column(name = "lot_id")
	private Long lotId;

	/**
	 * 容器ID
	 */
	@Column(name = "lpn_no")
	private String lpnNo;

	/**
	 * 可用数量
	 */
	@Column(name = "qty_available")
	private BigDecimal qtyAvailable;

	/**
	 * 总数量
	 */
	@Column(name = "qty_hold")
	private BigDecimal qtyHold;

	/**
	 * 版本号
	 */
	private Integer version;

	/**
	 * 仓库ID
	 */
	@Column(name = "warehouse_id")
	private Long warehouseId;
}