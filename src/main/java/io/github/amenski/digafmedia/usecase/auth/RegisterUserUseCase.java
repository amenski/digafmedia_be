package io.github.amenski.digafmedia.usecase.auth;

import io.github.amenski.digafmedia.domain.DomainValidationException;
import io.github.amenski.digafmedia.domain.repository.AccountRepository;
import io.github.amenski.digafmedia.domain.repository.UserProfileRepository;
import io.github.amenski.digafmedia.domain.user.Account;
import io.github.amenski.digafmedia.domain.user.RegisterUserCommand;
import io.github.amenski.digafmedia.domain.user.UserProfile;
import io.github.amenski.digafmedia.domain.ValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegisterUserUseCase {
    
    private static final Logger logger = LoggerFactory.getLogger(RegisterUserUseCase.class);
    
    private final AccountRepository accountRepository;
    private final UserProfileRepository userProfileRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserUseCase(AccountRepository accountRepository,
                              UserProfileRepository userProfileRepository,
                              PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.userProfileRepository = userProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Account execute(RegisterUserCommand command) {
        String correlationId = MDC.get("correlationId");
        logger.debug("Executing register user use case - correlationId: {}, username: {}", correlationId, command.getUsername());
        
        // Validate command
        ValidationResult validationResult = command.validate();
        if (!validationResult.isValid()) {
            logger.warn("Registration validation failed - correlationId: {}, errors: {}", correlationId, validationResult.getErrors());
            throw new DomainValidationException("Invalid registration data", validationResult.getErrors());
        }

        // Check for existing username
        if (accountRepository.existsByUsername(command.getUsername())) {
            logger.warn("Username already exists - correlationId: {}, username: {}", correlationId, command.getUsername());
            throw new DomainValidationException("Username already exists",
                ValidationResult.error("username", "Username is already taken").getErrors());
        }

        // Check for existing email
        if (accountRepository.existsByEmail(command.getEmail())) {
            logger.warn("Email already exists - correlationId: {}, email: {}", correlationId, command.getEmail());
            throw new DomainValidationException("Email already exists",
                ValidationResult.error("email", "Email is already registered").getErrors());
        }

        // Hash password
        String passwordHash = passwordEncoder.encode(command.getPassword());

        // Create account using builder
        Account account = Account.builder()
            .username(command.getUsername())
            .email(command.getEmail())
            .passwordHash(passwordHash)
            .role(command.getRole())
            .build();

        // Save account
        Account savedAccount = accountRepository.save(account);
        
        // Create user profile using builder
        UserProfile userProfile = UserProfile.builder()
            .accountId(savedAccount.getId())
            .build();
        userProfileRepository.save(userProfile);
        
        logger.info("User registered successfully - correlationId: {}, username: {}, accountId: {}",
            correlationId, savedAccount.getUsername(), savedAccount.getId());
        
        return savedAccount;
    }
}