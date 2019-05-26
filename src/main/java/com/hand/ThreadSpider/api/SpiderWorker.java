package com.hand.ThreadSpider.api;

import com.hand.ThreadSpider.util.SpiderUtil;
import org.jsoup.nodes.Document;

public interface SpiderWorker<T, S> {

    /**
     * 如何解析爬下来的HTML文档？
     *
     * @param document   JSOUP提供的文档
     * @param spiderUtil 爬虫工具
     * @return
     */
    T run(Document document, SpiderUtil spiderUtil);

    /**
     * run方法异常则执行fail方法
     *
     * @param htmlUrl 网址
     * @return
     */
    S fail(String htmlUrl);
}
