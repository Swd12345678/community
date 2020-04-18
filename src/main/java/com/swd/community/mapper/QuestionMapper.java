package com.swd.community.mapper;

import com.swd.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by myth on 2020/4/14 11:30
 */
@Component
@Mapper
public interface QuestionMapper {

    //点击发布提问信息，发布成功插入一条信息
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    public void create (Question question);

    //查question表中的全部信息
    @Select("select * from question limit #{size}  offset #{offset} ")
    List<Question> list(@Param(value="offset") Integer offset, @Param(value="size") Integer size);

    @Select("select count(1) from question")
    Integer count();

}
