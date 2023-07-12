package gaja.dbmatching.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import gaja.dbmatching.service.MemberService;
import gaja.dbmatching.domain.Member;




@Controller
@RequiredArgsConstructor

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
    public String register(Member member) {
        memberService.joinMember(member);
        return "redirect:/register";
    }

}
