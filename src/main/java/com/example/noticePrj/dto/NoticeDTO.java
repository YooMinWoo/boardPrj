package com.example.noticePrj.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@Data
public class NoticeDTO {
    private Long id;
    private String member_id;
    private String title;
    private String content;
    private Long view_count;
    private Date create_date;
    private Date update_date;
}
