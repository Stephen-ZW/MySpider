/**
 * 文件名：HttpUtil.java
 * 版权：Copyright 2017-2022 CMCC All Rights Reserved.
 * 描述：工具包
 */
package com.hand.httpUrlConnection;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author ZhangWei
 * @version 1.0
 * @date 2019/5/21 23:11
 */
public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    //静态内部类的单例模式
    public HttpUtil() {

    }

    private static class HttpUtilInstance {
        private static final HttpUtil INSTANCE = new HttpUtil();
    }

    public static HttpUtil getInstance() {
        return HttpUtilInstance.INSTANCE;
    }

    /**
     * get请求获取页面信息
     *
     * @param htmlUrl 页面URL
     * @return 页面信息
     */
    public static String doGet(String htmlUrl) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        String result = null;
        try {
            // 创建远程url连接对象
            URL url = new URL(htmlUrl);
            // 通过远程url连接对象打开一个连接，强转成HttpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式为get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000
            connection.setConnectTimeout(15 * 1000);
            // 设置读取远程返回的数据时间：60000
            connection.setReadTimeout(60 * 1000);

            // 添加请求头
            connection.setRequestProperty("Content-Type", "");
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestProperty("Cookie", "");

            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
                // 封装输入流inputStream，并指定字符集
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                // 存放数据
                StringBuilder stringBuilder = new StringBuilder();
                String temp = null;
                // 循环遍历一行一行读取数据
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp);
                    stringBuilder.append("\r\n");
                }
                result = stringBuilder.toString();
            }

        } catch (MalformedURLException e) {
            logger.error("MalformedURLException: {0}", e);
        } catch (IOException e) {
            logger.error("IOException: {0}", e);
        } finally {
            // 关闭资源
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 关闭远程连接
            connection.disconnect();
        }
        return result;
    }

    /**
     * post请求获取页面信息
     *
     * @param htmlUrl 页面URL
     * @param param   请求参数
     * @return 页面信息
     */
    public static String doPost(String htmlUrl, Map<String, String> param) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedReader bufferedReader = null;
        String result = null;
        try {
            // 创建远程url连接对象
            URL url = new URL(htmlUrl);
            // 通过远程url连接对象打开连接
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接请求方式
            connection.setRequestMethod("POST");
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(60000);
            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setDoInput(true);
            // 设置缓存为false
            connection.setUseCaches(false);

            connection.setRequestProperty("X-CSRF-TOKEN","8aeda894-6ae3-4619-a510-f85f5d54790d");

            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            //connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 设置Cookie
            connection.setRequestProperty("Cookie", "SESSIONID_HAP=b25f82cd-9528-4184-820d-1eee3e7376e3; GUEST_LANGUAGE_ID=zh_CN");
            // 设置用户代理
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.121 Safari/537.36");
            // 发送请求
            connection.connect();

            // 通过连接对象获取一个输出流
            outputStream = connection.getOutputStream();
            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
            byte[] bytes = buildUrlParams(param).getBytes();
            outputStream.write(bytes);
            System.out.println("httpStatus:" + connection.getResponseCode());
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
                // 封装输入流is，并指定字符集
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                // 存放数据
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = bufferedReader.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            logger.error("MalformedURLException: {0}", e);
        } catch (IOException e) {
            logger.error("IOException: {0}", e);
        } finally {
            // 关闭资源
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 关闭远程连接
            connection.disconnect();
        }
        return result;
    }

    /**
     * 将post请求的多参数Map解析成String
     *
     * @param params 请求参数
     * @return 参数
     */
    public static String buildUrlParams(Map<String, String> params) {
        Set<String> keySet = params.keySet();
        List<String> keyList = new ArrayList<String>(keySet);
        Collections.sort(keyList);
        StringBuilder stringBuilder = new StringBuilder();
        if (params != null && keyList.size() > 0) {
            for (String key : keyList) {
                if (StringUtils.isEmpty(params.get(key))) {
                    continue;
                }
                stringBuilder.append(key).append("=");
                try {
                    stringBuilder.append(URLEncoder.encode(params.get(key), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    logger.error("UnsupportedEncodingException, {0}", e);
                }
                stringBuilder.append("&");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }
}
