package gaja.dbmatching.controller;

import gaja.dbmatching.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import gaja.dbmatching.service.MemberService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class MemberController {
    private final MemberService memberService;
    @GetMapping("/")
    public String root() {
        return "index";
    }

    /**
     * 회원가입 폼(Get 요청)
     */
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    /**
     * 회원가입 폼 제출(Post 요청)
     */
    @PostMapping("/register")
    public boolean register(@RequestBody MemberDto dto) {
        memberService.joinMember(dto);
        return true;
    }
}
