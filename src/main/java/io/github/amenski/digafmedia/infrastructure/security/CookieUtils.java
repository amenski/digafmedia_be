package io.github.amenski.digafmedia.infrastructure.security;

import org.springframework.http.ResponseCookie;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CookieUtils {
    
    private final CookieProperties cookieProperties;
    private final JwtTokenService jwtTokenService;
    
    public CookieUtils(CookieProperties cookieProperties, JwtTokenService jwtTokenService) {
        this.cookieProperties = cookieProperties;
        this.jwtTokenService = jwtTokenService;
    }
    
    /**
     * Builds a secure HttpOnly cookie for authentication tokens
     */
    public ResponseCookie buildCookie(String name, String value, long maxAgeSeconds, String path) {
        ResponseCookie.ResponseCookieBuilder builder = ResponseCookie.from(name, value)
            .httpOnly(true)
            .secure(cookieProperties.isSecure())
            .path(path)
            .maxAge(maxAgeSeconds)
            .sameSite(cookieProperties.getSameSite());
            
        if (cookieProperties.getDomain() != null && !cookieProperties.getDomain().isBlank()) {
            builder.domain(cookieProperties.getDomain());
        }
        
        return builder.build();
    }
    
    /**
     * Builds an access token cookie with short TTL
     */
    public ResponseCookie buildAccessTokenCookie(String accessToken, long maxAgeSeconds) {
        return buildCookie(cookieProperties.getAccessName(), accessToken, maxAgeSeconds, "/");
    }
    
    /**
     * Builds a refresh token cookie with long TTL and restricted path
     */
    public ResponseCookie buildRefreshTokenCookie(String refreshToken, long maxAgeSeconds) {
        return buildCookie(cookieProperties.getRefreshName(), refreshToken, maxAgeSeconds, "/api/v1/auth");
    }
    
    /**
     * Creates a cookie to clear/delete an existing cookie by setting Max-Age=0
     */
    public ResponseCookie clearCookie(String name, String path) {
        ResponseCookie.ResponseCookieBuilder builder = ResponseCookie.from(name, "")
            .httpOnly(true)
            .secure(cookieProperties.isSecure())
            .path(path)
            .maxAge(0)
            .sameSite(cookieProperties.getSameSite());
            
        if (cookieProperties.getDomain() != null && !cookieProperties.getDomain().isBlank()) {
            builder.domain(cookieProperties.getDomain());
        }
        
        return builder.build();
    }
    
    /**
     * Creates cookies to clear both access and refresh tokens
     */
    public ResponseCookie[] clearAuthCookies() {
        return new ResponseCookie[] {
            clearCookie(cookieProperties.getAccessName(), "/"),
            clearCookie(cookieProperties.getRefreshName(), "/api/v1/auth")
        };
    }
    
    /**
     * Extracts refresh token from request cookies
     */
    @Nullable
    public String getRefreshTokenFromCookie(jakarta.servlet.http.HttpServletRequest request) {
        if (request.getCookies() != null && cookieProperties.isEnabled()) {
            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                if (cookieProperties.getRefreshName().equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
    
    /**
     * Validates refresh token from cookie and returns username if valid
     */
    @Nullable
    public String validateRefreshTokenAndGetUsername(jakarta.servlet.http.HttpServletRequest request) {
        String refreshToken = getRefreshTokenFromCookie(request);
        if (refreshToken != null && jwtTokenService.validateToken(refreshToken)) {
            return jwtTokenService.getUsernameFromToken(refreshToken);
        }
        return null;
    }
}