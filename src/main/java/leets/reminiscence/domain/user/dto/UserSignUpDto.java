package leets.reminiscence.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserSignUpDto {

    private String email;
    private String password;
    private String nickname;
    private int birthday;
    private int age;
    private String photographerId;
    private String city;

}
