package com.lyx.health.util;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author Steven0516
 * @create 2021-10-22
 */
public class PassageGet {
    public  static String  info2(Document document){
        Elements txt = document.getElementsByClass("yxl-editor-article ");

        System.out.println(txt.text().length());
        return txt.text();
    }
}
