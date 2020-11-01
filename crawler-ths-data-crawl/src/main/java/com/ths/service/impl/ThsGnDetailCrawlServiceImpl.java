package com.ths.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ths.constant.Constants;
import com.ths.dao.StockBoxMapper;
import com.ths.dao.StockThsGnInfoMapper;
import com.ths.entity.StockBox;
import com.ths.entity.StockThsGnInfo;
import com.ths.service.ThsGnDetailCrawlService;

@Service
public class ThsGnDetailCrawlServiceImpl implements ThsGnDetailCrawlService {
	private final static Logger LOGGER = LoggerFactory.getLogger(ThsGnDetailCrawlServiceImpl.class);

	/**
	 * 阻塞队列
	 */
	private ArrayBlockingQueue<HashMap<String, String>> arrayBlockingQueue = new ArrayBlockingQueue<>(1000);

	@Autowired
	private StockThsGnInfoMapper stockThsGnInfoMapper;
	@Autowired
	private StockBoxMapper stockBoxMapper;

	@Override
	public void putAllArrayBlockingQueue(List<HashMap<String, String>> list) {
		if (!CollectionUtils.isEmpty(list)) {
			arrayBlockingQueue.addAll(list);
		}
	}

	@Override
	public void ConsumeCrawlerGnDetailData(int threadNumber) {
		for (int i = 0; i < threadNumber; ++i) {
			LOGGER.info("开启线程第[{}]个消费", i);
			new Thread(new crawlerGnDataThread()).start();
		}
		LOGGER.info("一共开启线程[{}]个消费", threadNumber);
	}

	class crawlerGnDataThread implements Runnable {

		@Override
		public void run() {
			try {
				while (true) {
					Map<String, String> map = arrayBlockingQueue.take();
					String url = map.get("url");
					String gnName = map.get("gnName");
					String crawlerDateStr = new SimpleDateFormat("yyyy-MM-dd HH:00:00").format(new Date());
					// chromederiver存放位置
					System.setProperty("webdriver.chrome.driver", Constants.CHROMEDRIVER_STRING);
					ChromeOptions options = new ChromeOptions();
					// 无界面参数
					// options.addArguments("headless");
					// 禁用沙盒 就是被这个参数搞了一天
					// options.addArguments("no-sandbox");
					WebDriver webDriver = new ChromeDriver(options);
					try {
						webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
						webDriver.get(url);
						Thread.sleep(1000L);
						String oneGnHtml = webDriver.getPageSource();
						LOGGER.info("当前概念：[{}],html数据为[{}]", gnName, oneGnHtml);
						LOGGER.info(oneGnHtml);
						// TODO 解析并存储数据
						parseHtmlAndInsertData(webDriver, oneGnHtml, gnName, crawlerDateStr);
						clicktoOneGnNextPage(webDriver, oneGnHtml, gnName, crawlerDateStr);
					} catch (Exception e) {
						LOGGER.error("用chromerDriver抓取数据，出现异常，url为[{}],异常为[{}]", url, e);
					} finally {
						webDriver.close();
						webDriver.quit();
					}
				}
			} catch (Exception e) {
				LOGGER.error("阻塞队列出现循环出现异常:", e);
			}
		}
	}

