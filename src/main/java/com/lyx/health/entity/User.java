package com.lyx.health.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author Steven0516
 * @create 2021-10-22
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user")
public class User extends Model<User> {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "user_pwd")
    private String userPwd;

    @TableField(value = "user_email")
    private String userEmail;


}
