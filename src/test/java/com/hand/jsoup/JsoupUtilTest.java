package com.hand.jsoup;

import org.junit.Test;

import static org.junit.Assert.*;

public class JsoupUtilTest {

    @Test
    public void doJsoup() {
        String htmlUrl = "https://movie.douban.com/";
        JsoupUtil.doJsoup(htmlUrl);
    }
}