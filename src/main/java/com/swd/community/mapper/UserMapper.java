package com.swd.community.mapper;

import com.swd.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * Created by myth on 2020/4/13 20:50
 */
@Component
@Mapper
public interface UserMapper {
        //登录成功插入一条用户信息
        @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,avatar_url) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
        void insert ( User user);

        //根据token判断用户是否登录
        @Select("select * from user where token =#{token} ")
        User findByToken(@Param("token") String token);

        //根据Creator就是id拿用户，在QuestionService中使用
        @Select("select * from user where id =#{id} ")
        User findById(@Param("id")Integer creator);
}
