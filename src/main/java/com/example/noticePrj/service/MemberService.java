package com.example.noticePrj.service;

import com.example.noticePrj.exception.DuplicateIdException;
import com.example.noticePrj.mappers.MemberMapper;
import com.example.noticePrj.valid.ValidMember;
import com.example.noticePrj.vo.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;


    public void signup(ValidMember validMember) throws DuplicateIdException {
        String pw = passwordEncoder.encode(validMember.getPw());

        if(findId(validMember.getId()) != null) throw new DuplicateIdException("이미 사용 중인 아이디입니다.");

        Member member = new Member(validMember.getId(), pw, validMember.getName(), validMember.getRoles());
        memberMapper.signup(member);
    }

    public Member findId(String id){
        return memberMapper.findId(id);
    }

}
