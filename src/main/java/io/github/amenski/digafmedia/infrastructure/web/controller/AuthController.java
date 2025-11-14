package io.github.amenski.digafmedia.infrastructure.web.controller;

import io.github.amenski.digafmedia.domain.user.LoginCommand;
import io.github.amenski.digafmedia.domain.user.RegisterUserCommand;
import io.github.amenski.digafmedia.infrastructure.web.model.auth.AuthResponse;
import io.github.amenski.digafmedia.infrastructure.web.model.auth.LoginRequest;
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
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
    
    public AuthController(RegisterUserUseCase registerUserUseCase, LoginUseCase loginUseCase) {
        this.registerUserUseCase = registerUserUseCase;
        this.loginUseCase = loginUseCase;
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
        
        // TODO: Generate JWT tokens
        var authResponse = new AuthResponse(
                null, // accessToken - TODO: generate token
                null, // refreshToken - TODO: generate token
                3600L, // expiresIn
                userResponse
        );
        
        logger.info("User registered successfully: {}", request.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }
    
    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Authenticates user credentials and returns authentication tokens")
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
        
        // TODO: Generate JWT tokens
        var authResponse = new AuthResponse(
                null, // accessToken - TODO: generate token
                null, // refreshToken - TODO: generate token
                3600L, // expiresIn
                userResponse
        );
        
        logger.info("User logged in successfully: {}", request.getUsername());
        return ResponseEntity.ok(authResponse);
    }
}