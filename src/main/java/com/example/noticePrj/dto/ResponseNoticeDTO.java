package com.example.noticePrj.dto;

import com.example.noticePrj.vo.Notice;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResponseNoticeDTO {

    // 게시물 리스트
    private List<Notice> noticeList;

    // 키워드
    private String keyword;

    // 현재 페이지
    private int pageNum;

    public void setPageNum(int pageNum) {
        if(pageNum == 0) pageNum = 1;
        this.pageNum = pageNum;
    }

    // 보여지는 게시물 개수
    private int pageSize;

    public void setPageSize(int pageSize) {
        if(pageSize < 10 || pageSize > 30) pageSize = 10;
        this.pageSize = pageSize;
    }

    // 게시물의 총 개수
    private int totalEles;

    // 총 페이지
    private int totalPage;

    // 정렬 종류
    private String sort;

    // 마지막 페이지 여부
    private boolean last;
}
