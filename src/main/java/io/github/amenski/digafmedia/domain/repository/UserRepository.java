package io.github.amenski.digafmedia.domain.repository;

import io.github.amenski.digafmedia.domain.user.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    User save(User user);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    void deleteById(Long id);
}
