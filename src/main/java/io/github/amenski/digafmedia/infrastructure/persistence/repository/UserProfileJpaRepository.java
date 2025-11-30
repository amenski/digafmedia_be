package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.infrastructure.persistence.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileJpaRepository extends JpaRepository<UserProfileEntity, Long> {
    
    Optional<UserProfileEntity> findByAccountId(Long accountId);
    
    @Query("SELECT COUNT(up) > 0 FROM UserProfileEntity up WHERE up.accountId = :accountId")
    boolean existsByAccountId(@Param("accountId") Long accountId);
    
    void deleteByAccountId(Long accountId);
}