package com.example.noticePrj.valid;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ValidId {

    @NotBlank(message = "아이디는 필수 입력 사항입니다.")
    @Pattern(regexp = "^[a-z0-9]{6,20}$", message = "아이디는 영문(소문자)숫자만 포함한 6자 이상, 15자 이하여야합니다.")
    private String id;
}
