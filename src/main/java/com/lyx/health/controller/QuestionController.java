package com.lyx.health.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyx.health.entity.Question;
import com.lyx.health.service.QuestionService;
import com.lyx.health.util.JsonResponse;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Steven0516
 * @create 2021-10-25
 */

@RestController
@RequestMapping("/question")
@CrossOrigin(origins = "*")
@Api(tags = { "问答访问接口" })
@Slf4j
public class QuestionController {
    @Autowired
    private QuestionService questionService;




    @RequestMapping(value = "/listQuestionByPage",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "获取分页后的问答", notes = "参数包含（pageNo,pageSize），返回数据包含总问题数，总页数等等……请自行查看，返回的文章数据在data字段中的record，code字段为正确响应码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "pageNo", value = "当前页数（默认为第一页）", required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "pageSize", value = "每页问答数（默认为6条）", required = false, dataType = "Integer"),
    })
    private JsonResponse listAllByPage(@RequestParam(required = false,defaultValue = "1") Integer pageNo,
                                       @RequestParam(required = false,defaultValue = "6") Integer pageSize){
        return JsonResponse.success(questionService.listQuestionByPage(new Page<>(pageNo, pageSize)));
    }


    @RequestMapping(value = "/listRandomThree",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "精彩问答中的板块(三个问答)", notes = "无参数，返回的文章数据在data字段中，code字段为正确响应码")

    private JsonResponse listRandomThree(){

        int number = (int)(Math.random() * 10) + 1;
        Page<Question> questionPage = questionService.listQuestionByPage(new Page<>(number, 3));
        return JsonResponse.success(questionPage.getRecords());
    }

    @RequestMapping(value = "/pressLike",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "问答点赞", notes = "参数包含问答id（id），code字段为正确响应码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "问答id", required = false, dataType = "Integer")})
    @ApiResponses({ @ApiResponse(code = 200, message = "若点赞成功，返回原来点赞数 + 1;若点赞失败，返回-1")})
    private JsonResponse pressLike(@RequestParam(value = "id") Integer id){
        int count = questionService.pressLike(id);
        if(count < 0 ){
            return JsonResponse.success(-1);
        }
        return JsonResponse.success(count);
    }


    @RequestMapping(value = "/sendQuestion",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "问答发布", notes = "参数包含用户id（questionUid），问答标题（questionTitile），问答详情（questionContent）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "questionUid", value = "用户id", required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "questionUserName", value = "用户名字", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "questionTitile", value = "问答标题", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "questionContent", value = "问答详情（内容）", required = false, dataType = "String"),
    })
    @ApiResponses({ @ApiResponse(code = 200, message = "若发布问题成功，返回success字符串;若发布问题失败，返回false字符串")})
    private JsonResponse sendQuestion(Question question){
        String status = questionService.sendQuestion(question);
        return JsonResponse.success(status);
    }


    @RequestMapping(value = "/questionsOfOnePerson",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "展示一个人发布的所有问题", notes = "参数包含用户id（questionUid）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "questionUid", value = "用户id", required = false, dataType = "Integer")
    })
    private JsonResponse questionsOfOnePerson(@RequestParam(value = "questionUid")Integer questionUid){
        List<Question> questions = questionService.showQuestionsOfOnePerson(questionUid);

        return JsonResponse.success(questions);
    }


    @RequestMapping(value = "/oneQuesition",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "根据id找获取问题", notes = "参数包含问题id")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "id", required = false, dataType = "Integer")
    })
    private JsonResponse oneQuesition(@RequestParam(value = "id")Integer id){
        Question question = questionService.oneQuestion(id);
        return JsonResponse.success(question);
    }


    @RequestMapping(value = "/cancleLike",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "问答取消点赞", notes = "参数包含问答id（id），code字段为正确响应码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "问答id", required = false, dataType = "Integer")})
    @ApiResponses({ @ApiResponse(code = 200, message = "若取消点赞成功，返回原来点赞数 - 1;若点赞为0，返回0")})
    private JsonResponse cancleLike(@RequestParam(value = "id") Integer id){
        int count = questionService.cancleLike(id);
        return JsonResponse.success(count);
    }


//    @RequestMapping(value = "/testLimit")
//    @ApiOperation(value = "限流接口", notes = "无参数")
//    @ApiImplicitParams({})
//    @Limit(key = "testLimit", permitsPerSecond = 1, timeout = 500, timeunit = TimeUnit.MILLISECONDS,msg = "当前排队人数较多，请稍后再试！")
//    private String testlimit(){
//
//        log.info("令牌桶limit获取令牌成功");
//        return "ok";
//    }

}
