package com.lyx.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyx.health.entity.Question;
import com.lyx.health.mapper.QuestionMapper;
import com.lyx.health.service.QuestionService;
import com.lyx.health.service.RedisService;
import com.lyx.health.util.QuestionGet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Steven0516
 * @create 2021-10-25
 */

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired QuestionService questionService;

    @Override
    public void questionGet() throws Exception {
//        Document doc = Jsoup.connect("https://www.xinli001.com/").get();
//        Elements contents = doc.getElementsByClass("content-ul");
//        System.out.println(contents.size());
//        for(Element ques: contents){
//            Elements title = ques.select("span.title");
//            String sTitle = title.text();
//            System.out.println(sTitle);
//
//            Elements content = ques.select("div.ellipsis-content");
//            String sContent = content.text();
//            System.out.println(sContent);
//            System.out.println("---------------");
//
//            Question question = new Question();
//            question.setQuestionTitile(sTitle).setQuestionContent(sContent).setQuestionTime(new Date());
//            questionMapper.insert(question);
//            Thread.sleep(2000);
//        }
            Document doc = Jsoup.connect("https://www.xinli001.com/qa?page=10&type=answer&object_name=answer&title=&level2_tag=0&sort=id&from=houye-dh").get();
            Elements contents = doc.getElementsByClass(" undeblock");
            for(Element ques: contents){
                Elements title = ques.select("a.common-a");
                title.remove(0);
                String sTitle = title.text();
                System.out.println(sTitle);


                String href = title.attr("href");
                href = "https://www.xinli001.com" + href;
                System.out.println(href);

                String content = QuestionGet.getContent(Jsoup.connect(href).get());
                System.out.println(content);
                Question question = new Question();
                question.setQuestionTitile(sTitle).setQuestionContent(content).setQuestionTime(new Date());
                questionMapper.insert(question);
                System.out.println("---------------");

                Thread.sleep(3000);
            }
    }

    @Override
    public Page<Question> listQuestionByPage(Page page) {
        Page<Question> question = questionMapper.selectPage(page, new QueryWrapper<Question>().orderByDesc("question_time"));
        for(Question q: question.getRecords()){
            q.setLike(Integer.parseInt(redisService.get("like-" + q.getId())));
            q.setComment(Integer.parseInt(redisService.get("comment-" + q.getId())));
        }
        return question;
    }

    @Override
    public String sendQuestion(Question question) {
        Date now = new Date();
        question.setQuestionTime(now);
        try{
            questionMapper.insert(question);
            int id = question.getId();
            redisService.set("like-" + id,"0");
            redisService.set("comment-" + id,"0");

        }catch (Exception e){
            e.printStackTrace();
            return "false";
        }
        return "success";

    }

    @Override
    public int pressLike(int id) {

        //无id
        if(redisService.get("like-" + id) == null){
            return -1;
        }
        redisService.increment("like-" + id,1l);
        return Integer.parseInt(redisService.get("like-" + id));
    }

    @Override
    public List<Question> showQuestionsOfOnePerson(Integer question_uid) {
        QueryWrapper<Question> wq = new QueryWrapper<>();
        wq.eq("question_uid",question_uid);
        wq.orderByDesc("question_time");
        List<Question> questions = questionMapper.selectList(wq);
        for(Question question: questions){
            question.setLike(Integer.parseInt(redisService.get("like-" + question.getId())));
            question.setComment(Integer.parseInt(redisService.get("comment-" + question.getId())));
        }
        return questions;
    }

    @Override
    public Question oneQuestion(int id) {
        Question question = questionService.getById(id);
        String like = redisService.get("like-" + id);
        String comment = redisService.get("comment-" + id);
        question.setLike( Integer.parseInt(like));
        question.setComment(Integer.parseInt(comment));
        return question;

    }


}
