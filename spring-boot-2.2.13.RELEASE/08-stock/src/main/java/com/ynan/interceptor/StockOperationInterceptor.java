package com.ynan.interceptor;

import com.ynan.dto.StockOperation;
import com.ynan.model.BusinessContext;

/**
 * @author yuannan
 */
public interface StockOperationInterceptor {

	void doIntercept(StockOperation stockOperation, BusinessContext businessContext);
}
