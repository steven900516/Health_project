package com.lyx.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyx.health.entity.Question;
import com.lyx.health.entity.User;
import com.lyx.health.service.impl.QuestionServiceImpl;
import org.mapstruct.Mapper;

/**
 * @author Steven0516
 * @create 2021-10-25
 */

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

}
