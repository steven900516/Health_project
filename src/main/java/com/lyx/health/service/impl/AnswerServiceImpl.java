package com.lyx.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyx.health.util.SensitiveFilter;
import com.lyx.health.entity.Answer;
import com.lyx.health.mapper.AnswerMapper;
import com.lyx.health.service.AnswerService;
import com.lyx.health.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Steven0516
 * @create 2021-11-01
 */


@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements AnswerService {

    @Autowired
    private AnswerMapper answerMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Override
    public String sendAnswer(Answer answer) {
        if(answer.getAnswerContent().length() > 50){
            return "false";
        }
        answer.setAnswerTime(new Date());
        answer.setAnswerContent(sensitiveFilter.filter(answer.getAnswerContent()));
        try {
            answerMapper.insert(answer);
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }

        redisService.increment("comment-" + answer.getQuestionId(),1l);
        return "success";
    }

    @Override
    public List<Answer> showAllAnswerOfOneQuestion(Integer questionId) {
        QueryWrapper<Answer> wq = new QueryWrapper<>();
        wq.eq("question_id",questionId);
        List<Answer> allAnswers = answerMapper.selectList(wq);
        return allAnswers;
    }

    @Override
    public List<Answer> showAllAnswerOfOnePerson(Integer userId) {
        QueryWrapper<Answer> wq = new QueryWrapper<>();
        wq.eq("question_id",userId);
        List<Answer> answers = answerMapper.selectList(wq);
        return answers;
    }
}
