package com.ths.entity;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StockDfcfFundFlowInfo {

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 股市名
	 */
	private String stockMarket;

	/**
	 * 排行榜名
	 */
	private String stockRank;

	/**
	 * 股票代码
	 */
	private String stockCode;

	/**
	 * 股票名字
	 */
	private String stockName;

	/**
	 * 股票最新价格
	 */
	private BigDecimal priceNew;

	/**
	 * 涨跌幅
	 */
	private BigDecimal stockChange;

	/**
	 * 主力净流入--净额
	 */
	private BigDecimal mainNetInflowAmount;

	/**
	 * 主力净流入--净占比
	 */
	private BigDecimal mainNetProportion;

	/**
	 * 超大单净流入--净额
	 */
	private BigDecimal superBigPartNetInflowAmount;

	/**
	 * 超大单净流入--净占比
	 */
	private BigDecimal superBigPartNetProportion;

	/**
	 * 大单净流入--净额
	 */
	private BigDecimal bigPartNetInflowAmount;

	/**
	 * 大单净流入--净占比
	 */
	private BigDecimal bigPartNetProportion;

	/**
	 * 中单净流入--净额
	 */
	private BigDecimal middlePartNetInflowAmount;

	/**
	 * 中单净流入--净占比
	 */
	private BigDecimal middlePartNetProportion;

	/**
	 * 小单净流入--净额
	 */
	private BigDecimal litterPartNetInflowAmount;

	/**
	 * 小单净流入--净占比
	 */
	private BigDecimal litterPartNetProportion;

	/**
	 * 统计数据时间
	 */
	private Date countTime;

	/**
	 * 当前是第几页
	 */
	private int stockPage;

	/**
	 * 批次号
	 */
	private String timeVersion;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 扩展数据
	 */
	private String someinfo;

	private String crawlerVersion;

	private static final long serialVersionUID = 22222223213111L;

}
