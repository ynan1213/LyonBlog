package com.ynan.mappers;

import com.ynan.dto.StockLocator;
import com.ynan.entity.Stock;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author yuannan
 */
public interface StockMapper {

	/**
	 * 根据四要素查货架库存
	 */
	Stock selectByLocator(@Param("locator") StockLocator stockLocator);

	int insertSelective(Stock stock);

	/**
	 * 增加可用库存
	 */
	int plusStockAvailableNum(@Param("locator") StockLocator locator,
			@Param("plusNum") BigDecimal plusNum);

	/**
	 * 扣减可用库存
	 */
	int reduceStockAvailableNum(@Param("locator") StockLocator locator,
			@Param("reduceNum") BigDecimal reduceNum);

	int verifyAndReduceStockAvailableNum(@Param("locator") StockLocator locator,
			@Param("reduceNum") BigDecimal reduceNum,
			@Param("verifyNum") BigDecimal verifyNum);

	/**
	 * 扣减锁定库存
	 */
	int reduceStockHoldNum(@Param("locator") StockLocator locator,
			@Param("reduceNum") BigDecimal reduceNum);

	int verifyAndReduceStockHoldNum(@Param("locator") StockLocator locator,
			@Param("reduceNum") BigDecimal reduceNum,
			@Param("verifyNum") BigDecimal verifyNum);

	/**
	 * 增加锁定库存
	 */
	int plusStockHoldNum(@Param("locator") StockLocator locator,
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

	/**
	 * 根据sku查询可用库存
	 */
	List<Stock> selectAvailableBySkuId(@Param("skuId") Long skuId, @Param("warehouseId") Long warehouseId,
			@Param("specLocIds") List<Long> specLocIds, @Param("specPartIds") List<Long> specPartIds);

	List<Stock> selectAvailableByCondition(@Param("condition") String condition);

	List<Stock> selectByLocationSku(@Param("stkLoc") String stockLocationSku);

	/**
	 * 查询库存
	 */
	List<Stock> selectByCondition(@Param("condition") String stockQueryCondition);

	/**
	 * 是否存在库存量
	 *
	 * @param locId 库位ID
	 * @param warehouseId 仓库ID
	 * @return 是否存在库存标记
	 */
	Stock selectOneExistStock(@Param("locId") Long locId, @Param("warehouseId") Long warehouseId);

	/**
	 * 批量查询汇总库位商品库存
	 *
	 * @param locIdList 库位ID
	 * @param warehouseId 仓库ID
	 * @return 商品库位库存
	 */
	List<?> multiGetSummarySkuStock(@Param("locIdList") List<Long> locIdList,
			@Param("warehouseId") Long warehouseId);
}