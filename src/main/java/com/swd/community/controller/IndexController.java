package com.swd.community.controller;

import com.swd.community.dto.PaginationDTO;
import com.swd.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by myth on 2020/4/9
 */
@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;

    //接收get请求
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        //默认当前页数
                        @RequestParam(name="page",defaultValue = "1")Integer page,
                        //默认每页条数
                        @RequestParam(name="size",defaultValue = "2")Integer size)
    {

        //不管用户登录与否都要展示数据库中的question表中的信息，用questionService.list();去两个mapper查然后返回一个list
        //将list装进model便于前端循环展示。
        //补充：
        //在对页面分页处理后这里的对象由之前的QuestionDTO换为PaginationDTO--->封装了QuestionDTO
        PaginationDTO paginationDTO=questionService.list(page,size);
        model.addAttribute("paginationDTO",paginationDTO);
        //以上操作都是发生在用户在浏览器输入网址主页按下回车，到浏览器加载出网址主页的过程中，属于一个get请求
        return "index";
    }
}
