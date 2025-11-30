package io.github.amenski.digafmedia.domain.repository;

import io.github.amenski.digafmedia.domain.user.Account;

import java.util.Optional;

public interface AccountRepository {
    
    Account save(Account account);
    
    Optional<Account> findById(Long id);
    
    Optional<Account> findByUsername(String username);
    
    Optional<Account> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    void deleteById(Long id);
    
    boolean existsById(Long id);
}