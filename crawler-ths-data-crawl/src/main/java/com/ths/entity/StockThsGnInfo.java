package com.ths.entity;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 
 * 同花顺的概念个股数据
 * 
 **/
@Data
@Accessors(chain = true)
public class StockThsGnInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/****/

	private Long id;

	/** 概念名 **/

	private String gnName;

	/** 概念的code **/

	private String gnCode;

	/** 股票code **/

	private String stockCode;

	/** 股票名 **/

	private String stockName;

	/** 现价 **/

	private BigDecimal stockPrice;

	/** 涨跌幅（单位百分比：%） **/

	private BigDecimal stockChange;

	/** 涨跌价格 **/

	private BigDecimal stockChangePrice;

	/** 涨跌速（单位百分比：%） **/

	private BigDecimal stockChangeSpeed;

	/** 换手率（单位百分比：%） **/

	private BigDecimal stockHandoverScale;

	/** 量比 **/

	private BigDecimal stockLiangBi;

	/** 振幅（单位百分比：%） **/

	private BigDecimal stockAmplitude;

	/** 成交额(单位：万) **/

	private BigDecimal stockDealAmount;

	/** 流通股票量（单位:万） **/

	private BigDecimal stockFlowStockNumber;

	/** 流通市值（单位：万） **/

	private BigDecimal stockFlowMakertValue;

	/** 市盈率 **/

	private BigDecimal stockMarketTtm;

	/** 发起抓取时间 **/

	private String crawlerTime;

	/** 当前抓取的版本 **/

	private String crawlerVersion;

	/** 扩展数据 **/

	private String someExt;

	/****/

	private Date createTime;

	/****/

	private Date updateTime;

}
