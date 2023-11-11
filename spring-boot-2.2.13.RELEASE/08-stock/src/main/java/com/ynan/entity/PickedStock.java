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
@Table(name = "stk_picked")
public class PickedStock {

	@Id
	private Long id;

	@Column(name = "sku_id")
	private Long skuId;

	@Column(name = "lot_id")
	private Long lotId;

	@Column(name = "loc_id")
	private Long locId;

	@Column(name = "lpn_no")
	private String lpnNo;

	@Column(name = "qty_picked")
	private BigDecimal qtyPicked;

	@Column(name = "qty_hold")
	private BigDecimal qtyHold;

	@Column(name = "warehouse_id")
	private Long warehouseId;

	@Column(name = "version")
	private Integer version;


}
