package com.example.noticePrj.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Data
public class PagingDTO {
    private final int showCount = 10;
    private final int size;

    public PagingDTO(int page_num) {
        this.size = this.showCount * (page_num - 1);
    }
}
