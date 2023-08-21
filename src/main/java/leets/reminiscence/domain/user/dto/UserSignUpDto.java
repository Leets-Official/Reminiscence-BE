package leets.reminiscence.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@NoArgsConstructor
@Getter
public class UserSignUpDto {

    private String email;
    private String password;
    private String nickname;
    @Column(nullable = true)
    private String birthday;
    private int age;
    private String photographerId;
    private String city;

}
