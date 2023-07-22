package leets.reminiscence.domain.member.dto;

import leets.reminiscence.domain.member.Member;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberInfoDto {

    private String email;
    private String username;
    private String birthday;


    @Builder
    public MemberInfoDto(Member member) {
        this.email = member.getEmail();
        this.username = member.getUsername();
        this.birthday = member.getBirthday();
    }
}
