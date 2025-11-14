package io.github.amenski.digafmedia.usecase.auth;

import io.github.amenski.digafmedia.domain.DomainValidationException;
import io.github.amenski.digafmedia.domain.repository.UserRepository;
import io.github.amenski.digafmedia.domain.user.LoginCommand;
import io.github.amenski.digafmedia.domain.user.User;
import io.github.amenski.digafmedia.domain.ValidationResult;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LoginUseCase {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User execute(LoginCommand command) {
        // Validate command
        ValidationResult validationResult = command.validate();
        if (!validationResult.isValid()) {
            throw new DomainValidationException("Invalid login data", validationResult.getErrors());
        }

        // Find user by username
        Optional<User> userOptional = userRepository.findByUsername(command.getUsername());
        if (userOptional.isEmpty()) {
            throw new DomainValidationException("Invalid credentials", 
                ValidationResult.error("username", "Invalid username or password").getErrors());
        }

        User user = userOptional.get();

        // Verify password
        if (!passwordEncoder.matches(command.getPassword(), user.getPasswordHash())) {
            throw new DomainValidationException("Invalid credentials", 
                ValidationResult.error("password", "Invalid username or password").getErrors());
        }

        // Check if user is active
        if (!user.isActive()) {
            throw new DomainValidationException("Account deactivated", 
                ValidationResult.error("account", "Account is deactivated").getErrors());
        }

        return user;
    }
}