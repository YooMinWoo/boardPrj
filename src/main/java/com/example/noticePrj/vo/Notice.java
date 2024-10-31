package com.example.noticePrj.vo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@Getter
public class Notice {
    private Long id;
    private String member_id;
    private String title;
    private String content;
    private Long view_count;
    private Date create_date;
    private Date update_date;
}
