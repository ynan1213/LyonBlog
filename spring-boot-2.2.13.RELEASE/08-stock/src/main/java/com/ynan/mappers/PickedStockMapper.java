package com.ynan.mappers;

import com.ynan.dto.StockLocator;
import com.ynan.entity.PickedStock;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Param;

/**
 * @author yuannan
 */
public interface PickedStockMapper {

	int insertSelective(PickedStock pickedStock);

	/**
	 * 扣减
	 */
	int reduce(@Param("locator") StockLocator locator,
			@Param("reduceNum") BigDecimal reduceNum);

	/**
	 *
	 */
	int verifyAndReduce(
			@Param("locator") StockLocator locator,
			@Param("reduceNum") BigDecimal reduceNum,
			@Param("verifyNum") BigDecimal verifyNum);

	/**
	 * 增加
	 */
	int plus(@Param("locator") StockLocator locator,
			@Param("plusNum") BigDecimal plusNum);

	/**
	 *
	 */
	int reduceHold(@Param("locator") StockLocator locator, @Param("reduceNum") BigDecimal reduceNum);

	/**
	 *
	 */
	int verifyAndReduceHold(@Param("locator") StockLocator locator, @Param("reduceNum") BigDecimal reduceNum,
			@Param("verifyNum") BigDecimal verifyNum);

	/**
	 *
	 */
	int plusHold(@Param("locator") StockLocator locator, @Param("plusNum") BigDecimal plusNum);

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
	 * 查询
	 */
	PickedStock selectByLocator(StockLocator stockLocator);

	/**
	 * 根据skuId查库存
	 */
	List<PickedStock> selectBySkuId(@Param("skuId") Long skuId,
			@Param("warehouseId") Long warehouseId);

	/**
	 *
	 */
	List<PickedStock> selectBySkuAndLpn(@Param("skuId") Long skuId,
			@Param("lpnNo") String lpnNo, @Param("warehouseId") Long warehouseId);

	/**
	 * @param skuId skuId
	 * @param lpnNoList lpnNoList
	 * @param warehouseId warehouseId
	 * @return List<PickedStock>
	 */
	List<PickedStock> selectBySkuAndLpnList(@Param("skuId") Long skuId,
			@Param("lpnNoList") List<String> lpnNoList, @Param("warehouseId") Long warehouseId);

	/**
	 * 按条件查询库存(多张库存表统一)
	 */
	List<PickedStock> selectByCondition(@Param("condition") String stockQueryCondition);

	/**
	 * 更新删除
	 */
	Integer batchUpdateDeleteFlag(@Param("ids") List<Long> ids, @Param("warehouseId") Long warehouseId,
			@Param("flag") Integer flag);


	/**
	 * 获取拣货暂存位-有标签-并且拣货数量大于零    商品的拣货数量sum
	 */
	List<?> batchPickedAndLabelSumStock(@Param("labelNos") Set<String> labelNos);

	/**
	 * 查询拣货暂存位存在的库存的数据
	 *
	 * @param lockId 库位ID
	 * @param warehouseId 仓库ID
	 * @param excludeLpnNos 排除掉的标签
	 * @return List<PickedStock>
	 */
	List<?> listPickExistStockGroupBySkuLpn(@Param("locId") Long lockId,
			@Param("warehouseId") Long warehouseId, @Param("excludeLpnNos") List<String> excludeLpnNos,
			@Param("includeLpnNos") List<String> includeLpnNos);

	/**
	 * 查询标签拣货暂存位存在的库存的数据
	 *
	 * @param lpnNo lpn
	 * @param warehouseId 仓库ID
	 * @return List<PickedLpnNoSkuStock>
	 */
	List<?> listByLpnExistStockGroupBySkuLpn(@Param("lpnNo") String lpnNo,
			@Param("locId") Long lockId, @Param("warehouseId") Long warehouseId);

	/**
	 * 是否存在库存量
	 *
	 * @param locId 库位ID
	 * @param warehouseId 仓库ID
	 * @return 是否存在库存标记
	 */
	PickedStock selectOneExistStock(@Param("locId") Long locId, @Param("warehouseId") Long warehouseId);


	/**
	 * 是否存在标签库存
	 *
	 * @param locId 库位ID
	 * @param lpnNo 标签号
	 * @param warehouseId 仓库ID
	 * @return 是否存在标签库存
	 */
	PickedStock selectOneExistLabelStock(@Param("locId") Long locId, @Param("lpnNo") String lpnNo,
			@Param("warehouseId") Long warehouseId);

	/**
	 * 批量查询汇总商品库存
	 *
	 * @param locIdList locId
	 * @param warehouseId warehouseId
	 * @return 商品库存
	 */
	List<PickedStock> multiGetSummarySkuStock(@Param("locIdList") List<Long> locIdList,
			@Param("warehouseId") Long warehouseId);
}
