package com.hand.httpClient.demo;

import com.sun.deploy.net.HttpResponse;
import org.apache.commons.httpclient.HttpStatus;

import java.io.InputStream;

public class DoubanSpider {

    public static String getHtmlByUrl(String url){
        String html = null;
        //创建httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        httpget.addHeader("Accept", "text/html");
        httpget.addHeader("Accept-Charset", "utf-8");
        httpget.addHeader("Accept-Encoding", "gzip");
        httpget.addHeader("Accept-Language", "zh-CN,zh");
        httpget.addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.110 Safari/537.36");
        RequestConfig config=RequestConfig.custom()
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .build();
        httpget.setConfig(config);
        try {
            HttpResponse response = httpClient.execute(httpget);
            int ResStatu = response.getStatusLine().getStatusCode();
            if (ResStatu== HttpStatus.SC_OK) {
                //获得输入流
                InputStream entity = response.getEntity().getContent();
                if (entity!=null) {
                    html=getStreamString(entity);
//                    此处输出html
                    System.out.println(html);
                }
            }
        } catch (Exception e) {
            System.out.println("访问出现异常!");
            e.printStackTrace();
        } finally {
            // httpClient.getConnectionManager().shutdown();
        }
        return html;
}
