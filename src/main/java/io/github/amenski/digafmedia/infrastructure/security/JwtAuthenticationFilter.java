package io.github.amenski.digafmedia.infrastructure.security;

import io.github.amenski.digafmedia.domain.repository.AccountRepository;
import io.github.amenski.digafmedia.domain.user.Account;
import io.github.amenski.digafmedia.infrastructure.web.security.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenService jwtTokenService;
    private final AccountRepository accountRepository;
    private final CookieProperties cookieProperties;

    public JwtAuthenticationFilter(JwtTokenService jwtTokenService, AccountRepository accountRepository, CookieProperties cookieProperties) {
        this.jwtTokenService = jwtTokenService;
        this.accountRepository = accountRepository;
        this.cookieProperties = cookieProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && jwtTokenService.validateToken(jwt)) {
                String username = jwtTokenService.getUsernameFromToken(jwt);
                
                Optional<Account> accountOptional = accountRepository.findByUsername(username);
                if (accountOptional.isPresent() && accountOptional.get().isActive()) {
                    Account account = accountOptional.get();
                    UserPrincipal userPrincipal = UserPrincipal.create(account);
                    
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userPrincipal, null, userPrincipal.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    
                    String correlationId = MDC.get("correlationId");
                    logger.debug("Authenticated user - correlationId: {}, username: {}", correlationId, username);
                }
            }
        } catch (Exception ex) {
            String correlationId = MDC.get("correlationId");
            logger.error("Could not set user authentication in security context - correlationId: {}", correlationId, ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        // First try to get JWT from cookie
        String jwtFromCookie = getJwtFromCookie(request);
        if (StringUtils.hasText(jwtFromCookie)) {
            return jwtFromCookie;
        }
        
        // Fallback to Authorization header
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        
        return null;
    }
    
    private String getJwtFromCookie(HttpServletRequest request) {
        if (request.getCookies() != null && cookieProperties.isEnabled()) {
            for (Cookie cookie : request.getCookies()) {
                if (cookieProperties.getAccessName().equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}