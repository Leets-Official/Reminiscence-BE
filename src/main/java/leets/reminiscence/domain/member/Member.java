package leets.reminiscence.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity(name = "member")
public class Member {
    @Id
    private String email;
    private String username;
    private String password;
    private String birthday;
    @Enumerated(EnumType.STRING)
    private Role role;

    /////////////////////////////

//    public void setPassword(String password) {
//        this.password = password;
//    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setBirthday(String birthday){
        this.birthday = birthday;
    }


    public void setPassword(PasswordEncoder passwordEncoder, String password){
        this.password = passwordEncoder.encode(password);
    }

    //== 패스워드 암호화 ==//
    public void encodePassword(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(password);
    }

}
