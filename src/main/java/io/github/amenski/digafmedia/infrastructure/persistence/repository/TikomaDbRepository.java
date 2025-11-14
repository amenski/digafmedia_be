package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.tikoma.TikomaAlert;
import io.github.amenski.digafmedia.domain.tikoma.TikomaUrgency;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.TikomaAlertEntity;
import io.github.amenski.digafmedia.domain.repository.TikomaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TikomaDbRepository implements TikomaRepository {

    private final TikomaJpaRepository tikomaJpaRepository;

    public TikomaDbRepository(TikomaJpaRepository tikomaJpaRepository) {
        this.tikomaJpaRepository = tikomaJpaRepository;
    }

    @Override
    public List<TikomaAlert> findAll() {
        return tikomaJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<TikomaAlert> findRecent(int limit) {
        return tikomaJpaRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, limit))
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<TikomaAlert> findByUrgency(TikomaUrgency urgency) {
        return tikomaJpaRepository.findByUrgency(urgency).stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<TikomaAlert> findById(Long id) {
        return tikomaJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public boolean existsById(Long id) {
        return tikomaJpaRepository.existsById(id);
    }

    @Override
    public TikomaAlert save(TikomaAlert alert) {
        TikomaAlertEntity entity = toEntity(alert);
        if (entity.getId() == null) {
            entity.setCreatedAt(OffsetDateTime.now());
        }
        entity.setModifiedAt(OffsetDateTime.now());
        return toDomain(tikomaJpaRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        tikomaJpaRepository.deleteById(id);
    }

    @Override
    public List<TikomaAlert> findAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return tikomaJpaRepository.findAllPaginated(pageable).stream().map(this::toDomain).toList();
    }

    @Override
    public List<TikomaAlert> findByUrgencyPaginated(TikomaUrgency urgency, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return tikomaJpaRepository.findByUrgencyPaginated(urgency, pageable).stream().map(this::toDomain).toList();
    }

    @Override
    public long count() {
        return tikomaJpaRepository.count();
    }

    @Override
    public long countByUrgency(TikomaUrgency urgency) {
        return tikomaJpaRepository.countByUrgency(urgency);
    }

    private TikomaAlert toDomain(TikomaAlertEntity entity) {
        return new TikomaAlert(
                entity.getId(),
                entity.getTitle(),
                entity.getMessage(),
                entity.getContactName(),
                entity.getContactPhone(),
                entity.getUrgency(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

    private TikomaAlertEntity toEntity(TikomaAlert alert) {
        TikomaAlertEntity entity = new TikomaAlertEntity();
        entity.setId(alert.id());
        entity.setTitle(alert.title());
        entity.setMessage(alert.message());
        entity.setContactName(alert.contactName());
        entity.setContactPhone(alert.contactPhone());
        entity.setUrgency(alert.urgency());
        return entity;
    }
}
