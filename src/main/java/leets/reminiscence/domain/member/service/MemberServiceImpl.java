package leets.reminiscence.domain.member.service;

import leets.reminiscence.domain.member.Member;
import leets.reminiscence.domain.member.dto.MemberSignUpDto;
import leets.reminiscence.domain.member.dto.MemberUpdateDto;
import leets.reminiscence.domain.member.dto.MemberInfoDto;
import leets.reminiscence.domain.member.repository.MemberRepository;
import leets.reminiscence.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public void signUp(MemberSignUpDto memberSignUpDto) throws Exception {
        Member member = memberSignUpDto.toEntity();
        member.addUserAuthority();
        member.encodePassword(passwordEncoder);

        if(memberRepository.findByEmail(memberSignUpDto.getEmail()).isPresent()){
            throw new Exception("이미 존재하는 아이디입니다.");
        }

        memberRepository.save(member);
    }

    @Override
    public void update(MemberUpdateDto memberUpdateDto) throws Exception {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUsername()).orElseThrow(() -> new Exception("회원이 존재하지 않습니다"));

        memberUpdateDto.getUsername().ifPresent(member::setUsername);
        memberUpdateDto.getBirthday().ifPresent(member::setBirthday);
    }


    @Override
    public void updatePassword(String checkPassword, String toBePassword) throws Exception {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUsername()).orElseThrow(() -> new Exception("회원이 존재하지 않습니다"));

        if(!member.matchPassword(passwordEncoder, checkPassword) ) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        member.setPassword(passwordEncoder, toBePassword);
    }


    @Override
    public void withdraw(String checkPassword) throws Exception {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginUsername()).orElseThrow(() -> new Exception("회원이 존재하지 않습니다"));

        if(!member.matchPassword(passwordEncoder, checkPassword) ) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        memberRepository.delete(member);
    }




    @Override
    public MemberInfoDto getInfo(String email) throws Exception {
        Member findMember = memberRepository.findByEmail(email).orElseThrow(() -> new Exception("회원이 없습니다"));
        return new MemberInfoDto(findMember);
    }

    @Override
    public MemberInfoDto getMyInfo() throws Exception {
        Member findMember = memberRepository.findByEmail(SecurityUtil.getLoginUsername()).orElseThrow(() -> new Exception("회원이 없습니다"));
        return new MemberInfoDto(findMember);
    }
}
