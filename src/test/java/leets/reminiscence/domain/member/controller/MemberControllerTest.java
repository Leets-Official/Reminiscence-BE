package leets.reminiscence.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import leets.reminiscence.domain.member.Member;
import leets.reminiscence.domain.member.dto.MemberSignUpDto;
import leets.reminiscence.domain.member.repository.MemberRepository;
import leets.reminiscence.domain.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {


    @Autowired
    MockMvc mockMvc;
    @Autowired
    EntityManager em;
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    PasswordEncoder passwordEncoder;

    private static String SIGN_UP_URL = "/signUp";

    private String email = "jwnoh@test.com";
    private String password = "password1234@";
    private String username = "shinD";
    private String birthday = "shinD cute";




    private void clear(){
        em.flush();
        em.clear();
    }

    private void signUp(String signUpData) throws Exception {
        mockMvc.perform(
                        post(SIGN_UP_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(signUpData))
                .andExpect(status().isOk());
    }


    @Value("${jwt.access.header}")
    private String accessHeader;

    private static final String BEARER = "Bearer ";

    private String getAccessToken() throws Exception {

        Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);


        MvcResult result = mockMvc.perform(
                        post("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(map)))
                .andExpect(status().isOk()).andReturn();

        return result.getResponse().getHeader(accessHeader);
    }



    @Test
    public void 회원가입_성공() throws Exception {
        //given
        String signUpData = objectMapper.writeValueAsString(new MemberSignUpDto(email, password, username, birthday));

        //when
        signUp(signUpData);

        //then
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new Exception("회원이 없습니다"));
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(memberRepository.findAll().size()).isEqualTo(1);
    }




    @Test
    public void 회원가입_실패_필드가_없음() throws Exception {
        //given
        String noEmailSignUpData = objectMapper.writeValueAsString(new MemberSignUpDto(null, password, username, birthday));
        String noPasswordSignUpData = objectMapper.writeValueAsString(new MemberSignUpDto(email, null, username, birthday));
        String noUsernameSignUpData = objectMapper.writeValueAsString(new MemberSignUpDto(email, password, null, birthday));
        String noBirthdaySignUpData = objectMapper.writeValueAsString(new MemberSignUpDto(email, password, username, null));

        //when, then
        signUp(noEmailSignUpData);//예외가 발생하더라도 상태코드는 200
        signUp(noPasswordSignUpData);//예외가 발생하더라도 상태코드는 200
        signUp(noUsernameSignUpData);//예외가 발생하더라도 상태코드는 200
        signUp(noBirthdaySignUpData);//예외가 발생하더라도 상태코드는 200

        assertThat(memberRepository.findAll().size()).isEqualTo(0);
    }




    @Test
    public void 회원정보수정_성공() throws Exception {
        //given
        String signUpData = objectMapper.writeValueAsString(new MemberSignUpDto(email, password, username, birthday));

        signUp(signUpData);

        String accessToken = getAccessToken();
        Map<String, Object> map = new HashMap<>();
        map.put("username", username+"변경");
        map.put("birthday", birthday+"변경");
        String updateMemberData = objectMapper.writeValueAsString(map);


        //when
        mockMvc.perform(
                        put("/member")
                                .header(accessHeader,BEARER+accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateMemberData))
                .andExpect(status().isOk());

        //then
        Member member = memberRepository.findByEmail(username).orElseThrow(() -> new Exception("회원이 없습니다"));
        assertThat(member.getUsername()).isEqualTo(username+"변경");
        assertThat(member.getBirthday()).isEqualTo(birthday+"변경");
        assertThat(memberRepository.findAll().size()).isEqualTo(1);

    }



    @Test
    public void 회원정보수정_원하는필드만변경_성공() throws Exception {
        //given
        String signUpData = objectMapper.writeValueAsString(new MemberSignUpDto(email, password, username, birthday));
        signUp(signUpData);

        String accessToken = getAccessToken();
        Map<String, Object> map = new HashMap<>();
        map.put("username",username+"변경");
        String updateMemberData = objectMapper.writeValueAsString(map);


        //when
        mockMvc.perform(
                        put("/member")
                                .header(accessHeader,BEARER+accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateMemberData))
                .andExpect(status().isOk());

        //then
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new Exception("회원이 없습니다"));
        assertThat(member.getUsername()).isEqualTo(username+"변경");
        assertThat(member.getBirthday()).isEqualTo(birthday);
        assertThat(memberRepository.findAll().size()).isEqualTo(1);

    }



    @Test
    public void 비밀번호수정_성공() throws Exception {
        //given
        String signUpData = objectMapper.writeValueAsString(new MemberSignUpDto(email, password, username, birthday));
        signUp(signUpData);

        String accessToken = getAccessToken();

        Map<String, Object> map = new HashMap<>();
        map.put("checkPassword", password);
        map.put("toBePassword", password+"!@#@!#@!#");

        String updatePassword = objectMapper.writeValueAsString(map);


        //when
        mockMvc.perform(
                        put("/member/password")
                                .header(accessHeader,BEARER+accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updatePassword))
                .andExpect(status().isOk());

        //then
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new Exception("회원이 없습니다"));
        assertThat(passwordEncoder.matches(password, member.getPassword())).isFalse();
        assertThat(passwordEncoder.matches(password+"!@#@!#@!#", member.getPassword())).isTrue();
    }




    @Test
    public void 비밀번호수정_실패_검증비밀번호가_틀림() throws Exception {
        //given
        String signUpData = objectMapper.writeValueAsString(new MemberSignUpDto(email, password, username, birthday));
        signUp(signUpData);

        String accessToken = getAccessToken();

        Map<String, Object> map = new HashMap<>();
        map.put("checkPassword", password+"1");
        map.put("toBePassword", password+"!@#@!#@!#");

        String updatePassword = objectMapper.writeValueAsString(map);


        //when
        mockMvc.perform(
                        put("/member/password")
                                .header(accessHeader,BEARER+accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updatePassword))
                .andExpect(status().isOk());

        //then
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new Exception("회원이 없습니다"));
        assertThat(passwordEncoder.matches(password, member.getPassword())).isTrue();
        assertThat(passwordEncoder.matches(password+"!@#@!#@!#", member.getPassword())).isFalse();
    }




    @Test
    public void 비밀번호수정_실패_바꾸려는_비밀번호_형식_올바르지않음() throws Exception {
        //given
        String signUpData = objectMapper.writeValueAsString(new MemberSignUpDto(email, password, username, birthday));
        signUp(signUpData);

        String accessToken = getAccessToken();

        Map<String, Object> map = new HashMap<>();
        map.put("checkPassword", password);
        map.put("toBePassword","123123");

        String updatePassword = objectMapper.writeValueAsString(map);


        //when
        mockMvc.perform(
                        put("/member/password")
                                .header(accessHeader,BEARER+accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updatePassword))
                .andExpect(status().isOk());

        //then
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new Exception("회원이 없습니다"));
        assertThat(passwordEncoder.matches(password, member.getPassword())).isTrue();
        assertThat(passwordEncoder.matches("123123", member.getPassword())).isFalse();
    }




    @Test
    public void 회원탈퇴_성공() throws Exception {
        //given
        String signUpData = objectMapper.writeValueAsString(new MemberSignUpDto(email, password, username, birthday));
        signUp(signUpData);

        String accessToken = getAccessToken();

        Map<String, Object> map = new HashMap<>();
        map.put("checkPassword", password);

        String updatePassword = objectMapper.writeValueAsString(map);

        //when
        mockMvc.perform(
                        delete("/member")
                                .header(accessHeader,BEARER+accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updatePassword))
                .andExpect(status().isOk());

        //then
        assertThrows(Exception.class, () -> memberRepository.findByEmail(email).orElseThrow(() -> new Exception("회원이 없습니다")));
    }




    @Test
    public void 회원탈퇴_실패_비밀번호틀림() throws Exception {
        //given
        String signUpData = objectMapper.writeValueAsString(new MemberSignUpDto(email, password, username, birthday));
        signUp(signUpData);

        String accessToken = getAccessToken();

        Map<String, Object> map = new HashMap<>();
        map.put("checkPassword", password+11);

        String updatePassword = objectMapper.writeValueAsString(map);

        //when
        mockMvc.perform(
                        delete("/member")
                                .header(accessHeader,BEARER+accessToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updatePassword))
                .andExpect(status().isOk());

        //then
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new Exception("회원이 없습니다"));
        assertThat(member).isNotNull();


    }



    @Test
    public void 회원탈퇴_실패_권한이없음() throws Exception {
        //given
        String signUpData = objectMapper.writeValueAsString(new MemberSignUpDto(email, password, username, birthday));
        signUp(signUpData);

        String accessToken = getAccessToken();

        Map<String, Object> map = new HashMap<>();
        map.put("checkPassword", password);

        String updatePassword = objectMapper.writeValueAsString(map);

        //when
        mockMvc.perform(
                        delete("/member")
                                .header(accessHeader,BEARER+accessToken+"1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updatePassword))
                .andExpect(status().isForbidden());

        //then
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new Exception("회원이 없습니다"));
        assertThat(member).isNotNull();
    }




    @Test
    public void 내정보조회_성공() throws Exception {
        //given
        String signUpData = objectMapper.writeValueAsString(new MemberSignUpDto(email, password, username, birthday));
        signUp(signUpData);

        String accessToken = getAccessToken();


        //when
        MvcResult result = mockMvc.perform(
                        get("/member")
                                .characterEncoding(StandardCharsets.UTF_8)
                                .header(accessHeader, BEARER + accessToken))
                .andExpect(status().isOk()).andReturn();


        //then
        Map<String, Object> map = objectMapper.readValue(result.getResponse().getContentAsString(), Map.class);
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new Exception("회원이 없습니다"));
        assertThat(member.getBirthday()).isEqualTo(map.get("birthday"));
        assertThat(member.getUsername()).isEqualTo(map.get("username"));
        assertThat(member.getEmail()).isEqualTo(map.get("email"));

    }



    @Test
    public void 내정보조회_실패_JWT없음() throws Exception {
        //given
        String signUpData = objectMapper.writeValueAsString(new MemberSignUpDto(email, password, username, birthday));
        signUp(signUpData);

        String accessToken = getAccessToken();


        //when,then
        mockMvc.perform(
                        get("/member")
                                .characterEncoding(StandardCharsets.UTF_8)
                                .header(accessHeader, BEARER + accessToken+1))
                .andExpect(status().isForbidden());

    }



    /**
     * 회원정보조회 성공
     * 회원정보조회 실패 -> 회원이없음
     * 회원정보조회 실패 -> 권한이없음
     */
    @Test
    public void 회원정보조회_성공() throws Exception {
        //given
        String signUpData = objectMapper.writeValueAsString(new MemberSignUpDto(email, password, username, birthday));
        signUp(signUpData);

        String accessToken = getAccessToken();

        String email = memberRepository.findAll().get(0).getEmail();

        //when

        MvcResult result = mockMvc.perform(
                        get("/member/"+email)
                                .characterEncoding(StandardCharsets.UTF_8)
                                .header(accessHeader, BEARER + accessToken))
                .andExpect(status().isOk()).andReturn();


        //then
        Map<String, Object> map = objectMapper.readValue(result.getResponse().getContentAsString(), Map.class);
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new Exception("회원이 없습니다"));
        assertThat(member.getBirthday()).isEqualTo(map.get("birthday"));
        assertThat(member.getUsername()).isEqualTo(map.get("username"));
        assertThat(member.getEmail()).isEqualTo(map.get("email"));
    }



    @Test
    public void 회원정보조회_실패_없는회원조회() throws Exception {
        //given
        String signUpData = objectMapper.writeValueAsString(new MemberSignUpDto(email, password, username, birthday));
        signUp(signUpData);

        String accessToken = getAccessToken();


        //when
        MvcResult result = mockMvc.perform(
                        get("/member/2211")
                                .characterEncoding(StandardCharsets.UTF_8)
                                .header(accessHeader, BEARER + accessToken))
                .andExpect(status().isOk()).andReturn();

        //then
        assertThat(result.getResponse().getContentAsString()).isEqualTo("");//빈 문자열
    }



    @Test
    public void 회원정보조회_실패_JWT없음() throws Exception {
        //given
        String signUpData = objectMapper.writeValueAsString(new MemberSignUpDto(email, password, username, birthday));
        signUp(signUpData);

        String accessToken = getAccessToken();


        //when,then
        mockMvc.perform(
                        get("/member/1")
                                .characterEncoding(StandardCharsets.UTF_8)
                                .header(accessHeader, BEARER + accessToken+1))
                .andExpect(status().isForbidden());

    }

}