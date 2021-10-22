package com.lyx.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyx.health.entity.Passage;

import java.io.IOException;
import java.util.List;

/**
 * @author Steven0516
 * @create 2021-10-22
 */
public interface PassageService extends IService<Passage> {

    public void  addPassage() throws Exception;


    public List<Passage> listAll();
}
