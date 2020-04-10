package com.swd.community.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by myth on 2020/4/9
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String index()
    {
        return "index";
    }
}