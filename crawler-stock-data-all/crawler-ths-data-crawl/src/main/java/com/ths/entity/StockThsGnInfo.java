package com.ths.entity;

import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * 同花顺的概念个股数据
 * 
 **/
public class StockThsGnInfo implements java.io.Serializable {


  private static final long serialVersionUID = 1L;


  /****/

  private Long id;


  /**概念名**/

  private String gnName;


  /**概念的code**/

  private String gnCode;


  /**股票code**/

  private String stockCode;


  /**股票名**/

  private String stockName;


  /**现价**/

  private BigDecimal stockPrice;


  /**涨跌幅（单位百分比：%）**/

  private BigDecimal stockChange;


  /**涨跌价格**/

  private BigDecimal stockChangePrice;


  /**涨跌速（单位百分比：%）**/

  private BigDecimal stockChangeSpeed;


  /**换手率（单位百分比：%）**/

  private BigDecimal stockHandoverScale;


  /**量比**/

  private BigDecimal stockLiangBi;


  /**振幅（单位百分比：%）**/

  private BigDecimal stockAmplitude;


  /**成交额(单位：万)**/

  private BigDecimal stockDealAmount;


  /**流通股票量（单位:万）**/

  private BigDecimal stockFlowStockNumber;


  /**流通市值（单位：万）**/

  private BigDecimal stockFlowMakertValue;


  /**市盈率**/

  private BigDecimal stockMarketTtm;


  /**发起抓取时间**/

  private String crawlerTime;


  /**当前抓取的版本**/

  private String crawlerVersion;


  /**扩展数据**/

  private String someExt;


  /****/

  private Date createTime;


  /****/

  private Date updateTime;




  public void setId(Long id) { 
  }


  public Long getId() { 
  }


  public void setGnName(String gnName) { 
  }


  public String getGnName() { 
  }


  public void setGnCode(String gnCode) { 
  }


  public String getGnCode() { 
  }


  public void setStockCode(String stockCode) { 
  }


  public String getStockCode() { 
  }


  public void setStockName(String stockName) { 
  }


  public String getStockName() { 
  }


  public void setStockPrice(BigDecimal stockPrice) { 
  }


  public BigDecimal getStockPrice() { 
  }


  public void setStockChange(BigDecimal stockChange) { 
  }


  public BigDecimal getStockChange() { 
  }


  public void setStockChangePrice(BigDecimal stockChangePrice) { 
  }


  public BigDecimal getStockChangePrice() { 
  }


  public void setStockChangeSpeed(BigDecimal stockChangeSpeed) { 
  }


  public BigDecimal getStockChangeSpeed() { 
  }


  public void setStockHandoverScale(BigDecimal stockHandoverScale) { 
  }


  public BigDecimal getStockHandoverScale() { 
  }


  public void setStockLiangBi(BigDecimal stockLiangBi) { 
  }


  public BigDecimal getStockLiangBi() { 
  }


  public void setStockAmplitude(BigDecimal stockAmplitude) { 
  }


  public BigDecimal getStockAmplitude() { 
  }


  public void setStockDealAmount(BigDecimal stockDealAmount) { 
  }


  public BigDecimal getStockDealAmount() { 
  }


  public void setStockFlowStockNumber(BigDecimal stockFlowStockNumber) { 
  }


  public BigDecimal getStockFlowStockNumber() { 
  }


  public void setStockFlowMakertValue(BigDecimal stockFlowMakertValue) { 
  }


  public BigDecimal getStockFlowMakertValue() { 
  }


  public void setStockMarketTtm(BigDecimal stockMarketTtm) { 
  }


  public BigDecimal getStockMarketTtm() { 
  }


  public void setCrawlerTime(String crawlerTime) { 
  }


  public String getCrawlerTime() { 
  }


  public void setCrawlerVersion(String crawlerVersion) { 
  }


  public String getCrawlerVersion() { 
  }


  public void setSomeExt(String someExt) { 
  }


  public String getSomeExt() { 
  }


  public void setCreateTime(Date createTime) { 
  }


  public Date getCreateTime() { 
  }


  public void setUpdateTime(Date updateTime) { 
  }


  public Date getUpdateTime() { 
  }

}