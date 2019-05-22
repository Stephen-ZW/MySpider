package com.hand.httpUrlConnection;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class HttpUtilTest {

    @Test
    public void doGet() {
    }

    @Test
    public void doPost() {
        String htmlUrl = "https://cd.fang.anjuke.com/loupan/s";
        Map<String, String> map = new HashMap<String, String>();
        map.put("kw", "A");
        HttpUtil httpUtil = new HttpUtil();
        String result = httpUtil.doPost(htmlUrl, map);
        System.out.println("result:" + result);
    }

    @Test
    public void buildUrlParams() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("user", "zw");
        map.put("pass", "123456");
        HttpUtil httpUtil = new HttpUtil();
        String result = httpUtil.buildUrlParams(map);
        System.out.println("result:" + result);
    }
}