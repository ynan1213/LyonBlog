package com.ynan.service;

import com.ynan.dto.StockOperation;
import com.ynan.model.BusinessContext;

/**
 * @author yuannan
 */
public interface ExecutorGatewayService {

	void doStockOperate(StockOperation stockOperation, BusinessContext businessContext);

}
