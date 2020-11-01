package com.ths.test;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSONObject;
import com.ths.constant.Constants;
import com.ths.service.ThsGnCrawlService;
import com.ths.service.ThsGnDetailCrawlService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlTest {

	@Autowired
	private ThsGnCrawlService thsGnCrawlService;
	@Autowired
	private ThsGnDetailCrawlService thsGnDetailCrawlService;

	@Test
	public void test1() {
		System.setProperty("webdriver.chrome.driver", Constants.CHROMEDRIVER_STRING);
		ChromeOptions options = new ChromeOptions();
		WebDriver webDriver = new ChromeDriver(options);
		try {
			// 根据网速设置，网速慢可以调低点
			webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			webDriver.get("http://q.10jqka.com.cn/gn/detail/code/300900/");
			String gnWindow = webDriver.getWindowHandle();
			// 获取同花顺概念页面的HTML
			WebElement stock = webDriver.findElement(By.linkText("石头科技"));
			webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			stock.click();
			Set<String> gnWindows = webDriver.getWindowHandles();
			for (String win : gnWindows) {
				if (!gnWindow.equals(win)) {
					webDriver.switchTo().window(win);
					break;
				}
			}
			webDriver.get("http://stockpage.10jqka.com.cn/HQ_v4.html#hs_688169");
			WebElement kLine = webDriver.findElement(By.linkText("日K线"));
			kLine.click();
			webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			WebElement wLine = webDriver.findElement(By.linkText("周K线"));
			wLine.click();
			webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			WebElement mLine = webDriver.findElement(By.linkText("月K线"));
			mLine.click();

			Set<String> back = webDriver.getWindowHandles();
			for (String win : back) {
				if (gnWindow.equals(win)) {
					webDriver.switchTo().window(win);
					break;
				}
			}
			webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			webDriver.get("http://d.10jqka.com.cn/v6/line/hs_688169/01/all.js");

			String kLineData = webDriver.getPageSource();
			Document document = Jsoup.parse(kLineData);
			Element table = document.getElementsByTag("body").get(0);
			Element pre = table.getAllElements().get(1);
			String preData = pre.text();
			String jsonData = preData.substring(preData.indexOf("(") + 1, preData.length() - 1);
			JSONObject dataJsonObject = JSONObject.parseObject(jsonData);
			int priceFactor = dataJsonObject.getIntValue("priceFactor");
			String price = dataJsonObject.getString("price");
			String dates = dataJsonObject.getString("dates");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			webDriver.close();
			webDriver.quit();
		}
	}

}
