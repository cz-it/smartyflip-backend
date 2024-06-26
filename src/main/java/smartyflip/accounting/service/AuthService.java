package smartyflip.accounting.service;

import jakarta.servlet.http.Cookie;

public interface AuthService {

    String generateAccessToken(String username);

    String generateRefreshToken(String username);

    Cookie createAccessTokenCookie(String username);

    Cookie createRefreshToken(String username);
}
