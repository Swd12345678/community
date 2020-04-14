package com.swd.community.dto;

import lombok.Data;

/**
 * Created by myth on 2020/4/10 18:20
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;

}
