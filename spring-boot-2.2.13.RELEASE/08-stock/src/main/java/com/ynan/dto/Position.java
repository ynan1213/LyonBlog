package com.ynan.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yuannan
 */
@Accessors(chain = true)
@Data
public class Position implements StockPosition {

	private String name;
	/**
	 * 库位id
	 * 必填
	 */
	private Long locId;
	/**
	 * 容器编号
	 * 非必填
	 */
	private String lpnNo;

	public Position() {
		this.name = "Position";
	}

	public Position(Long locId, String lpnNo) {
		this.locId = locId;
		this.lpnNo = lpnNo;
		this.name = "Position";
	}
}