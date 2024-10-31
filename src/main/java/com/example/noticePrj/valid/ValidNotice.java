package com.example.noticePrj.valid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidNotice {
    private Long id;

    private String member_id;

    @NotBlank(message = "제목은 필수입니다.")
    @Size(min = 5, max = 20, message = "제목은 5글자 이상, 20글자 이하입니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    @Size(min = 5, max = 200, message = "내용은 5글자 이상, 200글자 이하입니다.")
    private String content;

    private int view_count;
}
