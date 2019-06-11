package com.hand.httpClient.demo;

import org.junit.Test;

import static org.junit.Assert.*;

public class DoubanSpiderTest {

    @Test
    public void getHtmlByUrl() {
        String htmlUrl = "https://www.douban.com/";
        DoubanSpider.getHtmlByUrl(htmlUrl);
    }
}