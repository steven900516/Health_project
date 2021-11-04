package com.lyx.health.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyx.health.entity.Answer;
import com.lyx.health.service.AnswerService;
import com.lyx.health.util.JsonResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Steven0516
 * @create 2021-11-01
 */


@RestController
@RequestMapping("/answer")
@CrossOrigin(origins = "*")
@Api(value = "问题操作api（解答……）",tags = { "评论操作api（解答……）" })
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @RequestMapping(value = "/sendAnswer",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "回答问题", notes = "参数包含（questionId,userId,userName,answerContent），其他不用")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "questionId", value = "问题id", required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "userId", value = "用户id", required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "userName", value = "用户名字", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "answerContent", value = "回答内容", required = false, dataType = "String")
    })
    @ApiResponses({ @ApiResponse(code = 200, message = "若发布解答成功，返回success;若发布解答失败，返回false")})
    private JsonResponse sendAnswer(Answer answer){
        String status = answerService.sendAnswer(answer);
        return JsonResponse.success(status);
    }


    @RequestMapping(value = "/showAnswersOfOneQuestion",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "展示一个问题的所有解答", notes = "参数包含（questionId）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "questionId", value = "问题id", required = false, dataType = "Integer"),
    })

    private JsonResponse showAnswersOfOneQuestion(@RequestParam(value = "questionId") Integer questionId){
        List<Answer> answers = answerService.showAllAnswerOfOneQuestion(questionId);
        return JsonResponse.success(answers);
    }






}
