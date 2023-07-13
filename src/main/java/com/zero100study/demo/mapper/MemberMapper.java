package com.zero100study.demo.mapper;

import com.zero100study.demo.domain.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    Member getMemberAccount(String memberId); //로그인

    void save(Member member); //회원가입
}
