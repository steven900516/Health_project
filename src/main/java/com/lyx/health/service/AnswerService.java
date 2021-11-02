package com.lyx.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyx.health.entity.Answer;
import com.lyx.health.entity.Question;

import java.util.List;

/**
 * @author Steven0516
 * @create 2021-11-01
 */

public interface AnswerService extends IService<Answer> {

    public String sendAnswer(Answer answer);

    public List<Answer> showAllAnswerOfOneQuestion(Integer questionId);

    public List<Answer> showAllAnswerOfOnePerson(Integer userId);
}
