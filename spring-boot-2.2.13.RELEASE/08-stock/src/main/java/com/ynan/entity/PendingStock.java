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
@Accessors(chain = true)
@Data
@Table(name = "stk_pending")
public class PendingStock {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "sku_id")
	private Long skuId;

	@Column(name = "lpn_no")
	private String lpnNo;

	@Column(name = "lot_id")
	private Long lotId;

	@Column(name = "loc_id")
	private Long locId;

	@Column(name = "qty_pending")
	private BigDecimal qtyPending;

	@Column(name = "warehouse_id")
	private Long warehouseId;

	@Column(name = "version")
	private Integer version;

}
