package com.example.noticePrj.dto;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class SearchDTO {
    private final int showCount = 10;
    private final int size;
    private final String keyword;

    public SearchDTO(int page_num, String keyword) {
        this.size = this.showCount * (page_num - 1);
        this.keyword = keyword;
    }
}
