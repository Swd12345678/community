package com.swd.community.provider;

import com.alibaba.fastjson.JSON;
import com.swd.community.dto.AccessTokenDTO;

import com.swd.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by myth on 2020/4/10 17:30
 */
@Component
public class GithubProvider {
    //ioc
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
         MediaType mediaType = MediaType.get("application/json; charset=utf-8");
         OkHttpClient client = new OkHttpClient();
         RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
         Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
         try (Response response = client.newCall(request).execute()) {
             String string= response.body().string();
             String[] split = string.split("&");
             String tokenstr=split[0];
             String token = tokenstr.split("=")[1];
             return token;
         } catch (Exception e) {
             e.printStackTrace();
         }
         return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return  githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}