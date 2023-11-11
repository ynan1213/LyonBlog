package com.ynan.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 * @author yuannan
 */
@JsonTypeInfo(use = Id.NAME, property = "name")
@JsonSubTypes(value = {
		@JsonSubTypes.Type(name = "Position", value = Position.class),
		@JsonSubTypes.Type(name = "Counter", value = CounterPosition.class),
		@JsonSubTypes.Type(name = "Identity", value = IdentityPosition.class)
})
public interface StockPosition {

}
