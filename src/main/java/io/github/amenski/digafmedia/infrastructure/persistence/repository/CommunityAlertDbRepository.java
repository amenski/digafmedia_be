package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.alert.AlertUrgency;
import io.github.amenski.digafmedia.domain.alert.CommunityAlert;
import io.github.amenski.digafmedia.domain.repository.CommunityAlertRepository;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.CommunityAlertEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class CommunityAlertDbRepository implements CommunityAlertRepository {

    private final CommunityAlertJpaRepository communityAlertJpaRepository;

    public CommunityAlertDbRepository(CommunityAlertJpaRepository communityAlertJpaRepository) {
        this.communityAlertJpaRepository = communityAlertJpaRepository;
    }

    @Override
    public List<CommunityAlert> findAll() {
        return communityAlertJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<CommunityAlert> findAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return communityAlertJpaRepository.findAllByOrderByCreatedAtDesc(pageable).stream().map(this::toDomain).toList();
    }

    @Override
    public List<CommunityAlert> findRecent(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        return communityAlertJpaRepository.findAllByOrderByCreatedAtDesc(pageable).stream().map(this::toDomain).toList();
    }

    @Override
    public List<CommunityAlert> findByUrgency(AlertUrgency urgency) {
        return communityAlertJpaRepository.findByUrgency(urgency).stream().map(this::toDomain).toList();
    }

    @Override
    public List<CommunityAlert> findByUrgencyPaginated(AlertUrgency urgency, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return communityAlertJpaRepository.findByUrgency(urgency, pageable).stream().map(this::toDomain).toList();
    }

    @Override
    public long count() {
        return communityAlertJpaRepository.count();
    }

    @Override
    public long countByUrgency(AlertUrgency urgency) {
        return communityAlertJpaRepository.findByUrgency(urgency).size();
    }

    @Override
    public Optional<CommunityAlert> findById(Long id) {
        return communityAlertJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public boolean existsById(Long id) {
        return communityAlertJpaRepository.existsById(id);
    }

    @Override
    public CommunityAlert save(CommunityAlert alert) {
        CommunityAlertEntity entity = toEntity(alert);
        if (entity.getId() == null) {
            entity.setCreatedAt(OffsetDateTime.now());
        }
        entity.setModifiedAt(OffsetDateTime.now());
        return toDomain(communityAlertJpaRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        communityAlertJpaRepository.deleteById(id);
    }

    private CommunityAlert toDomain(CommunityAlertEntity entity) {
        return new CommunityAlert(
                entity.getId(),
                entity.getTitle(),
                entity.getMessage(),
                entity.getContactName(),
                entity.getContactPhone(),
                entity.getUrgency(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getCreatedBy()
        );
    }

    private CommunityAlertEntity toEntity(CommunityAlert alert) {
        CommunityAlertEntity entity = new CommunityAlertEntity();
        entity.setId(alert.id());
        entity.setTitle(alert.title());
        entity.setMessage(alert.message());
        entity.setContactName(alert.contactName());
        entity.setContactPhone(alert.contactPhone());
        entity.setUrgency(alert.urgency());
        entity.setCreatedBy(alert.createdBy());
        return entity;
    }
}
