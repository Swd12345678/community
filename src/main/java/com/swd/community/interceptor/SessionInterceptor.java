package com.swd.community.interceptor;

import com.swd.community.mapper.UserMapper;
import com.swd.community.model.User;
import com.swd.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by myth on 2020/4/21 9:33
 */
@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //先把cookie拿到是个数组
        Cookie[] cookies = request.getCookies();
        //cookie判空防止空指针异常
        if (cookies != null && cookies.length!=0) {
            //循环数组找名为token的cookie
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token"))
                {
                    String token=cookie.getValue();
                    //拿此名为token的cookie到数据库中找是否有这么个用户，即该用户是否登录
                    UserExample userExample = new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(userExample);
                    //User user= userMapper.findByToken(token);
                    if(users.size()!=0) {
                        //用户已经登录了，将user对象放进session便于获取用户用户信息，以及维持登录态。
                        request.getSession().setAttribute("user",users.get(0));
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
