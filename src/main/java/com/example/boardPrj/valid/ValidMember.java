package com.example.boardPrj.valid;

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
public class ValidMember {

    @NotBlank(message = "아이디는 필수 입력 사항입니다.")
    @Pattern(regexp = "^[a-z0-9]{6,20}$", message = "아이디는 영문(소문자)숫자만 포함한 6자 이상, 15자 이하여야합니다.")
    private String id;

    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함된 8자 ~ 20자의 비밀번호여야 합니다.")
    private String pw;

    @NotBlank(message = "이름은 필수 입력 사항입니다.")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣]{2,5}$", message = "이름은 2자 이상, 5자 이하로 한글만 입력해주세요")
    private String name;

    private String roles;
}
