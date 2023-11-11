package com.ynan.service;

import com.ynan.model.BusinessContext;
import com.ynan.request.*;
import com.ynan.response.AllocateStockResult;

/**
 * @author yuannan
 */
public interface StockOperationService {

	/**
	 * 增加总库存
	 * 收货或盘点使用
	 *
	 * @return 批次No
	 */
	boolean addStock(AddStockRequest addStockRequest, BusinessContext businessContext);

	/**
	 * 扣减总库存
	 * 出库或盘点使用
	 */
	boolean reduceStock(ReduceStockRequest reduceStockRequest, BusinessContext businessContext);

	/**
	 * 库存转移
	 * 上架、拣货、分拣、移位
	 */
	boolean transferStock(TransferStockRequest transferStockRequest, BusinessContext businessContext);

	/**
	 * 根据预占id 转移库存
	 */
	boolean transferStockByLock(TransferStockByAllocateRequest request, BusinessContext businessContext);

	/**
	 * 锁定库存
	 */
	AllocateStockResult allocateStock(AllocateStockRequest allocateStockRequest, BusinessContext businessContext);

	/**
	 * 根据锁定id冻结库存
	 */
	//	boolean freezeStockByLock(FreezeStockByLockRequest request, BusinessContext businessContext);

	/**
	 * 冻结库存
	 */
	//	boolean freezeStock(FreezeStockRequest request, BusinessContext businessContext);

	/**
	 * 释放预占库存
	 */
	boolean releaseStock(ReleaseStockRequest releaseStockRequest, BusinessContext businessContext);

	/**
	 * 通用转移（fromOperand和toOperand不由processType唯一确定）
	 */
	boolean commonTransferStock(CommonTransferStockRequest request, BusinessContext businessContext);

	/**
	 * 通用增加库存
	 */
	boolean commonAddStock(CommonAddStockRequest request, BusinessContext businessContext);

	/**
	 * 通用扣减库存
	 */
	boolean commonReduceStock(CommonReduceStockRequest request, BusinessContext businessContext);
}