	public void parseHtmlAndInsertData(WebDriver webDriver, String html, String gnName, String crawlerDateStr) {
		Document document = Jsoup.parse(html);
//        Element boardElement = document.getElementsByClass("board-hq").get(0);
//        String gnCode = boardElement.getElementsByTag("h3").get(0).getElementsByTag("span").get(0).text();

		Element table = document.getElementsByClass("m-pager-table").get(0);
		Element tBody = table.getElementsByTag("tbody").get(0);
		Elements trs = tBody.getElementsByTag("tr");
		for (Element tr : trs) {
			try {
				Elements tds = tr.getElementsByTag("td");
				String stockCode = tds.get(1).text();
				String stockName = tds.get(2).text();
				if (stockCode.startsWith("688") || stockCode.startsWith("3")) {
					continue;
				}
				BigDecimal stockPrice = parseValueToBigDecimal(tds.get(3).text());
				BigDecimal stockChange = parseValueToBigDecimal(tds.get(4).text());
				BigDecimal stockChangePrice = parseValueToBigDecimal(tds.get(5).text());
				BigDecimal stockChangeSpeed = parseValueToBigDecimal(tds.get(6).text());
				BigDecimal stockHandoverScale = parseValueToBigDecimal(tds.get(7).text());
				BigDecimal stockLiangBi = parseValueToBigDecimal(tds.get(8).text());
				BigDecimal stockAmplitude = parseValueToBigDecimal(tds.get(9).text());
				BigDecimal stockDealAmount = parseValueToBigDecimal(tds.get(10).text());
				BigDecimal stockFlowStockNumber = parseValueToBigDecimal(tds.get(11).text());
				BigDecimal stockFlowMakertValue = parseValueToBigDecimal(tds.get(12).text());
				BigDecimal stockMarketTtm = parseValueToBigDecimal(tds.get(13).text());
				// 存储数据
				StockThsGnInfo stockThsGnInfo = new StockThsGnInfo();
				stockThsGnInfo.setGnName(gnName);
				stockThsGnInfo.setGnCode(null);
				stockThsGnInfo.setStockCode(stockCode);
				stockThsGnInfo.setStockName(stockName);
				stockThsGnInfo.setStockPrice(stockPrice);
				stockThsGnInfo.setStockChange(stockChange);
				stockThsGnInfo.setStockChangePrice(stockChangePrice);
				stockThsGnInfo.setStockChangeSpeed(stockChangeSpeed);
				stockThsGnInfo.setStockHandoverScale(stockHandoverScale);
				stockThsGnInfo.setStockLiangBi(stockLiangBi);
				stockThsGnInfo.setStockAmplitude(stockAmplitude);
				stockThsGnInfo.setStockDealAmount(stockDealAmount);
				stockThsGnInfo.setStockFlowStockNumber(stockFlowStockNumber);
				stockThsGnInfo.setStockFlowMakertValue(stockFlowMakertValue);
				stockThsGnInfo.setStockMarketTtm(stockMarketTtm);
				stockThsGnInfo.setCrawlerTime(crawlerDateStr);
				stockThsGnInfo.setCrawlerVersion("同花顺概念板块#" + crawlerDateStr);
				stockThsGnInfo.setCreateTime(new Date());
				stockThsGnInfo.setUpdateTime(new Date());
				System.out.println(JSONObject.toJSONString(stockThsGnInfo));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				StockThsGnInfo existInfo = stockThsGnInfoMapper
						.selectOne(new QueryWrapper<StockThsGnInfo>(new StockThsGnInfo().setStockName(stockName)));
				if (existInfo == null) {
					stockThsGnInfoMapper.insert(stockThsGnInfo);
					clicktoStockDetail(stockCode, stockName, webDriver);
				} else {
					stockThsGnInfo.setId(existInfo.getId());
					if (!existInfo.getGnName().contains(stockThsGnInfo.getGnName())) {
						stockThsGnInfo.setGnName(existInfo.getGnName() + "," + stockThsGnInfo.getGnName());
					}
					stockThsGnInfoMapper.updateById(stockThsGnInfo);
				}

			} catch (Exception e) {
				LOGGER.error("插入同花顺概念板块数据出现异常:", e);
			}

		}
	}

	public BigDecimal parseValueToBigDecimal(String value) {
		if (StringUtils.isEmpty(value)) {
			return BigDecimal.ZERO;
		} else if ("--".equals(value)) {
			return BigDecimal.ZERO;
		} else if (value.endsWith("亿")) {
			return new BigDecimal(value.substring(0, value.length() - 1)).multiply(BigDecimal.ONE);
		}
		return new BigDecimal(value);
	}

