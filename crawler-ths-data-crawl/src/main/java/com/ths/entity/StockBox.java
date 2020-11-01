package com.ths.entity;

import java.math.BigDecimal;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StockBox {

	private Integer id;

	private String stockCode;

	private String stockName;

	/**
	 * k线日
	 */
	private String kDate;
	/**
	 * 开
	 */
	private BigDecimal start;
	/**
	 * 高
	 */
	private BigDecimal high;
	/**
	 * 低
	 */
	private BigDecimal low;
	/**
	 * 收
	 */
	private BigDecimal end;
	/**
	 * 涨跌值
	 */
	private BigDecimal updown;
	/**
	 * 涨跌幅
	 */
	private BigDecimal increase;
}
