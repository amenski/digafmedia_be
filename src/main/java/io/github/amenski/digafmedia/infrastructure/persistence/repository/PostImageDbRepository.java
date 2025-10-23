package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.image.PostImage;
import io.github.amenski.digafmedia.domain.image.PostType;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.PostImageEntity;
import io.github.amenski.digafmedia.domain.repository.PostImageRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class PostImageDbRepository implements PostImageRepository {

    private final PostImageJpaRepository postImageJpaRepository;

    public PostImageDbRepository(PostImageJpaRepository postImageJpaRepository) {
        this.postImageJpaRepository = postImageJpaRepository;
    }

    @Override
    public List<PostImage> findAll() {
        return postImageJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<PostImage> findByPostTypeAndPostId(PostType postType, Long postId) {
        return postImageJpaRepository.findByPostTypeAndPostIdOrderByDisplayOrderAsc(postType, postId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Optional<PostImage> findById(Long id) {
        return postImageJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public PostImage save(PostImage image) {
        PostImageEntity entity = toEntity(image);
        if (entity.getId() == null) {
            entity.setCreatedAt(OffsetDateTime.now());
        }
        entity.setModifiedAt(OffsetDateTime.now());
        return toDomain(postImageJpaRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        postImageJpaRepository.deleteById(id);
    }

    @Override
    public void deleteByPostTypeAndPostId(PostType postType, Long postId) {
        postImageJpaRepository.deleteByPostTypeAndPostId(postType, postId);
    }

    private PostImage toDomain(PostImageEntity entity) {
        return new PostImage(
                entity.getId(),
                entity.getPostType(),
                entity.getPostId(),
                entity.getFilePath(),
                entity.getAltText(),
                entity.getDisplayOrder(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

    private PostImageEntity toEntity(PostImage image) {
        PostImageEntity entity = new PostImageEntity();
        entity.setId(image.id());
        entity.setPostType(image.postType());
        entity.setPostId(image.postId());
        entity.setFilePath(image.filePath());
        entity.setAltText(image.altText());
        entity.setDisplayOrder(image.displayOrder());
        return entity;
    }
}
