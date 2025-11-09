package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.afalgun.AfalgunPost;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunStatus;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.AfalgunPostEntity;
import io.github.amenski.digafmedia.domain.repository.AfalgunRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class AfalgunDbRepository implements AfalgunRepository {

    private final AfalgunJpaRepository afalgunJpaRepository;

    public AfalgunDbRepository(AfalgunJpaRepository afalgunJpaRepository) {
        this.afalgunJpaRepository = afalgunJpaRepository;
    }

    @Override
    public List<AfalgunPost> findAll() {
        return afalgunJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<AfalgunPost> findByStatus(AfalgunStatus status) {
        return afalgunJpaRepository.findByStatus(status).stream().map(this::toDomain).toList();
    }

    @Override
    public List<AfalgunPost> findAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return afalgunJpaRepository.findAll(pageable).stream().map(this::toDomain).toList();
    }

    @Override
    public List<AfalgunPost> findByStatusPaginated(AfalgunStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return afalgunJpaRepository.findByStatus(status, pageable).stream().map(this::toDomain).toList();
    }

    @Override
    public long count() {
        return afalgunJpaRepository.count();
    }

    @Override
    public long countByStatus(AfalgunStatus status) {
        return afalgunJpaRepository.countByStatus(status);
    }

    @Override
    public Optional<AfalgunPost> findById(Long id) {
        return afalgunJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public AfalgunPost save(AfalgunPost post) {
        AfalgunPostEntity entity = toEntity(post);
        if (entity.getId() == null) {
            entity.setCreatedAt(OffsetDateTime.now());
        }
        entity.setModifiedAt(OffsetDateTime.now());
        return toDomain(afalgunJpaRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        afalgunJpaRepository.deleteById(id);
    }

    private AfalgunPost toDomain(AfalgunPostEntity entity) {
        return new AfalgunPost(
                entity.getId(),
                entity.getMissingPersonName(),
                entity.getAge(),
                entity.getLastSeenLocation(),
                entity.getContactName(),
                entity.getContactPhone(),
                entity.getContactEmail(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

    private AfalgunPostEntity toEntity(AfalgunPost post) {
        AfalgunPostEntity entity = new AfalgunPostEntity();
        entity.setId(post.id());
        entity.setMissingPersonName(post.missingPersonName());
        entity.setAge(post.age());
        entity.setLastSeenLocation(post.lastSeenLocation());
        entity.setContactName(post.contactName());
        entity.setContactPhone(post.contactPhone());
        entity.setContactEmail(post.contactEmail());
        entity.setDescription(post.description());
        entity.setStatus(post.status());
        return entity;
    }
}
