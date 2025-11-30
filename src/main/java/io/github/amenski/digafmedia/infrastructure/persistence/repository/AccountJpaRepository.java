package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.infrastructure.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountEntity, Long> {
    
    Optional<AccountEntity> findByUsername(String username);
    
    Optional<AccountEntity> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT COUNT(a) > 0 FROM AccountEntity a WHERE a.id = :id")
    boolean existsById(@Param("id") Long id);
}