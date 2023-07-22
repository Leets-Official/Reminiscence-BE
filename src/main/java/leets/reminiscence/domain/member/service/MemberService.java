package leets.reminiscence.domain.member.service;

import leets.reminiscence.domain.member.dto.MemberInfoDto;
import leets.reminiscence.domain.member.dto.MemberSignUpDto;
import leets.reminiscence.domain.member.dto.MemberUpdateDto;

public interface MemberService {

    /**
     * 회원가입
     * 정보수정
     * 회원탈퇴
     * 정보조회
     */

    void signUp(MemberSignUpDto memberSignUpDto) throws Exception;

    void update(MemberUpdateDto memberUpdateDto) throws Exception;

    void updatePassword(String checkPassword, String toBePassword) throws Exception;

    void withdraw(String checkPassword) throws Exception;

    MemberInfoDto getInfo(String email) throws Exception;

    MemberInfoDto getMyInfo() throws Exception;
}
