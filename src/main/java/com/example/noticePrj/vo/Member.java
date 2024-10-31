package com.example.noticePrj.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Member {
    private final String id;
    private final String pw;
    private final String name;
    private final String roles;
}
