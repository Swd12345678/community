package com.swd.community.dto;

import lombok.Data;

/**
 * Created by myth on 2020/4/10 18:20
 */
@Data
public class GithubUser {
    //从github的user接口返回的json解析成的实体
    private String name;
    private Long id;
    private String bio;
    //fastjson可将下划线自动映射驼峰
    private String avatarUrl;

}
