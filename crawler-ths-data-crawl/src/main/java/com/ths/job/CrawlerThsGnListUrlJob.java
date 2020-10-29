package com.ths.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ths.service.ThsGnCrawlService;

@Component
public class CrawlerThsGnListUrlJob {

	@Autowired
	private ThsGnCrawlService thsGnCrawlService;

	@Scheduled(fixedRate = 10000)
	public void execute() {
//        System.out.println("111");
//        thsGnCrawlService.ThsGnCrawlListUrl();
	}

}
