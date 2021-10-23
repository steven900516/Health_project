package com.lyx.health.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyx.health.entity.Passage;
import com.lyx.health.mapper.PassageMapper;
import com.lyx.health.service.PassageService;
import com.lyx.health.util.JsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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


    @RequestMapping(value = "/listAllByPage",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "获取分页后的心理文章信息", notes = "参数包含（pageNo,pageSize），返回的文章数据在data字段中，code字段为正确响应码")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "pageNo", value = "当前页数（默认为第一页）", required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "pageSize", value = "每页文章数（默认为6条）", required = false, dataType = "Integer"),
    })
    private JsonResponse listAllByPage(@RequestParam(required = false,defaultValue = "1") Integer pageNo,
                                       @RequestParam(required = false,defaultValue = "6") Integer pageSize){
        return JsonResponse.success(passageService.listAllByPage(new Page<>(pageNo, pageSize)));
    }

    @RequestMapping(value = "/listPassageByLei",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation(value = "获取各个大类的文章", notes = "参数包含（passageLei），返回的文章数据在data字段中")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "passageLei", value = "大类名字（例如：心理科普）",  dataType = "String")

    })
    private JsonResponse listPassageByLei(@RequestParam(value = "passageLei") String passageLei){
        return JsonResponse.success(passageService.listByLei(passageLei));
    }




}
