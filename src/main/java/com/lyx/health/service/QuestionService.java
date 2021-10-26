package com.lyx.health.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import com.lyx.health.entity.Passage;
import com.lyx.health.entity.Question;

import java.io.IOException;

/**
 * @author Steven0516
 * @create 2021-10-25
 */
public interface QuestionService extends IService<Question> {


    //爬取数据使用
    public void questionGet() throws IOException, Exception;

    public Page<Question> listQuestionByPage(Page page);

    public void sendQuestion();

    public int pressLike(int id);
}
