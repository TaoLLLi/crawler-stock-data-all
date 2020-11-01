package com.ths.service;

import java.util.List;

import com.ths.entity.StockBox;

public interface LineParseService {

	List<StockBox> getBoxLineArray(String stockCode, Integer size) throws Exception;
}
