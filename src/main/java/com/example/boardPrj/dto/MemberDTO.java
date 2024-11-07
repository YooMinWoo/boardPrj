package com.example.boardPrj.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDTO {
    private String id;
    private String name;
    private String pw;
    private String roles;

    public MemberDTO() {
    }

    public MemberDTO(String id, String name, String pw, String roles) {
        this.id = id;
        this.name = name;
        this.pw = pw;
        this.roles = roles;
    }
}
