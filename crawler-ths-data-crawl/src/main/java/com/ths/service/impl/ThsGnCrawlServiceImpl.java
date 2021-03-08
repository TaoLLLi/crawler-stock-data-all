package com.ths.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ths.constant.Constants;
import com.ths.service.ThsGnCrawlService;
import com.ths.service.ThsParseHtmlService;

@Service
public class ThsGnCrawlServiceImpl implements ThsGnCrawlService {
	private final static Logger LOGGER = LoggerFactory.getLogger(ThsGnCrawlServiceImpl.class);

	/**
	 * 
	 * 同花顺全部概念板块url
	 */
//	private final static String GN_URL = "http://q.10jqka.com.cn/gn/detail/code/301558/";
	private final static String GN_URL = "http://q.10jqka.com.cn/gn/";

	@Autowired
	private ThsParseHtmlService thsParseHtmlService;

	@Override
	public List<HashMap<String, String>> ThsGnCrawlListUrl() {
		System.setProperty("webdriver.chrome.driver", Constants.CHROMEDRIVER_STRING);
		ChromeOptions options = new ChromeOptions();
		// 是否启用浏览器界面的参数
		// 无界面参数
//        options.addArguments("headless");
		// 禁用沙盒 就是被这个参数搞了一天
//        options.addArguments("no-sandbox");
		WebDriver webDriver = new ChromeDriver(options);
		try {
			// 根据网速设置，网速慢可以调低点
			webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			webDriver.get(GN_URL);
			Thread.sleep(1000L);
			String gnWindow = webDriver.getWindowHandle();
			// 获取同花顺概念页面的HTML
			String thsGnHtml = webDriver.getPageSource();
			LOGGER.info("获取同花顺url:[{}]的html为:/n{}", GN_URL, thsGnHtml);
			return thsParseHtmlService.parseGnHtmlReturnGnUrlList(thsGnHtml);
		} catch (Exception e) {
			LOGGER.error("获取同花顺概念页面的HTML，出现异常:", e);
		} finally {
			webDriver.close();
			webDriver.quit();
		}
		return null;
	}
}
