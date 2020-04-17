package com.swd.community.controller;

import com.swd.community.dto.QuestionDTO;
import com.swd.community.mapper.QuestionMapper;
import com.swd.community.mapper.UserMapper;
import com.swd.community.model.Question;
import com.swd.community.model.User;
import com.swd.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by myth on 2020/4/9
 */
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    //接收get请求
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model)
    {
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
                    User user= userMapper.findByToken(token);
                    if(user!=null)
                    {
                        //用户已经登录了，将user对象放进session便于获取用户用户信息，以及维持登录态。
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }
        //不管用户登录与否都要展示数据库中的question表中的信息，用questionService.list();去两个mapper查然后返回一个list
        //将list装进model便于前端循环展示。
        List<QuestionDTO> questionList=questionService.list();
        model.addAttribute("questions",questionList);
        //以上操作都是发生在用户在浏览器输入网址主页按下回车，到浏览器加载出网址主页的过程中，属于一个get请求
        return "index";
    }
}
