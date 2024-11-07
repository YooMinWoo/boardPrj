package com.example.boardPrj.mappers;

import com.example.boardPrj.vo.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    Member findId(String id);
    void signup(Member member);
}
