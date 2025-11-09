package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.withyou.WithYouTestimonial;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.WithYouTestimonialEntity;
import io.github.amenski.digafmedia.domain.repository.WithYouRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class WithYouDbRepository implements WithYouRepository {

    private final WithYouJpaRepository withYouJpaRepository;

    public WithYouDbRepository(WithYouJpaRepository withYouJpaRepository) {
        this.withYouJpaRepository = withYouJpaRepository;
    }

    @Override
    public List<WithYouTestimonial> findAll() {
        return withYouJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<WithYouTestimonial> findApproved() {
        return withYouJpaRepository.findByIsApproved(true).stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<WithYouTestimonial> findById(Long id) {
        return withYouJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public WithYouTestimonial save(WithYouTestimonial testimonial) {
        WithYouTestimonialEntity entity = toEntity(testimonial);
        if (entity.getId() == null) {
            entity.setCreatedAt(OffsetDateTime.now());
        }
        entity.setModifiedAt(OffsetDateTime.now());
        return toDomain(withYouJpaRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        withYouJpaRepository.deleteById(id);
    }

    @Override
    public List<WithYouTestimonial> findAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return withYouJpaRepository.findAll(pageable).stream().map(this::toDomain).toList();
    }

    @Override
    public List<WithYouTestimonial> findByApprovedPaginated(boolean approved, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return withYouJpaRepository.findByIsApproved(approved, pageable).stream().map(this::toDomain).toList();
    }

    @Override
    public long count() {
        return withYouJpaRepository.count();
    }

    @Override
    public long countByApproved(boolean approved) {
        return withYouJpaRepository.countByIsApproved(approved);
    }

    private WithYouTestimonial toDomain(WithYouTestimonialEntity entity) {
        return new WithYouTestimonial(
                entity.getId(),
                entity.getTitle(),
                entity.getStory(),
                entity.getAuthorName(),
                entity.getAuthorLocation(),
                entity.getIsApproved(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

    private WithYouTestimonialEntity toEntity(WithYouTestimonial testimonial) {
        WithYouTestimonialEntity entity = new WithYouTestimonialEntity();
        entity.setId(testimonial.id());
        entity.setTitle(testimonial.title());
        entity.setStory(testimonial.story());
        entity.setAuthorName(testimonial.authorName());
        entity.setAuthorLocation(testimonial.authorLocation());
        entity.setIsApproved(testimonial.isApproved());
        return entity;
    }
}
