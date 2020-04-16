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

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model)
    {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length!=0) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token"))
                {
                    String token=cookie.getValue();
                    User user= userMapper.findByToken(token);
                    if(user!=null)
                    {
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        }

        List<QuestionDTO> questionList=questionService.list();
        model.addAttribute("questions",questionList);
        return "index";
    }
}
