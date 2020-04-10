package com.swd.community.community.controller;

import com.swd.community.community.dto.AccessTokenDTO;
import com.swd.community.community.dto.GithubUser;
import com.swd.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by myth on 2020/4/10 17:16
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state){
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id("e203c07480aad4162241");
        accessTokenDTO.setClient_secret("fe17752c5ca4ac029ba4f06dc62c23a4172afc45");

        String accessToken=githubProvider.getAccessToken(accessTokenDTO);

        GithubUser user = githubProvider.getUser(accessToken);
        System.out.printf(user.getName());
        return "index";
    }

}
