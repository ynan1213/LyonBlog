package com.ynan.dto;

import lombok.Data;

/**
 * @author yuannan
 */
@Data
public class IdentityPosition implements StockPosition {

	private Long id;

	private String name;

	public static IdentityPosition of(Long id) {
		IdentityPosition identityPosition = new IdentityPosition();
		identityPosition.setId(id);
		identityPosition.setName("Identity");
		return identityPosition;
	}

	public IdentityPosition() {
		this.name = "Identity";
	}
}
