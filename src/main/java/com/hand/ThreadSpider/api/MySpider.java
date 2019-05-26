package com.hand.ThreadSpider.api;

import com.hand.ThreadSpider.thread.SpiderExecutor;
import com.hand.ThreadSpider.util.SpiderUtil;
import org.jsoup.nodes.Document;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class MySpider implements SpiderWorker {

    @Override
    public Object run(Document document, SpiderUtil spiderUtil) {
        //使用JSOUP进行HTML解析获取想要的div节点和属性
        //保存在数据库或本地文件中
        //新增spiderUtil工具类可以再次请求网址
        return document.title();
    }

    @Override
    public Object fail(String htmlUrl) {
        //任务执行失败
        //可以记录失败网址
        //记录日志
        return false;
    }

    public static void main(String args[]) throws ExecutionException, InterruptedException {

        List<String> htmlUrls = new ArrayList<>();
        htmlUrls.add("https://blog.51cto.com/13501268/2087975");
        htmlUrls.add("https://my.oschina.net/u/3572551/blog/1862260/");
        //第一步：新建AiPa实例
        SpiderExecutor spiderExecutor = new SpiderExecutor(new MySpider()).setCharset(Charset.forName("UTF-8"));
        //第二步：提交任务
        spiderExecutor.submit(htmlUrls);
        //第三步：读取返回值
        List<Future> futureList = spiderExecutor.getFutureList();
        for (int i = 0; i < futureList.size(); i++) {
            //get() 方法会阻塞当前线程直到获取返回值
            System.out.println(i+"："+futureList.get(i).get());
        }
        //第四步：关闭线程池
        spiderExecutor.shutdown();
    }
}
