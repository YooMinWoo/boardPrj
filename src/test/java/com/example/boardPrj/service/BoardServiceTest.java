package com.example.boardPrj.service;

import com.example.boardPrj.dto.ResponseBoardDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    public void getAllPage(){
        int pageNum = 1;
        String keyword = "";
        int pageSize = 10;
        String sort = "latest";
        ResponseBoardDTO allPage = boardService.getAllPage(pageNum, keyword, pageSize, sort);
        System.out.println(allPage);
    }

}
