package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.repository.AccountRepository;
import io.github.amenski.digafmedia.domain.user.Account;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.AccountEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AccountDbRepository implements AccountRepository {
    
    private final AccountJpaRepository accountJpaRepository;

    public AccountDbRepository(AccountJpaRepository accountJpaRepository) {
        this.accountJpaRepository = accountJpaRepository;
    }

    @Override
    public Account save(Account account) {
        AccountEntity entity = AccountEntity.fromDomain(account);
        AccountEntity savedEntity = accountJpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountJpaRepository.findById(id)
                .map(AccountEntity::toDomain);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountJpaRepository.findByUsername(username)
                .map(AccountEntity::toDomain);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return accountJpaRepository.findByEmail(email)
                .map(AccountEntity::toDomain);
    }

    @Override
    public boolean existsByUsername(String username) {
        return accountJpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return accountJpaRepository.existsByEmail(email);
    }

    @Override
    public void deleteById(Long id) {
        accountJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return accountJpaRepository.existsById(id);
    }
}