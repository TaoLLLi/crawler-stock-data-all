package com.ths.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.ths.config.StockCommonConfig;
import com.ths.entity.StockBox;
import com.ths.service.LineParseService;
import com.ths.util.OkHttpUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DayLineParseServiceImpl implements LineParseService {

	@Autowired
	private StockCommonConfig config;

	@Override
	public List<StockBox> getBoxLineArray(String stockCode, Integer size) throws Exception {

		String resultString = OkHttpUtils
				.doGet(String.format("http://d.10jqka.com.cn/v6/line/hs_%s/%s/all.js", stockCode, "01"));
		if (StringUtils.isEmpty(resultString)) {
			return null;
		}
		List<StockBox> boxs = new ArrayList<StockBox>();

		String dataString = resultString.substring(resultString.indexOf("(") + 1, resultString.length() - 1);
		JSONObject dataJsonObject = JSONObject.parseObject(dataString);
		int priceFactor = dataJsonObject.getIntValue("priceFactor");
		String price = dataJsonObject.getString("price");
		String dates = dataJsonObject.getString("dates");

		return boxs;
	}

	public static void main(String[] args) throws Exception {
	}

}
