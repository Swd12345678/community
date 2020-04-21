package com.swd.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by myth on 2020/4/18 13:02
 */
@Data
public class PaginationDTO {
    //问题列表
    private List<QuestionDTO> questions;
    private boolean showprevious;
    private boolean showNext;
    private boolean showFirstPage;
    private boolean showEndPage;
    //当前页
    private Integer page;
    //当前分页条
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public void setPagination(Integer totalPage, Integer page) {

        this.totalPage=totalPage;
        this.page=page;
        //将当前page放进pages列表
        //然后以当前page左右各加3页
        //所以下面的循环只循环3次
        pages.add(page);
        for(int i=1;i<=3;i++) {
            if(page - i >0 ){
                //头插
                pages.add(0,page -i);
            }
            if(page + i <= totalPage){
                //尾插
                pages.add(page+i );
            }
        }
        //是否展示上一页
        if(page == 1)
        {
            showprevious=false;
        }else {
            showprevious=true;
        }
        //是否展示下一页
        if(page ==totalPage)
        {
            showNext=false;
        }else {
            showNext=true;
        }
        //是否展示第一页
        if(pages.contains(1)){
            showFirstPage = false;
        }else {
            showFirstPage=true;
        }
        //是否展示最后页
        if(pages.contains(totalPage)){
            showEndPage = false;
        }else {
            showEndPage=true;
        }
    }
}
