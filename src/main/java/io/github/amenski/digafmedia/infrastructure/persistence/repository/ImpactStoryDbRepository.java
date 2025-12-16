package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.repository.ImpactStoryRepository;
import io.github.amenski.digafmedia.domain.story.ImpactStory;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.ImpactStoryEntity;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ImpactStoryDbRepository implements ImpactStoryRepository {

    private final ImpactStoryJpaRepository impactStoryJpaRepository;

    public ImpactStoryDbRepository(ImpactStoryJpaRepository impactStoryJpaRepository) {
        this.impactStoryJpaRepository = impactStoryJpaRepository;
    }

    @Override
    public List<ImpactStory> findAll() {
        return impactStoryJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<ImpactStory> findAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return impactStoryJpaRepository.findAllByOrderByCreatedAtDesc(pageable).stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public List<ImpactStory> findApproved() {
        return impactStoryJpaRepository.findByIsApproved(true).stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public List<ImpactStory> findByApprovedPaginated(boolean approved, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return impactStoryJpaRepository.findByIsApproved(approved, pageable).stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public List<ImpactStory> findApprovedPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return impactStoryJpaRepository.findByIsApproved(true, pageable).stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public long count() {
        return impactStoryJpaRepository.count();
    }

    @Override
    public long countByApproved(boolean approved) {
        return impactStoryJpaRepository.findByIsApproved(approved).size();
    }

    @Override
    public long countApproved() {
        return impactStoryJpaRepository.findByIsApproved(true).size();
    }

    @Override
    public Optional<ImpactStory> findById(Long id) {
        return impactStoryJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public boolean existsById(Long id) {
        return impactStoryJpaRepository.existsById(id);
    }

    @Override
    public ImpactStory save(ImpactStory story) {
        ImpactStoryEntity entity = toEntity(story);
        if (entity.getId() == null) {
            entity.setCreatedAt(OffsetDateTime.now());
        }
        entity.setModifiedAt(OffsetDateTime.now());
        return toDomain(impactStoryJpaRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        impactStoryJpaRepository.deleteById(id);
    }

    private ImpactStory toDomain(ImpactStoryEntity entity) {
        return new ImpactStory(
                entity.getId(),
                entity.getTitle(),
                entity.getStory(),
                entity.getAuthorName(),
                entity.getAuthorLocation(),
                entity.getIsApproved(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getCreatedBy()
        );
    }

    private ImpactStoryEntity toEntity(ImpactStory story) {
        ImpactStoryEntity entity = new ImpactStoryEntity();
        entity.setId(story.id());
        entity.setTitle(story.title());
        entity.setStory(story.story());
        entity.setAuthorName(story.authorName());
        entity.setAuthorLocation(story.authorLocation());
        entity.setIsApproved(story.isApproved());
        entity.setCreatedBy(story.createdBy());
        return entity;
    }
}
