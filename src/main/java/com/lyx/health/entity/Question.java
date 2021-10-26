package com.lyx.health.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Steven0516
 * @create 2021-10-25
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("question")
@ApiModel(value="问题对象")
public class Question extends Model<Question> {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;

    @TableField(value = "question_title")
    private String questionTitile;

    @TableField(value = "question_content")
    private String questionContent;

    @TableField(value = "question_time")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date questionTime;


    @TableField(exist = false)
    private int like;


    @TableField(exist = false)
    private int comment;
}
