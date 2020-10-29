package com.ths.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ths.entity.StockThsGnInfo;
import com.ths.util.CommonQueryBean;

/**
 * 
 * StockThsGnInfo数据库操作接口类
 * 
 **/

@Repository
public interface StockThsGnInfoDao {

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	StockThsGnInfo selectByPrimaryKey(@Param("id") Long id);

	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	int deleteByPrimaryKey(@Param("id") Long id);

	/**
	 * 
	 * 添加
	 * 
	 **/
	int insert(StockThsGnInfo record);

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	int updateByPrimaryKeySelective(StockThsGnInfo record);

	/**
	 * 
	 * list分页查询
	 * 
	 **/
	List<StockThsGnInfo> list4Page(@Param("record") StockThsGnInfo record,
			@Param("commonQueryParam") CommonQueryBean query);

	/**
	 * 
	 * count查询
	 * 
	 **/
	long count(@Param("record") StockThsGnInfo record);

	/**
	 * 
	 * list查询
	 * 
	 **/
	List<StockThsGnInfo> list(@Param("record") StockThsGnInfo record);

}