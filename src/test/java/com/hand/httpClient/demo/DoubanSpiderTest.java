package com.hand.httpClient.demo;

import org.junit.Test;

import static org.junit.Assert.*;

public class DoubanSpiderTest {

    @Test
    public void getHtmlByUrl() {
        String htmlUrl = "https://blog.csdn.net/";
        DoubanSpider.getHtmlByUrl(htmlUrl);
    }
}