/**
 * 文件名：HttpClientUtil.java
 * 版权：Copyright 2017-2022 CMCC All Rights Reserved.
 * 描述：httpClient爬取页面
 */

package com.hand.httpClient.utils;

import com.hand.httpClient.page.Page;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Wei
 * @version 1.0
 * @date 2019/5/22 10:16
 */
public class HttpClientUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 获取页面
     *
     * @param htmlUrl 页面URL
     * @return 页面信息
     */
    public static Page doPage(String htmlUrl) {

        Page page = null;
        // 1.生成 HttpClinet 对象并设置参数
        HttpClient httpClient = new HttpClient();
        // 设置 HTTP 连接超时 5s
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
        // 2.生成 GetMethod 对象并设置参数
        GetMethod getMethod = new GetMethod(htmlUrl);
        // 设置 get 请求超时 5s
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        // 设置请求重试处理
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
        // 3.执行 HTTP GET 请求

        try {
            int statusCode = httpClient.executeMethod(getMethod);
            // 判断访问的状态码
            if (statusCode != HttpStatus.SC_OK) {
                logger.error("method fail:", getMethod.getStatusLine());
            }
            // 4.处理 HTTP 响应内容
            byte[] bytes = getMethod.getResponseBody();// 读取为字节 数组
            // 得到当前返回类型
            String contentType = getMethod.getResponseHeader("Content-Type").getValue();
            // 封装成为页面
            page = new Page(bytes, htmlUrl, contentType); //封装成为页面
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("IOException, {0}", e);
        } finally {
            getMethod.releaseConnection();
        }

        return page;
    }


}
