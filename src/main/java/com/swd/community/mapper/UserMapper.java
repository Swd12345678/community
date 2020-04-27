package com.swd.community.mapper;

import com.swd.community.model.User;
import org.apache.ibatis.annotations.*;
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

        @Select("select * from user where account_id =#{account_id} ")
        User findByAccountId(@Param("account_id")String account_id);

        @Update("update user set name = #{name},token =#{token}, gmt_modified =#{gmtModified},avatar_url=#{avatarUrl} where id=#{id}")
        void update(User user);
}
