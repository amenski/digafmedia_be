package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM UserEntity u WHERE u.username = :username AND u.active = true")
    Optional<UserEntity> findByUsernameAndActiveTrue(@Param("username") String username);
    
    @Query("SELECT u FROM UserEntity u WHERE u.email = :email AND u.active = true")
    Optional<UserEntity> findByEmailAndActiveTrue(@Param("email") String email);
}