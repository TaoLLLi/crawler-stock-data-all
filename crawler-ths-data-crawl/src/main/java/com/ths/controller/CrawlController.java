package com.ths.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ths.service.ThsGnCrawlService;
import com.ths.service.ThsGnDetailCrawlService;

@Controller
public class CrawlController {

	@Autowired
	private ThsGnCrawlService thsGnCrawlService;

	@Autowired
	private ThsGnDetailCrawlService thsGnDetailCrawlService;

	@RequestMapping("/test")
	@ResponseBody
	public void test() {
		// 抓取所有概念板块的url
		List<HashMap<String, String>> list = thsGnCrawlService.ThsGnCrawlListUrl();
		// 放入阻塞队列
		thsGnDetailCrawlService.putAllArrayBlockingQueue(list);
		// 对线程抓取
		thsGnDetailCrawlService.ConsumeCrawlerGnDetailData(1);
	}

	@RequestMapping("/test2")
	@ResponseBody
	public void teya1(String gnUrl, String gnName) {
		thsGnDetailCrawlService.ConsumeCrawlerGn(gnUrl, gnName);
	}

}
