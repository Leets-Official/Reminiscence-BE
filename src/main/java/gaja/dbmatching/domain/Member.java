package gaja.dbmatching.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

// @Getter, @Setter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode 어노테이션을 한꺼번에 설정해주는 어노테이션
@Data
public class Member implements UserDetails {
    private String mGradeStr; // 사용자 권한(향후 권한 관리에서 사용한다. 지금은 사용하지 않으므로 필드만 삽입해두고 넘어가면 되겠다.)
    private String nickname; // 로그인에 사용되는 닉네임
    private String email; // 이메일
    private String pw; // 비밀번호
    private String createDate; // 생일

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.mGradeStr));
    }

    @Override
    public String getUsername() {
        return this.nickname;
    }

    @Override
    public String getPassword() {
        return this.pw;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
