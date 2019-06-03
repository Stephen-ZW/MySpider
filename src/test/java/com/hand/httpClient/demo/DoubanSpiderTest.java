package com.hand.httpClient.demo;

import org.junit.Test;

import static org.junit.Assert.*;

public class DoubanSpiderTest {

    @Test
    public void getHtmlByUrl() {
        String htmlUrl = "https://www.cnblogs.com/pythonfm/p/9063511.html";
        DoubanSpider.getHtmlByUrl(htmlUrl);
    }
}