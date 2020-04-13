package com.swd.community.mapper;

import com.swd.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * Created by myth on 2020/4/13 20:50
 */
@Component
@Mapper
public interface UserMapper {
        @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified) values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
        void insert ( User user);
}
