package com.lyx.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyx.health.entity.User;

/**
 * @author Steven0516
 * @create 2021-10-23
 */
public interface UserService extends IService<User> {

    public void registUser(User user);

    public User selectUserRepeat(String userName);

    public User login(String userName,String userPwd);

    public String updateUser(int id,String new_Name);
}