	public boolean clicktoOneGnNextPage(WebDriver webDriver, String oneGnHtml, String key, String crawlerDateStr)
			throws InterruptedException {
		// 是否包含下一页
		String pageNumber = includeNextPage(oneGnHtml);
		if (!StringUtils.isEmpty(pageNumber)) {
			WebElement nextPageElement = webDriver.findElement(By.linkText("下一页"));
			webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			nextPageElement.click();
			Thread.sleep(700);
			String nextPageHtml = webDriver.getPageSource();
			LOGGER.info("下一页：");
			LOGGER.info(nextPageHtml);
			// TODO 解析并存储数据
			parseHtmlAndInsertData(webDriver, nextPageHtml, key, crawlerDateStr);
			clicktoOneGnNextPage(webDriver, nextPageHtml, key, crawlerDateStr);
		}
		return true;
	}

	public void clicktoStockDetail(String stockCode, String stockName, WebDriver webDriver)
			throws InterruptedException {
		try {
			// 板块页
			String gnWindow = webDriver.getWindowHandle();
			// 点击股票进入详情页
			WebElement stockDetail = webDriver.findElement(By.linkText(stockCode));
			stockDetail.click();
			Set<String> gnWindows = webDriver.getWindowHandles();
			for (String win : gnWindows) {
				if (!gnWindow.equals(win)) {
					webDriver.switchTo().window(win);
					break;
				}
			}
			// 股票详情页
			String stockDetailWindows = webDriver.getWindowHandle();
			WebElement gloabel = webDriver.findElement(By.linkText("全球市场"));
			gloabel.click();
			Set<String> wgnWindows = webDriver.getWindowHandles();
			for (String win : wgnWindows) {
				if (!gnWindow.equals(win) && !stockDetailWindows.equals(win)) {
					webDriver.switchTo().window(win);
					break;
				}
			}
			String tempWindow = webDriver.getWindowHandle();

			// 在当前页打开k线图
			webDriver.get("http://stockpage.10jqka.com.cn/HQ_v4.html#hs_" + stockCode);
			WebElement kLine = webDriver.findElement(By.linkText("日K线"));
			kLine.click();
			webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			WebElement wLine = webDriver.findElement(By.linkText("周K线"));
			wLine.click();
			webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			WebElement mLine = webDriver.findElement(By.linkText("月K线"));
			mLine.click();

			webDriver.switchTo().window(stockDetailWindows);
			webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			webDriver.get("http://d.10jqka.com.cn/v6/line/hs_" + stockCode + "/01/all.js");

			String kLineData = webDriver.getPageSource();
			Document document = Jsoup.parse(kLineData);
			Element table = document.getElementsByTag("body").get(0);
			Element pre = table.getAllElements().get(1);
			String preData = pre.text();
			String jsonData = preData.substring(preData.indexOf("(") + 1, preData.length() - 1);
			JSONObject dataJsonObject = JSONObject.parseObject(jsonData);
			int priceFactor = dataJsonObject.getIntValue("priceFactor");
			String[] price = dataJsonObject.getString("price").split(",");
			String[] dates = dataJsonObject.getString("dates").split(",");
//			dataJsonObject.getJSONArray("sortYear").get(0);
			String year = "2020";
			int limit = 170;// 只处理至今前170条数据
			int t = price.length / 4 - limit;
			int size = t > 0 ? limit : price.length / 4;
			int index = t < 0 ? 0 : t * 4;
			int j = 0;
			StockBox[] boxs = new StockBox[size];
			for (int i = index; i < price.length; i += 4) {
				BigDecimal low = new BigDecimal(Integer.parseInt(price[i])).divide(new BigDecimal(priceFactor));
				BigDecimal start = new BigDecimal(Integer.parseInt(price[i]) + Integer.parseInt(price[i + 1]))
						.divide(new BigDecimal(priceFactor));
				BigDecimal high = new BigDecimal(Integer.parseInt(price[i]) + Integer.parseInt(price[i + 2]))
						.divide(new BigDecimal(priceFactor));
				BigDecimal end = new BigDecimal(Integer.parseInt(price[i]) + Integer.parseInt(price[i + 3]))
						.divide(new BigDecimal(priceFactor));
				BigDecimal updown = null;
				BigDecimal increase = null;
				if (j > 0) {
					updown = end.subtract(boxs[j - 1].getEnd());
					increase = updown.divide(boxs[j - 1].getEnd(), 2, BigDecimal.ROUND_HALF_UP);
				} else {
					updown = new BigDecimal(0);
					increase = new BigDecimal(0);
				}

				System.out.println("date=" + dates[i / 4] + " low=" + low + " high=" + high + " start=" + start
						+ " end=" + end + " updown=" + updown);
				StockBox box = new StockBox();
				box.setStockCode(stockCode);
				box.setStockName(stockName);
				box.setEnd(end);
				box.setStart(start);
				box.setHigh(high);
				box.setLow(low);
				box.setIncrease(increase);
				box.setUpdown(updown);
				box.setKDate(year + dates[i / 4]);
				StockBox exiStockBox = stockBoxMapper.selectOne(
						new QueryWrapper<StockBox>(new StockBox().setStockCode(stockCode).setKDate(box.getKDate())));
				if (exiStockBox == null) {
					stockBoxMapper.insert(box);
				}
				boxs[j] = box;
				j++;
			}
			webDriver.close();
			webDriver.switchTo().window(tempWindow);
			webDriver.close();
			// 切回板块页
			webDriver.switchTo().window(gnWindow);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String includeNextPage(String html) {
		Document document = Jsoup.parse(html);
		List<Element> list = document.getElementsByTag("a");
		for (Element element : list) {
			String a = element.text();
			if ("下一页".equals(a)) {
				String pageNumber = element.attr("page");
				return pageNumber;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		String[] price = "44603,2197,9085,5207,45600,2913,3697,400,43306,494,4194,3393".split(",");
		String[] dates = "1027,1028,1029".split(",");
		String year = "2020";
		int priceFactor = 100;

		int limit = 5;
		int t = price.length / 4 - limit;
		int size = t > 0 ? limit : price.length / 4;
		int index = t < 0 ? 0 : t * 4;
		int j = 0;
		StockBox[] boxs = new StockBox[size];
		for (int i = index; i < price.length; i += 4) {
			BigDecimal low = new BigDecimal(Integer.parseInt(price[i])).divide(new BigDecimal(priceFactor));
			BigDecimal start = new BigDecimal(Integer.parseInt(price[i]) + Integer.parseInt(price[i + 1]))
					.divide(new BigDecimal(priceFactor));
			BigDecimal high = new BigDecimal(Integer.parseInt(price[i]) + Integer.parseInt(price[i + 2]))
					.divide(new BigDecimal(priceFactor));
			BigDecimal end = new BigDecimal(Integer.parseInt(price[i]) + Integer.parseInt(price[i + 3]))
					.divide(new BigDecimal(priceFactor));
			BigDecimal updown = null;
			BigDecimal increase = null;
			if (j > 0) {
				updown = end.subtract(boxs[j - 1].getEnd());
				increase = updown.divide(boxs[j - 1].getEnd(), 2, BigDecimal.ROUND_HALF_UP);
			} else {
				updown = new BigDecimal(0);
				increase = new BigDecimal(0);
			}

			System.out.println("date=" + dates[i / 4] + " low=" + low + " high=" + high + " start=" + start + " end="
					+ end + " updown=" + updown);
			StockBox box = new StockBox();
			box.setEnd(end);
			box.setStart(start);
			box.setHigh(high);
			box.setLow(low);
			box.setIncrease(increase);
			box.setUpdown(updown);
			box.setKDate(year + dates[i / 4]);
			boxs[j] = box;
			j++;
		}
		System.out.println("success");
	}

}
