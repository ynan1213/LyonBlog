package com.ynan.mappers;

import com.ynan.dto.StockLocator;
import com.ynan.entity.PendingStock;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author yuannan
 */
public interface PendingStockMapper {

	int insertSelective(PendingStock pendingStock);

	int reduce(@Param("locator") StockLocator locator,
			@Param("reduceNum") BigDecimal reduceNum);

	int verifyAndReduce(@Param("locator") StockLocator locator,
			@Param("reduceNum") BigDecimal reduceNum,
			@Param("verifyNum") BigDecimal verifyNum);

	int plus(@Param("locator") StockLocator locator,
			@Param("plusNum") BigDecimal plusNum);

	/**
	 * 查询数量为零的库存记录
	 */
	List<Long> selectZeroRecord(@Param("whId") Long whId,
			@Param("endTime") Date endTime,
			@Param("limit") Integer limit);

	/**
	 * 物理删除数量为零的库存记录
	 */
	int deleteZeroRecord(@Param("ids") List<Long> ids,
			@Param("whId") Long whId,
			@Param("endTime") Date endTime);

	PendingStock selectByLocator(@Param("locator") StockLocator stockLocator);

	/**
	 * 根据条件统计货品数量
	 */
	double selectSum(PendingStock queryPending);

	/**
	 * 根据条件查询库存
	 */
	List<PendingStock> selectByCondition(@Param("condition") String stockQueryCondition);

	/**
	 * 是否存在库存量
	 *
	 * @param locId 库位ID
	 * @param warehouseId 仓库ID
	 * @return 是否存在库存标记
	 */
	PendingStock selectOneExistStock(@Param("locId") Long locId, @Param("warehouseId") Long warehouseId);

	/**
	 * 批量汇总查询接口
	 *
	 * @param locIdList 库位ID
	 * @param warehouseId 仓库ID
	 * @return 库位商品汇总库存
	 */
	List<?> multiGetSummarySkuStock(@Param("locIdList") List<Long> locIdList,
			@Param("warehouseId") Long warehouseId);
}
