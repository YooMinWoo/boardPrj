package com.example.noticePrj.mappers;

import com.example.noticePrj.vo.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    Member findId(String id);
    void signup(Member member);
}
