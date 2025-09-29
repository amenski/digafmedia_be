package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.Comment;
import io.github.amenski.digafmedia.usecase.port.CommentRepository;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.CommentEntity;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentDbRepository implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;

    public CommentDbRepository(CommentJpaRepository commentJpaRepository) {
        this.commentJpaRepository = commentJpaRepository;
    }

    @Override
    public List<Comment> findAll() {
        return commentJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Comment save(Comment comment) {
        CommentEntity entity = toEntity(comment);
        if (entity.getId() == null) {
            entity.setCreatedAt(OffsetDateTime.now());
        }
        entity.setModifiedAt(OffsetDateTime.now());
        return toDomain(commentJpaRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        commentJpaRepository.deleteById(id);
    }

    private Comment toDomain(CommentEntity entity) {
        return new Comment(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getContent(),
                entity.getCreatedAt()
        );
    }

    private CommentEntity toEntity(Comment comment) {
        CommentEntity entity = new CommentEntity();
        entity.setId(comment.id());
        entity.setName(comment.name());
        entity.setEmail(comment.email());
        entity.setContent(comment.content());
        return entity;
    }
}
