package com.example.noticePrj.service;

import com.example.noticePrj.dto.ResponseNoticeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class NoticeServiceTest {

    @Autowired
    private NoticeService noticeService;

    @Test
    public void getAllPage(){
        int pageNum = 1;
        String keyword = "";
        int pageSize = 10;
        String sort = "latest";
        ResponseNoticeDTO allPage = noticeService.getAllPage(pageNum, keyword, pageSize, sort);
        System.out.println(allPage);
    }

}
