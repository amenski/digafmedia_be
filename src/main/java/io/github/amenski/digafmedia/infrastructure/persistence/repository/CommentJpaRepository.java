package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.infrastructure.persistence.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {
}