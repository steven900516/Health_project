package com.lyx.health.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyx.health.entity.Passage;
import com.lyx.health.mapper.PassageMapper;
import com.lyx.health.service.PassageService;
import com.lyx.health.util.PassageGet;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven0516
 * @create 2021-10-22
 */

@Service
public class PassageServiceImpl extends ServiceImpl<PassageMapper, Passage> implements PassageService {

    @Autowired
    private PassageMapper passageMapper;

    @Override
    public void addPassage() throws Exception {
        Document doc = Jsoup.connect("https://www.xinli001.com/").get();
        Elements content = doc.getElementsByClass("content");
        for(Element passage: content){
            Elements mainTitle = passage.select("p.main-title");
            String mainTitleS = mainTitle.text();
            System.out.println(mainTitleS);

            Elements smallTitle = passage.select("p.small-title");
            String smallTitleS = smallTitle.text();
            if(smallTitleS.equals(""))  continue;
            System.out.println(smallTitleS);

            Elements category = passage.select("p.ci-title");
            String catgoryS = category.text();
            System.out.println(catgoryS);

            Elements img = passage.select("img");
            String imgUrl = img.attr("abs:src");
            System.out.println(imgUrl);

            Elements info = passage.select("a.common-a");
            String infoHref = info.attr("href");
            System.out.println(infoHref);

            Connection connection = Jsoup.connect(infoHref);
            //获取网页的Document对象
            Document document = connection.timeout(10*1000).get();

            String detail = PassageGet.info2(document);
            System.out.println(detail);
            Passage eve = new Passage();
            eve.setPassageSmallTitle(smallTitleS).setPassageTitle(mainTitleS).setPassageImg(imgUrl).setPassageCategory(catgoryS).setPassageContent(detail);
            passageMapper.insert(eve);
            System.out.println("------------------------------------------");
            Thread.sleep(3000);
        }

    }

    @Override
    public List<Passage> listAll() {

        return passageMapper.selectList(null);
    }
}
