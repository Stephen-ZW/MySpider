package com.hand.httpClient.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class HttpClientUtilTest {

    @Test
    public void doPage() {
        String htmlUrl = "https://www.douban.com/";
        HttpClientUtil.doPage(htmlUrl);
    }
}