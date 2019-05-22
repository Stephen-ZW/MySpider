package com.hand.httpClient.page;


import com.hand.httpClient.utils.CharsetDetector;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * 保存获取到的响应的相关内容
 *
 * @author Wei
 * @version 1.0
 * @date 2019/5/22 10:40
 */
public class Page {

    private byte[] content;
    private String html;  //网页源码字符串
    private Document doc;//网页Dom文档
    private String charset;//字符编码
    private String url;//url路径
    private String contentType;// 内容类型

    @Override
    public String toString() {
        return "Page{" +
                "content=" + Arrays.toString(content) +
                ", html='" + html + '\'' +
                ", doc=" + doc +
                ", charset='" + charset + '\'' +
                ", url='" + url + '\'' +
                ", contentType='" + contentType + '\'' +
                '}';
    }

    public Page(byte[] content, String url, String contentType) {
        this.content = content;
        this.url = url;
        this.contentType = contentType;
    }

    public String getCharset() {
        return charset;
    }

    public String getUrl() {
        return url;
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getContent() {
        return content;
    }

    /**
     * 返回网页的源码字符串
     *
     * @return 网页的源码字符串
     */
    public String getHtml() {
        if (html != null) {
            return html;
        }
        if (content == null) {
            return null;
        }
        if (charset == null) {
            charset = CharsetDetector.guessEncoding(content); // 根据内容来猜测 字符编码
        }
        try {
            this.html = new String(content, charset);
            return html;
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 得到文档
     *
     * @return doc
     */
    public Document getDoc() {
        if (doc != null) {
            return doc;
        }
        try {
            this.doc = Jsoup.parse(getHtml(), url);
            return doc;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


}
