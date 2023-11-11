package com.ynan.dto;

import com.ynan.dictionary.StockCounterTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author yuannan
 */
@Data
@Accessors(chain = true)
public class CounterPosition extends Position {

	private String name;

	private StockCounterTypeEnum counterType;

	public CounterPosition() {
		this.name = "Counter";
	}

	public CounterPosition(Long locId, StockCounterTypeEnum counterType) {
		super(locId, null);
		this.counterType = counterType;
		this.name = "Counter";
	}

	public CounterPosition(Long locId, String lpnNo, StockCounterTypeEnum counterType) {
		super(locId, lpnNo);
		this.counterType = counterType;
		this.name = "Counter";
	}
}