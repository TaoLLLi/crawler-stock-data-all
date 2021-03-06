package com.ths.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ths.entity.StockDfcfFundFlowInfo;

@Repository
public interface StockDfcfFundFlowInfoMapper extends BaseMapper<StockDfcfFundFlowInfo> {

	List<String> selectThreeDownFourUp(@Param(value = "oneDay") String oneDay, @Param(value = "twoDay") String twoDay,
			@Param(value = "threeDay") String threeDay, @Param(value = "fourDay") String fourDay);

	List<StockDfcfFundFlowInfo> selectByTimeVersion(@Param(value = "stockCode") String stockCode,
			@Param(value = "timeVersion") String timeVersion);

	String selectStockNameByCrawlerVersion(@Param(value = "stockCode") String stockCode,
			@Param(value = "timeVersion") String timeVersion);

	List<String> selectByCrawlerVersion(@Param(value = "crawlerVersion") String crawlerVersion);

	int insertSelective(StockDfcfFundFlowInfo record);

	StockDfcfFundFlowInfo selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(StockDfcfFundFlowInfo record);

}