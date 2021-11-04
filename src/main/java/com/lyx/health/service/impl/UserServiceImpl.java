package com.lyx.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyx.health.entity.User;
import com.lyx.health.mapper.UserMapper;
import com.lyx.health.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Steven0516
 * @create 2021-10-23
 */

@Service
public class UserServiceImpl  extends ServiceImpl<UserMapper, User> implements UserService   {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void registUser(User user) {
        userService.save(user);
    }

    @Override
    public User selectUserRepeat(String userName) {
        QueryWrapper<User> wq = new QueryWrapper<>();
        wq.eq("user_name",userName);
        return userService.getOne(wq);
    }

    @Override
    public User login(String userName, String userPwd) {
        QueryWrapper<User> wq = new QueryWrapper<>();
        wq.eq("user_name",userName).eq("user_pwd",userPwd);
        return userService.getOne(wq);
    }

    @Override
    public String updateUser(int id,String new_name) {
        UpdateWrapper<User> uw = new UpdateWrapper<>();
        uw.eq("id",id).set("user_name",new_name);
        userMapper.update(null,uw);
        return "success";
    }
}
