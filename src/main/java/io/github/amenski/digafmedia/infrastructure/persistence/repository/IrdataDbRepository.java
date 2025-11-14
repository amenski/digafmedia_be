package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.irdata.IrdataPost;
import io.github.amenski.digafmedia.domain.irdata.IrdataStatus;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.IrdataPostEntity;
import io.github.amenski.digafmedia.domain.repository.IrdataRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class IrdataDbRepository implements IrdataRepository {

    private final IrdataJpaRepository irdataJpaRepository;

    public IrdataDbRepository(IrdataJpaRepository irdataJpaRepository) {
        this.irdataJpaRepository = irdataJpaRepository;
    }

    @Override
    public List<IrdataPost> findRecent(int limit) {
        return irdataJpaRepository.findRecent(limit).stream().map(this::toDomain).toList();
    }

    @Override
    public List<IrdataPost> findRecentByStatus(IrdataStatus status, int limit) {
        return irdataJpaRepository.findRecentByStatus(status, limit).stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<IrdataPost> findById(Long id) {
        return irdataJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public boolean existsById(Long id) {
        return irdataJpaRepository.existsById(id);
    }

    @Override
    public IrdataPost save(IrdataPost post) {
        IrdataPostEntity entity = toEntity(post);
        if (entity.getId() == null) {
            entity.setCreatedAt(OffsetDateTime.now());
        }
        entity.setModifiedAt(OffsetDateTime.now());
        return toDomain(irdataJpaRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        irdataJpaRepository.deleteById(id);
    }

    @Override
    public List<IrdataPost> findAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return irdataJpaRepository.findAll(pageable).stream().map(this::toDomain).toList();
    }

    @Override
    public List<IrdataPost> findByStatusPaginated(IrdataStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return irdataJpaRepository.findByStatus(status, pageable).stream().map(this::toDomain).toList();
    }

    @Override
    public long count() {
        return irdataJpaRepository.count();
    }

    @Override
    public long countByStatus(IrdataStatus status) {
        return irdataJpaRepository.countByStatus(status);
    }

    private IrdataPost toDomain(IrdataPostEntity entity) {
        return new IrdataPost(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getGoalAmount(),
                entity.getCurrentAmount(),
                entity.getBankName(),
                entity.getAccountNumber(),
                entity.getAccountHolder(),
                entity.getContactName(),
                entity.getContactPhone(),
                entity.getContactEmail(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }

    private IrdataPostEntity toEntity(IrdataPost post) {
        IrdataPostEntity entity = new IrdataPostEntity();
        entity.setId(post.id());
        entity.setTitle(post.title());
        entity.setDescription(post.description());
        entity.setGoalAmount(post.goalAmount());
        entity.setCurrentAmount(post.currentAmount());
        entity.setBankName(post.bankName());
        entity.setAccountNumber(post.accountNumber());
        entity.setAccountHolder(post.accountHolder());
        entity.setContactName(post.contactName());
        entity.setContactPhone(post.contactPhone());
        entity.setContactEmail(post.contactEmail());
        entity.setStatus(post.status());
        return entity;
    }
}
