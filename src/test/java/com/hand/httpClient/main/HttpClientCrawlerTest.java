package com.hand.httpClient.main;

import org.junit.Test;

import static org.junit.Assert.*;

public class HttpClientCrawlerTest {

    @Test
    public void crawling() {
        String[] htmlUrls = {"http://www.huitu.com/topic-detail/1659.html?recType=0"};
        HttpClientCrawler httpClientCrawler = new HttpClientCrawler();
        httpClientCrawler.crawling(htmlUrls);
    }
}