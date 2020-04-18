package com.swd.community.service;

import com.swd.community.dto.PaginationDTO;
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

    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);
        if(page<1)
        {
            page=1;
        }
        if(page > paginationDTO.getTotalPage())
        {
            page = paginationDTO.getTotalPage();
        }
        //1 5 0 5
        //2 5 5 5
        //3 5 10 5
        //(page-1)*size
        Integer offset = size * (page - 1);


        //所有问题都查出来
        List<Question> list = questionMapper.list(offset,size);
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

        paginationDTO.setQuestions(questionDTOList);
        paginationDTO.setPage(page);

        return paginationDTO;
    }
}
