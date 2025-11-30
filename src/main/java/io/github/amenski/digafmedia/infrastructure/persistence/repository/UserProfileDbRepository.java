package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.repository.UserProfileRepository;
import io.github.amenski.digafmedia.domain.user.UserProfile;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.UserProfileEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserProfileDbRepository implements UserProfileRepository {
    
    private final UserProfileJpaRepository userProfileJpaRepository;

    public UserProfileDbRepository(UserProfileJpaRepository userProfileJpaRepository) {
        this.userProfileJpaRepository = userProfileJpaRepository;
    }

    @Override
    public UserProfile save(UserProfile userProfile) {
        UserProfileEntity entity = UserProfileEntity.fromDomain(userProfile);
        UserProfileEntity savedEntity = userProfileJpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<UserProfile> findById(Long id) {
        return userProfileJpaRepository.findById(id)
                .map(UserProfileEntity::toDomain);
    }

    @Override
    public Optional<UserProfile> findByAccountId(Long accountId) {
        return userProfileJpaRepository.findByAccountId(accountId)
                .map(UserProfileEntity::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        userProfileJpaRepository.deleteById(id);
    }

    @Override
    public void deleteByAccountId(Long accountId) {
        userProfileJpaRepository.deleteByAccountId(accountId);
    }

    @Override
    public boolean existsByAccountId(Long accountId) {
        return userProfileJpaRepository.existsByAccountId(accountId);
    }
}