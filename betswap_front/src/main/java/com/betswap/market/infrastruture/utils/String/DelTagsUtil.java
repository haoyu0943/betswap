package com.betswap.market.infrastruture.utils.String;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class DelTagsUtil {
    private static SimpleDateFormat sdfhf=new SimpleDateFormat("yyyyMMddHHmmss");
    private static SimpleDateFormat sdfhfs=new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private static Random random = new Random();
    private static String delHtmlTags(String htmlStr) {
        //定义script的正则表达式，去除js可以防止注入
        String scriptRegex="<script[^>]*?>[\\s\\S]*?<\\/script>";
        //定义style的正则表达式，去除style样式，防止css代码过多时只截取到css样式代码
        String styleRegex="<style[^>]*?>[\\s\\S]*?<\\/style>";
        //定义HTML标签的正则表达式，去除标签，只提取文字内容
        String htmlRegex="<[^>]+>";
        //定义空格,回车,换行符,制表符
        String spaceRegex = "\\s*|\t|\r|\n";
        String blankRegex = "&nbsp;";
        // 过滤script标签
        htmlStr = htmlStr.replaceAll(scriptRegex, "");
        // 过滤style标签
        htmlStr = htmlStr.replaceAll(styleRegex, "");
        // 过滤html标签
        htmlStr = htmlStr.replaceAll(htmlRegex, "");
        // 过滤空格等
        htmlStr = htmlStr.replaceAll(spaceRegex, "");
        htmlStr = htmlStr.replaceAll(blankRegex, "");
        return htmlStr.trim(); // 返回文本字符串
    }
    /**
     * 获取HTML代码里的内容
     * @param htmlStr
     * @return
     */
    public static String getTextFromHtml(String htmlStr){
        //去除html标签
        htmlStr = delHtmlTags(htmlStr);
        //去除空格" "
        int p= htmlStr.indexOf(" ");
        while(p>0) {
            htmlStr = htmlStr.replaceAll(" ", "");
            p= htmlStr.indexOf(" ");
        }
        return htmlStr;
    }

    public static String getJlbh(String prefix) {
        if (StringUtils.isBlank(prefix))prefix = "";
        String str=prefix+sdfhfs.format(new Date())+(random.nextInt(899)+100);
        return str;
    }

}

