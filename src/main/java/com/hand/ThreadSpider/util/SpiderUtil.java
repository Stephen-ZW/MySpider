/**
 * 文件名：SpiderUtil.java
 * 版权：Copyright 2017-2022 CMCC All Rights Reserved.
 * 描述：爬虫类
 */

package com.hand.ThreadSpider.util;

import lombok.Data;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author ZhangWei
 * @version 1.0
 * @date 2019/5/25 16:42
 */
@Data
public class SpiderUtil {

    //网页编码
    private Charset charset;

    private Map<String, String> cookies;

    //用户代理
    private String userAgent;

    //请求头
    private Map<String, String> header;

    //请求方法
    private Connection.Method method;

    //超时时间
    private int timeOut;

    public SpiderUtil(Charset charset, Map<String, String> cookies, String userAgent, Map<String, String> header, Connection.Method method, int timeOut) {
        this.charset = charset;
        this.cookies = cookies;
        this.userAgent = userAgent;
        this.header = header;
        this.method = method;
        this.timeOut = timeOut;
    }

    public SpiderUtil() {

    }

    public Document getHtmlResource(String htmlUrl) throws IOException {
        // 爬虫开始运行
        Connection connection = Jsoup.connect(htmlUrl).method(method);
        //设置请求头
        if (header != null) {
            //connection.headers(header);
            for (Map.Entry<String, String> entry : header.entrySet()) {
                connection.header(entry.getKey(), entry.getValue());
            }
        }
        //设置cookie
        if (cookies != null) {
            connection.cookies(cookies);
        }
        //开始爬虫
        Connection.Response response = connection.execute().charset(this.charset.name());
        //转码
        return response.parse();
    }

}
