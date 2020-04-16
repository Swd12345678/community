package com.swd.community.dto;

import com.swd.community.model.User;
import lombok.Data;

/**
 * Created by myth on 2020/4/15 20:39
 */
@Data
public class QuestionDTO {
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
    private User user;
}
