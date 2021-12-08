package com.lyx.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyx.health.entity.Passage;
import com.lyx.health.mapper.PassageMapper;
import com.lyx.health.service.PassageService;
import com.lyx.health.service.RedisService;
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

    @Autowired
    private PassageService passageService;

    @Autowired
    private RedisService redisService;

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

    @Override
    public Page<Passage> listAllByPage(Page page) {
        Page res = passageService.page(page, null);
        List<Passage> passages = res.getRecords();
        for(Passage passage: passages){
            int count = Integer.parseInt(redisService.get("passageLike-" + passage.getId()));
            passage.setPassageLike(count);
        }
        return res;
    }

    @Override
    public List<Passage> listByLei(String passageLei) {
        QueryWrapper<Passage> wq = new QueryWrapper<>();
        wq.eq("passage_lei","#" + passageLei);
        return passageService.list(wq);
    }

    @Override
    public int pressLike(int id) {
        //无id
        if(redisService.get("passageLike-" + id) == null){
            return -1;
        }
        redisService.increment("passageLike-" + id,1l);
        return Integer.parseInt(redisService.get("passageLike-" + id));
    }

    @Override
    public Passage onePassage(int id) {
        Passage one = passageService.getById(id);
        String s = redisService.get("passageLike-" + id);
        one.setPassageLike(Integer.parseInt(s));
        return one;
    }

    @Override
    public int cancleLike(int id) {
        Integer s = Integer.parseInt(redisService.get("passageLike-" + id));
        if(s <= 0){
            return 0;
        }else{
            redisService.decrement("passageLike-" + id,1l);
        }
        return Integer.parseInt(redisService.get("passageLike-" + id));
    }


}
