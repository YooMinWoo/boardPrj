package com.example.boardPrj.controller;

import com.example.boardPrj.vo.MemberContext;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String main(@AuthenticationPrincipal MemberContext memberContext){
        return "메인페이지";
    }
}
