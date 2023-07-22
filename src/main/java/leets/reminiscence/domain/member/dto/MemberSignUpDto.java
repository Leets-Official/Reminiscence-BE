package leets.reminiscence.domain.member.dto;

import leets.reminiscence.domain.member.Member;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignUpDto {

    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
            message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min=2, message = "닉네임이 너무 짧습니다.")
    @NotBlank
    private String username;

    @NotBlank(message = "생일을 YYYY-MM-DD 형식으로 입력해주세요")
    @NotBlank
    private String birthday;


    public Member toEntity() {
        return Member.builder().email(email).password(password).username(username).birthday(birthday).build();
    }
}
