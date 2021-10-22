package com.lyx.health.controller;

import com.lyx.health.entity.Passage;
import com.lyx.health.mapper.PassageMapper;
import com.lyx.health.service.PassageService;
import com.lyx.health.util.JsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Steven0516
 * @create 2021-10-22
 */

@RestController
@RequestMapping("/passage")
@CrossOrigin(origins = "*")
@Api(value = "心理文章api",tags = { "文章访问接口" })
public class PassageController {
    @Autowired
    private PassageService passageService;


    @RequestMapping(value = "/listAll",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "获取所有心理文章信息(标题，小标题，内容，图片链接……)", notes = "无参数（直接调用），返回的文章数据在data字段中，code字段为正确响应码")
    private JsonResponse<List<Passage>> listAll(){

        List<Passage> passages = passageService.listAll();

        return JsonResponse.success(passages);
    }
}
