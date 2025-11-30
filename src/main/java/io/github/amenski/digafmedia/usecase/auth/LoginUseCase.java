package io.github.amenski.digafmedia.usecase.auth;

import io.github.amenski.digafmedia.domain.DomainValidationException;
import io.github.amenski.digafmedia.domain.repository.UserRepository;
import io.github.amenski.digafmedia.domain.user.LoginCommand;
import io.github.amenski.digafmedia.domain.user.User;
import io.github.amenski.digafmedia.domain.ValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LoginUseCase {
    
    private static final Logger logger = LoggerFactory.getLogger(LoginUseCase.class);
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User execute(LoginCommand command) {
        String correlationId = MDC.get("correlationId");
        logger.debug("Executing login use case - correlationId: {}, username: {}", correlationId, command.getUsername());
        
        // Validate command
        ValidationResult validationResult = command.validate();
        if (!validationResult.isValid()) {
            logger.warn("Login validation failed - correlationId: {}, errors: {}", correlationId, validationResult.getErrors());
            throw new DomainValidationException("Invalid login data", validationResult.getErrors());
        }

        // Find user by username
        Optional<User> userOptional = userRepository.findByUsername(command.getUsername());
        if (userOptional.isEmpty()) {
            logger.warn("User not found during login - correlationId: {}, username: {}", correlationId, command.getUsername());
            throw new DomainValidationException("Invalid credentials",
                ValidationResult.error("username", "Invalid username or password").getErrors());
        }

        User user = userOptional.get();

        // Verify password
        if (!passwordEncoder.matches(command.getPassword(), user.getPasswordHash())) {
            logger.warn("Invalid password during login - correlationId: {}, username: {}", correlationId, command.getUsername());
            throw new DomainValidationException("Invalid credentials",
                ValidationResult.error("password", "Invalid username or password").getErrors());
        }

        // Check if user is active
        if (!user.isActive()) {
            logger.warn("Inactive account attempted login - correlationId: {}, username: {}", correlationId, command.getUsername());
            throw new DomainValidationException("Account deactivated",
                ValidationResult.error("account", "Account is deactivated").getErrors());
        }

        logger.debug("Login use case completed successfully - correlationId: {}, username: {}", correlationId, command.getUsername());
        return user;
    }
}