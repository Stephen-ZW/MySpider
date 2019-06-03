package com.hand.ThreadSpider.thread;

import com.hand.ThreadSpider.api.SpiderWorker;
import com.hand.ThreadSpider.util.SpiderUtil;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.Callable;

public class SpiderCallable implements Callable<Object> {

    //爬虫工具类
    private SpiderUtil spiderUtil;

    //需要实现的方法
    private SpiderWorker spiderWorker;

    //最大爬取失败次数
    private int maxFailCount;

    //爬取地址
    private String htmlUrl;

    public SpiderCallable init(SpiderUtil spiderUtil, SpiderWorker spiderWorker, int maxFailCount, String htmlUrl) {
        this.spiderUtil = spiderUtil;
        this.spiderWorker = spiderWorker;
        this.maxFailCount = maxFailCount;
        this.htmlUrl = htmlUrl;
        return this;
    }

    public SpiderCallable init(Charset charset, Map<String, String> cookies, String userAgent, Map<String, String> header,
                               Connection.Method method, int timeOut, SpiderWorker spiderWorker, int maxFailCount, String htmlUrl) {
        this.spiderUtil = new SpiderUtil(charset, cookies, userAgent, header, method, timeOut);
        this.spiderWorker = spiderWorker;
        this.maxFailCount = maxFailCount;
        this.htmlUrl = htmlUrl;
        return this;
    }

    @Override
    public Object call() {
        int c = 0;

        while (c < maxFailCount) {
            try {
                //开始爬虫
                Document document = spiderUtil.getHtmlResource(htmlUrl);
                //执行任务
                return spiderWorker.run(document, spiderUtil);
            } catch (IOException e) {
                c++;
                if (c == maxFailCount) {
                    e.printStackTrace();
                }
            }
        }
        // 爬取失败执行的方法
        return spiderWorker.fail(htmlUrl);

    }
}
