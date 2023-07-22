package leets.reminiscence.global.login.handler;

import leets.reminiscence.domain.member.repository.MemberRepository;
import leets.reminiscence.global.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessJWTProvideHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String email = extractUsername(authentication);
        String accessToken = jwtService.createAccessToken(email);
        String refreshToken = jwtService.createRefreshToken();

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);

        memberRepository.findByEmail(email).ifPresent(
                member -> member.setRefreshToken(refreshToken)
        );

        log.info( "로그인에 성공합니다. username: {}" , email);
        log.info( "AccessToken 을 발급합니다. AccessToken: {}" , accessToken);
        log.info( "RefreshToken 을 발급합니다. RefreshToken: {}" , refreshToken);

    }

    private String extractUsername(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
