package com.zero100study.demo.service;

import com.zero100study.demo.domain.Member;
import com.zero100study.demo.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
    Date time = new Date();
    String localTime = format.format(time);

    private final MemberMapper memberMapper;

    @Transactional
    public void joinMember(Member member) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setMemberPwd(passwordEncoder.encode(member.getPassword()));
        member.setMGradeStr("USER");
        member.setMDate(localTime);
        memberMapper.save(member);
    }

    @Override
    public Member loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = memberMapper.getMemberAccount(memberId);
        if (member == null){
            throw new UsernameNotFoundException("User not authorized.");
        }
        return member;
    }
}
