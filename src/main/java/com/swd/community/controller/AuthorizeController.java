package com.swd.community.controller;

import com.swd.community.dto.AccessTokenDTO;
import com.swd.community.dto.GithubUser;
import com.swd.community.mapper.UserMapper;
import com.swd.community.model.User;
import com.swd.community.provider.GithubProvider;
import com.swd.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by myth on 2020/4/10 17:16
 */
@Controller
@PropertySource({"classpath:application.properties"})
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientID;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Autowired
    private UserService userService;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code")String code,
                           @RequestParam(name="state")String state,
                           HttpServletResponse response){
        //本控制器的目的就是传输携带包括code和state的DTO对象到github端用code换token
        AccessTokenDTO accessTokenDTO=new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientID);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        //携带code的AccessToken对象换token
        String accessToken=githubProvider.getAccessToken(accessTokenDTO);
        //携带token换githubUser
        GithubUser githubUser = githubProvider.getUser(accessToken);
        //if拿到了githubUser
        // 需要将githubUser做持久化 即存进数据库
        if(githubUser!=null && githubUser.getId()!=null)
        {
            User user = new User();
            //使用uuid生成token为36位随机机器码，一方面保证唯一性，另一方面保证数据格式一致
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdate(user);
            //并生成一条cookie可以用来校验登录态
            response.addCookie(new Cookie("token",token));
            //返回主页
            return "redirect:/";
        }
        else {
            return "redirect:/";
        }
    }
    @GetMapping("/logout")
    public String callback(HttpServletRequest request,
                           HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie=new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
