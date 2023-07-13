package gaja.dbmatching.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDto {
    private String nickname; // 로그인에 사용되는 닉네임
    private String email; // 이메일
    private String pw; // 비밀번호
    private String createDate; // 생일
}
