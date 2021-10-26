package com.lyx.health.util;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author Steven0516
 * @create 2021-10-25
 */
public class QuestionGet {
    public static String  getContent(Document document){
        Elements select = document.select("p.text");
        return select.text();
    }
}
