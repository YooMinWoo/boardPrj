package com.example.boardPrj.vo;

import com.example.boardPrj.dto.MemberDTO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class MemberContext implements UserDetails {

    private MemberDTO memberDTO;
    private final List<GrantedAuthority> roles;

    public MemberContext(MemberDTO memberDTO, List<GrantedAuthority> roles) {
        this.memberDTO = memberDTO;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return memberDTO.getPw();
    }

    @Override
    public String getUsername() {
        return memberDTO.getId();
    }

}
