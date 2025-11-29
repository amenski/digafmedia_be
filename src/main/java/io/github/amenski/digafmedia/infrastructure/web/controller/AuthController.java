package io.github.amenski.digafmedia.infrastructure.web.controller;

import io.github.amenski.digafmedia.domain.DomainValidationException;
import io.github.amenski.digafmedia.domain.ValidationResult;
import io.github.amenski.digafmedia.domain.repository.UserRepository;
import io.github.amenski.digafmedia.domain.user.LoginCommand;
import io.github.amenski.digafmedia.domain.user.RegisterUserCommand;
import io.github.amenski.digafmedia.domain.user.User;
import io.github.amenski.digafmedia.infrastructure.security.CookieUtils;
import io.github.amenski.digafmedia.infrastructure.security.JwtTokenService;
import io.github.amenski.digafmedia.infrastructure.web.model.auth.AuthResponse;
import io.github.amenski.digafmedia.infrastructure.web.model.auth.LoginRequest;
import io.github.amenski.digafmedia.infrastructure.web.model.auth.RefreshTokenRequest;
import io.github.amenski.digafmedia.infrastructure.web.model.auth.RegisterRequest;
import io.github.amenski.digafmedia.infrastructure.web.model.auth.UserResponse;
import io.github.amenski.digafmedia.usecase.auth.LoginUseCase;
import io.github.amenski.digafmedia.usecase.auth.RegisterUserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication endpoints for user registration and login")
public class AuthController {
    
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    private final RegisterUserUseCase registerUserUseCase;
    private final LoginUseCase loginUseCase;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;
    private final CookieUtils cookieUtils;
    
    public AuthController(RegisterUserUseCase registerUserUseCase, LoginUseCase loginUseCase,
                         JwtTokenService jwtTokenService, UserRepository userRepository,
                         CookieUtils cookieUtils) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUseCase = loginUseCase;
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
        this.cookieUtils = cookieUtils;
    }
    
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account and returns authentication tokens")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User registered successfully", 
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Username or email already exists")
    })
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        logger.info("Registering new user with username: {}", request.getUsername());
        
        var command = RegisterUserCommand.createUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );
        
        var user = registerUserUseCase.execute(command);
        
        var userResponse = new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.isActive()
        );
        
        var authResponse = new AuthResponse(
                jwtTokenService.generateAccessToken(user),
                jwtTokenService.generateRefreshToken(user),
                3600L,
                userResponse
        );
        
        logger.info("User registered successfully: {}", request.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }
    
    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Authenticates user credentials and returns authentication tokens. Also sets HttpOnly cookies for web clients.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials"),
        @ApiResponse(responseCode = "403", description = "Account is inactive")
    })
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        logger.info("Login attempt for username: {}", request.getUsername());
        
        var command = LoginCommand.create(
                request.getUsername(),
                request.getPassword()
        );
        
        var user = loginUseCase.execute(command);
        
        var userResponse = new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.isActive()
        );
        
        // Generate JWT tokens
        String accessToken = jwtTokenService.generateAccessToken(user);
        String refreshToken = jwtTokenService.generateRefreshToken(user);
        
        var authResponse = new AuthResponse(
                accessToken,
                refreshToken,
                3600L, // expiresIn
                userResponse
        );
        
        // Set HttpOnly cookies for web clients
        ResponseCookie accessCookie = cookieUtils.buildAccessTokenCookie(accessToken, 1800); // 30 minutes
        ResponseCookie refreshCookie = cookieUtils.buildRefreshTokenCookie(refreshToken, 2592000); // 30 days
        
        logger.info("User logged in successfully: {}", request.getUsername());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(authResponse);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh JWT tokens", description = "Refreshes expired access token using a valid refresh token from request body or cookie")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tokens refreshed successfully",
                    content = @Content(schema = @Schema(implementation = AuthResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid refresh token"),
        @ApiResponse(responseCode = "401", description = "Invalid or expired refresh token"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<AuthResponse> refreshTokens(@Valid @RequestBody RefreshTokenRequest request, HttpServletRequest httpRequest) {
        logger.info("Refreshing tokens");
        
        String refreshToken;
        if (request.getRefreshToken() != null && !request.getRefreshToken().isBlank()) {
            // Use refresh token from request body (for mobile/native clients)
            refreshToken = request.getRefreshToken();
        } else {
            // Try to get refresh token from cookie (for web clients)
            refreshToken = cookieUtils.getRefreshTokenFromCookie(httpRequest);
            if (refreshToken == null) {
                throw new DomainValidationException("No refresh token provided",
                    ValidationResult.error("refreshToken", "Refresh token is required").getErrors());
            }
        }
        
        // Validate refresh token
        if (!jwtTokenService.validateToken(refreshToken)) {
            throw new DomainValidationException("Invalid refresh token",
                ValidationResult.error("refreshToken", "Invalid or expired refresh token").getErrors());
        }
        
        // Extract username from refresh token
        String username = jwtTokenService.getUsernameFromToken(refreshToken);
        
        // Find user by username
        var userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new DomainValidationException("User not found", ValidationResult.error("user", "User not found").getErrors());
        }
        
        User user = userOptional.get();
        
        // Check if user is active
        if (!user.isActive()) {
            throw new DomainValidationException("Account deactivated", ValidationResult.error("account", "Account is deactivated").getErrors());
        }
        
        var userResponse = new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.isActive()
        );
        
        // Generate new tokens
        String newAccessToken = jwtTokenService.generateAccessToken(user);
        String newRefreshToken = jwtTokenService.generateRefreshToken(user);
        
        var authResponse = new AuthResponse(
                newAccessToken,
                newRefreshToken,
                3600L, // expiresIn
                userResponse
        );
        
        // Set new HttpOnly cookies for web clients
        ResponseCookie accessCookie = cookieUtils.buildAccessTokenCookie(newAccessToken, 1800); // 30 minutes
        ResponseCookie refreshCookie = cookieUtils.buildRefreshTokenCookie(newRefreshToken, 2592000); // 30 days
        
        logger.info("Tokens refreshed successfully for user: {}", username);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(authResponse);
    }
    @PostMapping("/logout")
    @Operation(summary = "Logout user", description = "Clears authentication cookies and invalidates the session")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Logout successful"),
        @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<Void> logout() {
        logger.info("Logging out user");
        
        // Clear authentication cookies
        ResponseCookie[] clearedCookies = cookieUtils.clearAuthCookies();
        
        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, clearedCookies[0].toString())
                .header(HttpHeaders.SET_COOKIE, clearedCookies[1].toString())
                .build();
    }
}