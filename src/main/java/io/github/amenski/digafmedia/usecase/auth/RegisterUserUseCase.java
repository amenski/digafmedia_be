package io.github.amenski.digafmedia.usecase.auth;

import io.github.amenski.digafmedia.domain.DomainValidationException;
import io.github.amenski.digafmedia.domain.repository.UserRepository;
import io.github.amenski.digafmedia.domain.user.RegisterUserCommand;
import io.github.amenski.digafmedia.domain.user.User;
import io.github.amenski.digafmedia.domain.ValidationResult;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegisterUserUseCase {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User execute(RegisterUserCommand command) {
        // Validate command
        ValidationResult validationResult = command.validate();
        if (!validationResult.isValid()) {
            throw new DomainValidationException("Invalid registration data", validationResult.getErrors());
        }

        // Check for existing username
        if (userRepository.existsByUsername(command.getUsername())) {
            throw new DomainValidationException("Username already exists", 
                ValidationResult.error("username", "Username is already taken").getErrors());
        }

        // Check for existing email
        if (userRepository.existsByEmail(command.getEmail())) {
            throw new DomainValidationException("Email already exists", 
                ValidationResult.error("email", "Email is already registered").getErrors());
        }

        // Hash password
        String passwordHash = passwordEncoder.encode(command.getPassword());

        // Create user
        User user = User.create(
            command.getUsername(),
            command.getEmail(),
            passwordHash,
            command.getRole()
        );

        // Save user
        return userRepository.save(user);
    }
}