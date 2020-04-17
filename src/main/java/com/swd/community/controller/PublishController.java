package com.swd.community.controller;

import com.swd.community.mapper.QuestionMapper;
import com.swd.community.mapper.UserMapper;
import com.swd.community.model.Question;
import com.swd.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by myth on 2020/4/14 10:26
 */
@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            //非实体参数需要写@RequestParam注解
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model) {
        //回显
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        //判空
        if(title == null || title.trim().length()== 0)
        {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if(description == null || description.trim().length()== 0)
        {
            model.addAttribute("error", "问题补种不能为空");
            return "publish";
        }
        if(tag == null || tag.trim().length()== 0)
        {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        //判登录与否
        User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length!=0) {
            for (Cookie cookie : cookies) {

                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    //库中寻找
                    user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
                //找到了放session
            }
        }
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            //未登录就返回错误信息：未登录
            return "publish";
        }
        //各种信息都添加好
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        //插库
        questionMapper.create(question);
        return "redirect:/";
    }
}
