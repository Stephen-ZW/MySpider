package com.hand.httpClient.main;

import org.junit.Test;

import static org.junit.Assert.*;

public class HttpClientCrawlerTest {

    @Test
    public void crawling() {
        String[] htmlUrls = {"https://yiyicclub.com"};
        HttpClientCrawler httpClientCrawler = new HttpClientCrawler();
        httpClientCrawler.crawling(htmlUrls);
    }
}