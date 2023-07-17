package gaja.dbmatching.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import gaja.dbmatching.service.MemberService;
import gaja.dbmatching.domain.Member;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    @GetMapping("/")
    public String root() {
        return "redirect:/login";
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
        return "redirect:/login"; // 회원가입 후 로그인 페이지로 리다이렉트
    }
    /**
     * 로그인 폼(Get 요청)
     */
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    /**
     * 로그인 실패 폼
     * @return
     */
    @GetMapping("/access_denied")
    public String accessDenied() {
        return "access_denied";
    }

    /**
     * 유저 페이지
     */
    @GetMapping("/user_access")
    public String userAccess(Model model, Authentication authentication) {
        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        Member member = (Member) authentication.getPrincipal();  //userDetail 객체를 가져옴
        model.addAttribute("info", member.getEmail() + "의 " + member.getNickname()+ "님");      //유저 아이디
        return "user_access";
    }
}


