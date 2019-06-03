/**
 * 文件名：WebClientUtil.java
 * 版权：Copyright 2017-2022 CMCC All Rights Reserved.
 * 描述：
 */

package com.hand.webClient.utils;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.hand.webClient.entity.AnalyzedTask;
import com.hand.webClient.entity.PatentDoc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Wei
 * @version 1.0
 * @date 2019/5/23 14:17
 */
public class WebClientUtil {

    private static Logger logger = LoggerFactory.getLogger(WebClientUtil.class);

    private static final String htmlUrl = "http://kns.cnki.net/kns/brief/result.aspx?dbprefix=SCPD";

    //建立返回结果对象集
    static List<PatentDoc> resultList = new ArrayList<PatentDoc>();

    static HtmlPage lastOnePage = null;
    static HtmlPage nextOnePage = null;

    public static void main(String []args) {

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        System.out.println("=============开始爬虫==============");
        /** 获取当前系统时间*/
        long startTime = System.currentTimeMillis();
        //获取客户端，禁用JS
        WebClient webClient = HtmlUtil.iniParam_Js();

        try {
            //获取搜索页面，搜索页面包含多个学者，机构通常是非完全匹配，姓名是完全匹配的，我们需要对所有的学者进行匹配操作
            HtmlPage htmlPage = webClient.getPage(htmlUrl);
            // 根据名字得到一个表单，查看上面这个网页的源代码可以发现表单的名字叫“f”
            HtmlForm form = htmlPage.getFormByName("Form1");
            // 同样道理，获取”检 索“这个按钮
            HtmlButtonInput button = form.getInputByValue("检 索");
            // 得到搜索框
            HtmlTextInput from = form.getInputByName("publishdate_from");
            HtmlTextInput to = form.getInputByName("publishdate_to");
            // 设置搜索框的value
            from.setValueAttribute("2018-01-01");
            to.setValueAttribute("2018-12-31");
            // 设置好之后，模拟点击按钮
            HtmlPage nextPage = button.click();

            HtmlAnchor applicationDate = nextPage.getAnchorByText("申请日");
            HtmlPage secondPage = applicationDate.click();
            HtmlAnchor fifty = secondPage.getAnchorByText("50");
            HtmlPage thirdPage = fifty.click();

            lastOnePage = thirdPage;

            System.out.println("===========拿到检索结果==============");
            //利用线程池开启线程解析首页的数据
            executorService.execute(new AnalyzedTask(lastOnePage, 1));

            //定义总页数
            int size = 200;
            for (int i = 2; i < size; i++) {
                //休眠一分钟防止频繁按下一页
                Thread.sleep(40000);
                try {
                    //将一页的结果抓取完之后开始点击下一页
                    HtmlAnchor next = lastOnePage.getAnchorByText("下一页");
                    nextOnePage = next.click();
                } catch (ElementNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("需要输入验证码！直接跳出循环。结束爬虫！");
                    break;
                }
                //利用线程池开启线程解析数据
                executorService.execute(new AnalyzedTask(nextOnePage, i));
                lastOnePage = nextOnePage;
            }

            /** 获取当前的系统时间，与初始时间相减就是程序运行的毫秒数，除以1000就是秒数*/
            long endTime = System.currentTimeMillis();
            long usedTime = (endTime - startTime) / 1000;
            System.out.println("程序运行时间：" + usedTime + "s");
        } catch (IOException e) {
            logger.error("IOException, {1}", e);
        } catch (InterruptedException e) {
            logger.error("InterruptedException, {1}", e);
        }
    }


}
