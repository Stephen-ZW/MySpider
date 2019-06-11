package com.hand.httpUrlConnection;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class HttpUtilTest {

    @Test
    public void doGet() {
        String htmlUrl = "https://yiyicclub.com";
        HttpUtil httpUtil = new HttpUtil();
        String result = httpUtil.doGet(htmlUrl);
        System.out.println("result:" + result);
    }

    @Test
    public void doPost() {
        String htmlUrl = "http://asc.hand-china.com/hrms/staffquery/queryStaff";
        Map<String, String> map = new HashMap<String, String>();
        map.put("orgName", null);
        map.put("pernr", "12518");
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