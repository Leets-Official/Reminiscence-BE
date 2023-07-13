package com.zero100study.demo.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

@Data
public class Member implements UserDetails {
    private String memberId; // 이메일 주소
    private String memberPwd; // 비밀번호
    private String mName; // 사용자 닉네임
    private String mDate; // 사용자 계정 생성일
    private String mGradeStr; //사용자 권한

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.mGradeStr));
    }

    @Override
    public String getUsername() {
        return this.memberId;
    }

    @Override
    public String getPassword() {
        return this.memberPwd;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
