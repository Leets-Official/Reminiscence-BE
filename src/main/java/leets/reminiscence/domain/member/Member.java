package leets.reminiscence.domain.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

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
    private Role role; //권한 -> USER, ADMIN
    @Column(length = 1000)
    private String refreshToken; //JWT RefreshToken

    /////////////////////////////

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

    public void setRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
    public void destroyRefreshToken(){
        this.refreshToken = null;
    }
}
