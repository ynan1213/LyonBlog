package com.ynan.executor;

import com.ynan.model.BusinessContext;
import com.ynan.operation.PlusOperation;
import com.ynan.operation.ReduceOperation;

/**
 * @author yuannan
 */
public interface StockExecutor {

	/**
	 *
	 */
	boolean reduce(ReduceOperation reduceOperation, BusinessContext businessContext);

	/**
	 *
	 */
	boolean plus(PlusOperation plusOperation, BusinessContext businessContext);

}
