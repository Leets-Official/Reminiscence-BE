package leets.reminiscence.global.jwt.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public interface JwtService {

    String createAccessToken(String email);
    String createRefreshToken();

    void updateRefreshToken(String email, String refreshToken);

    void destroyRefreshToken(String email);

    void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken);
    void sendAccessToken(HttpServletResponse response, String accessToken);

//    void sendToken(HttpServletResponse response, String accessToken, String refreshToken) throws IOException;
//    String extractAccessToken(HttpServletRequest request) throws IOException, ServletException;
//    String extractRefreshToken(HttpServletRequest request) throws IOException, ServletException;
//    String extractUsername(String accessToken);

    Optional<String> extractAccessToken(HttpServletRequest request);

    Optional<String> extractRefreshToken(HttpServletRequest request);

    Optional<String> extractUsername(String accessToken);

    void setAccessTokenHeader(HttpServletResponse response, String accessToken);
    void setRefreshTokenHeader(HttpServletResponse response, String refreshToken);

    boolean isTokenValid(String token);
}