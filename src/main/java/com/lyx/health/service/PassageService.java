package com.lyx.health.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lyx.health.entity.Passage;
import com.lyx.health.entity.User;


import java.io.IOException;
import java.util.List;

/**
 * @author Steven0516
 * @create 2021-10-22
 */
public interface PassageService extends IService<Passage> {

    public void  addPassage() throws Exception;


    public List<Passage> listAll();

    public Page<Passage> listAllByPage(Page page);

    public List<Passage> listByLei(String passageLei);

    public int pressLike(int id);


    public Passage onePassage(int id);
}
