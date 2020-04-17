package com.swd.community.service;

import com.swd.community.dto.QuestionDTO;
import com.swd.community.mapper.QuestionMapper;
import com.swd.community.mapper.UserMapper;
import com.swd.community.model.Question;
import com.swd.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myth on 2020/4/15 20:41
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public List<QuestionDTO> list() {
        //所有问题都查出来
        List<Question> list = questionMapper.list();
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for (Question question : list) {
            //对每个问题循环查找用户
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            //找到了该用户
            //用BeanUtils工具类把question,questionDTO相同的属性填入
            BeanUtils.copyProperties(question,questionDTO);
            //questionDTO独特的user手动填入
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        //返回给indexController
        return questionDTOList;
    }
}
