package com.swd.community.model;

import lombok.Data;

/**
 * Created by myth on 2020/4/14 11:35
 */
@Data
public class Question {
    //question表
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;

}
