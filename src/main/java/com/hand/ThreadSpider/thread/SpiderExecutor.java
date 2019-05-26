/**
 * 文件名：SpiderExecutor.java
 * 版权：Copyright 2017-2022 CMCC All Rights Reserved.
 * 描述：
 */

package com.hand.ThreadSpider.thread;

import com.hand.ThreadSpider.api.SpiderWorker;
import org.jsoup.Connection;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author ZhangWei
 * @version 1.0
 * @date 2019/5/25 17:20
 */

public class SpiderExecutor {

    private SpiderWorker spiderWorker;
    private ExecutorService executorService = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() + 1);
    private int maxFailCount = 5;
    private Charset charset = Charset.forName("UTF-8");
    private Map<String, String> header = null;
    private Map<String, String> cookies = null;
    private String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36";
    private int timeOut = 30 * 1000;
    private Connection.Method method = Connection.Method.GET;
    private List<Future> futureList = new Vector<>();

    /**
     * @param spiderWorker 用户必须实现的接口
     */
    public SpiderExecutor(SpiderWorker spiderWorker) {
        this.spiderWorker = spiderWorker;
    }

    /**
     * 提交任务
     *
     * @param htmlUrls 爬取的地址集合
     */
    public void submit(List<String> htmlUrls) {
        for (String htmlUrl : htmlUrls) {
            futureList.add(executorService.submit(new SpiderCallable()
                    .init(charset, cookies, userAgent, header, method, timeOut, spiderWorker, maxFailCount, htmlUrl)));
        }
    }

    /**
     * 执行完后关闭线程池
     */
    public void shutdown() {
        executorService.shutdown();
    }

    /**
     * 返回任务执行结果
     *
     * @return
     */
    public List<Future> getFutureList() {
        return futureList;
    }

    /**
     * 返回线程池
     *
     * @return
     */
    public ExecutorService getExecutor() {
        return executorService;
    }

    /**
     * @param threads 工作线程数量
     * @return
     */
    public SpiderExecutor setThreads(int threads) {
        this.executorService = Executors.newFixedThreadPool(threads);
        return this;
    }

    /**
     * 重写爬虫代码则属性无效
     *
     * @param charset 网页解码格式
     * @return
     */
    public SpiderExecutor setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }

    /**
     * @param maxFailCount 爬取失败最多尝试次数
     * @return
     */
    public SpiderExecutor setMaxFailCount(int maxFailCount) {
        this.maxFailCount = maxFailCount;
        return this;
    }

    /**
     * 重写爬虫代码则属性无效
     *
     * @param header 请求头
     */
    public SpiderExecutor setHeader(Map<String, String> header) {
        this.header = header;
        return this;
    }

    /**
     * 重写爬虫代码则属性无效
     *
     * @param method 请求方法
     */
    public SpiderExecutor setMethod(Connection.Method method) {
        this.method = method;
        return this;
    }

    /**
     * 默认超时时间为30秒(30000毫秒)。零超时被视为无限超时。
     * 重写爬虫代码则属性无效
     *
     * @param timeout 请求超时
     */
    public SpiderExecutor setTimeout(int timeout) {
        this.timeOut = timeout;
        return this;
    }

    /**
     * 自定义UA
     * 重写爬虫代码则属性无效
     *
     * @param userAgent
     */
    public SpiderExecutor setUserAgent(String userAgent) {
        this.userAgent = userAgent;
        return this;
    }

    /**
     * 设置Cookies
     * 重写爬虫代码则属性无效
     *
     * @param cookies
     */
    public SpiderExecutor setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
        return this;
    }
}
