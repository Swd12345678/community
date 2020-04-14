package com.swd.community.controller;

import com.swd.community.mapper.QuestionMapper;
import com.swd.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by myth on 2020/4/14 10:26
 */
@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public  String publish()
    {

        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag){
        Question question = new Question();

        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);

        questionMapper.create(question);
        return "publish";
    }
}
