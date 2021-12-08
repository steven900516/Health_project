package com.lyx.health.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import com.lyx.health.entity.Answer;
import com.lyx.health.entity.Passage;
import com.lyx.health.entity.Question;

import java.io.IOException;
import java.util.List;

/**
 * @author Steven0516
 * @create 2021-10-25
 */
public interface QuestionService extends IService<Question> {


    //爬取数据使用
    public void questionGet() throws IOException, Exception;

    public Page<Question> listQuestionByPage(Page page);

    public String sendQuestion(Question question);

    public int pressLike(int id);

    public List<Question> showQuestionsOfOnePerson(Integer question_uid);


    public Question oneQuestion(int id);

    public int cancleLike(int id);
}
