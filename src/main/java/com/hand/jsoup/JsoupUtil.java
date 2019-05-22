/**
 * 文件名：JsoupUtil.java
 * 版权：Copyright 2017-2022 CMCC All Rights Reserved.
 * 描述：Jsoup工具类使用
 */

package com.hand.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Wei
 * @version 1.0
 * @date 2019/5/22 9:59
 */
public class JsoupUtil {

    private static Logger logger = LoggerFactory.getLogger(JsoupUtil.class);


    public static String doJsoup(String htmlUrl) {
        try {
            Document document = Jsoup.connect(htmlUrl).get();
            String title = document.title();
        } catch (IOException e) {
            logger.error("IOException, {0}", e);
        }
        return null;
    }
}
