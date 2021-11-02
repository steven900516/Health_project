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
 * @create 2021-11-01
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("answer")
@ApiModel(value="解答对象")
public class Answer extends Model<Answer> {


    @TableId(value = "id",type = IdType.AUTO)
    private int id;

    @TableField(value = "question_id")
    private String questionId;


    @TableField(value = "user_id")
    private String userId;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "answer_content")

    private String answerContent;


    @TableField(value = "answer_time")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date answerTime;



}
