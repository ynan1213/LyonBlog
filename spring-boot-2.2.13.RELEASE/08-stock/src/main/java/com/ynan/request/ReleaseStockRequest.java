package com.ynan.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yuannan
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReleaseStockRequest {

	/**
	 * 锁id
	 * 调用lockStock方法获得
	 */
	private Long allocateId;
}
