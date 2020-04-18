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

    public void setPagination(Integer totalCount, Integer page, Integer size) {

        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage=totalCount / size +1 ;
        }

        if(page<1)
        {
            page=1;
        }
        if(page>totalPage)
        {
            page=totalPage;
        }
        this.page=page;
        pages.add(page);
        for(int i=1;i<=3;i++) {
            if(page - i >0 ){
                pages.add(0,page -i);
            }
            if(page + i <= totalPage){
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
