package gaja.dbmatching.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberDto {
    private String nickname;
    private String email;
    private String pw;
    private String createDate;
}
