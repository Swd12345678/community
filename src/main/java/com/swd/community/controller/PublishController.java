package com.swd.community.controller;

import com.swd.community.dto.QuestionDTO;
import com.swd.community.mapper.QuestionMapper;
import com.swd.community.model.Question;
import com.swd.community.model.User;
import com.swd.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by myth on 2020/4/14 10:26
 */
@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name="id")Integer id,
                       Model model){
        QuestionDTO question = questionService.getById(id);

        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        return "publish";
    }

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
            @RequestParam(value = "id",required = false) Integer id,
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
        User user=(User)request.getSession().getAttribute("user");
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
        question.setId(id);
        //插库
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
