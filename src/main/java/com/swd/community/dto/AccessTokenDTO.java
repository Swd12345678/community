package com.swd.community.dto;

import lombok.Data;

/**
 * Created by myth on 2020/4/10 17:34
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
