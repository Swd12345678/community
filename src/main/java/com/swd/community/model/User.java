package com.swd.community.model;

import lombok.Data;

/**
 * Created by myth on 2020/4/13 20:55
 */
@Data
public class User {
    //user表
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;

}
