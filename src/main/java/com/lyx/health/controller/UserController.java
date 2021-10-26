package com.lyx.health.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyx.health.entity.User;
import com.lyx.health.service.EmailService;
import com.lyx.health.service.PassageService;
import com.lyx.health.service.UserService;
import com.lyx.health.util.JsonResponse;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Steven0516
 * @create 2021-10-23
 */

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@Api(value = "用户api",tags = { "用户接口" })
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;


    @RequestMapping(value = "/regist",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "用户注册", notes = "参数包含（userName,userPwd,userEmail）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "userName", value = "用户名",  dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "userPwd", value = "用户密码",  dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "userEmail", value = "用户邮箱", dataType = "String"),
    })
    @ApiResponses({ @ApiResponse(code = 200, message = "若用户名重复，message字段返回字符串fail，若无重复用户名返回success") })
    private JsonResponse regist(User user){
        User isRepeat = userService.selectUserRepeat(user.getUserName());
        if(!(isRepeat == null)){
            return JsonResponse.failure("fail");
        }else {
            userService.registUser(user);
            return JsonResponse.successMessage("success");
        }

    }

    @RequestMapping(value = "/login",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "用户登录", notes = "参数包含（userName,userPwd）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "userName", value = "用户名",  dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "userPwd", value = "用户密码",  dataType = "String"),
    })
    @ApiResponses({ @ApiResponse(code = 200, message = "若密码错误，message字段返回字符串fail，若登陆成功返回success") })
    private JsonResponse login(@RequestParam(value = "userName") String  userName,@RequestParam(value = "userPwd") String  userPwd){
        User user = userService.login(userName,userPwd);
        if(user == null){
            return JsonResponse.failure("fail");
        }else{
            return JsonResponse.successMessage("success");
        }

    }



    @RequestMapping(value = "/sendEmail",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "发送邮件", notes = "参数包含（userEmail）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "userEmail", value = "邮箱",  dataType = "String"),
    })
    @ApiResponses({ @ApiResponse(code = 200, message = "发送邮件成功，message字段返回6位验证码;邮箱格式错误，message字段返回fail字符串")})
    private JsonResponse sendEmail(@RequestParam(value = "userEmail") String  userEmail){
        String code = null;
        try {
            code = emailService.sendMail(userEmail);
        }catch (Exception e){
            e.printStackTrace();
            return JsonResponse.failure("fail");
        }
        return JsonResponse.successMessage(code);
    }

}
