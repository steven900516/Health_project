package com.lyx.health.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyx.health.entity.Question;
import com.lyx.health.mapper.QuestionMapper;
import com.lyx.health.service.QuestionService;
import com.lyx.health.service.RedisService;
import com.lyx.health.util.JsonResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Steven0516
 * @create 2021-10-25
 */

@RestController
@RequestMapping("/question")
@CrossOrigin(origins = "*")
@Api(tags = { "问答访问接口" })
public class QuestionController {
    @Autowired
    private QuestionService questionService;


//    @RequestMapping("/redis")
//    public void redis(){
//        questionService.sendQuestion();
//    }


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
    @ApiOperation(value = "问答点赞", notes = "参数包含文章id（id），code字段为正确响应码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "文章id", required = false, dataType = "Integer")})
    @ApiResponses({ @ApiResponse(code = 200, message = "若点赞成功，返回原来点赞数 + 1;若点赞失败，返回-1")})
    private JsonResponse pressLike(@RequestParam(value = "id") Integer id){
        int count = questionService.pressLike(id);
        if(count < 0 ){
            return JsonResponse.success(-1);
        }
        return JsonResponse.success(count);
    }


}
