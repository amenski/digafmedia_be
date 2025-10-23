package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.image.PostType;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.PostImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageJpaRepository extends JpaRepository<PostImageEntity, Long> {
    List<PostImageEntity> findByPostTypeAndPostIdOrderByDisplayOrderAsc(PostType postType, Long postId);
    void deleteByPostTypeAndPostId(PostType postType, Long postId);
}
