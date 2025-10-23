package io.github.amenski.digafmedia.domain.repository;

import io.github.amenski.digafmedia.domain.image.PostImage;
import io.github.amenski.digafmedia.domain.image.PostType;

import java.util.List;
import java.util.Optional;

public interface PostImageRepository {

    List<PostImage> findAll();

    List<PostImage> findByPostTypeAndPostId(PostType postType, Long postId);

    Optional<PostImage> findById(Long id);

    PostImage save(PostImage image);

    void deleteById(Long id);

    void deleteByPostTypeAndPostId(PostType postType, Long postId);
}
