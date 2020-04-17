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
    //这是携带code和state的AccessTokenDTO，通过post github去请求token
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        //okhttp中抄得
         MediaType mediaType = MediaType.get("application/json; charset=utf-8");
         OkHttpClient client = new OkHttpClient();
         //将token转为json传上去
         RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
         Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
         //response接收到，将其拆分前半部分就是我们要的token
         try (Response response = client.newCall(request).execute()) {
             String string= response.body().string();
             //这里怎么判断token是response的前半段的忘了。
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
        //拿我们得到的token去换user信息
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                //在这里get进去
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try {
            //得到的response
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //下面这个神奇的方法就可以直接将得到的json格式转换为githubUser对象，然后返回该对象
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return  githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
