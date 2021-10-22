package com.lyx.health.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author Steven0516
 * @create 2021-10-22
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("passage")
public class Passage extends Model<Passage> {

    @TableId(value = "id",type = IdType.AUTO)
    private int id;

    @TableField(value = "passage_title")
    private String passageTitle;

    @TableField(value = "passage_small_title")
    private String passageSmallTitle;


    @TableField(value = "passage_content")
    private String passageContent;

    @TableField(value = "passage_category")
    private String passageCategory;

    @TableField(value = "passage_img")
    private String passageImg;



}
