package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.repository.CommunityResourceRepository;
import io.github.amenski.digafmedia.domain.resource.CommunityResource;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.CommunityResourceEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class CommunityResourceDbRepository implements CommunityResourceRepository {

    private final CommunityResourceJpaRepository communityResourceJpaRepository;

    public CommunityResourceDbRepository(CommunityResourceJpaRepository communityResourceJpaRepository) {
        this.communityResourceJpaRepository = communityResourceJpaRepository;
    }

    @Override
    public List<CommunityResource> findAll() {
        return communityResourceJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<CommunityResource> findByActive(Boolean isActive) {
        return communityResourceJpaRepository.findByIsActive(isActive).stream().map(this::toDomain).toList();
    }

    @Override
    public List<CommunityResource> findByCategory(String category) {
        return communityResourceJpaRepository.findByCategory(category).stream().map(this::toDomain).toList();
    }

    @Override
    public List<CommunityResource> findByLocationContaining(String location) {
        return communityResourceJpaRepository.findByLocationContaining(location).stream().map(this::toDomain).toList();
    }

    @Override
    public List<CommunityResource> findAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return communityResourceJpaRepository.findAllByOrderByCreatedAtDesc(pageable).stream().map(this::toDomain).toList();
    }

    @Override
    public List<CommunityResource> findByActivePaginated(Boolean isActive, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return communityResourceJpaRepository.findByIsActive(isActive, pageable).stream().map(this::toDomain).toList();
    }

    @Override
    public long count() {
        return communityResourceJpaRepository.count();
    }

    @Override
    public long countByActive(Boolean isActive) {
        return communityResourceJpaRepository.findByIsActive(isActive).size();
    }

    @Override
    public Optional<CommunityResource> findById(Long id) {
        return communityResourceJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public boolean existsById(Long id) {
        return communityResourceJpaRepository.existsById(id);
    }

    @Override
    public CommunityResource save(CommunityResource resource) {
        CommunityResourceEntity entity = toEntity(resource);
        if (entity.getId() == null) {
            entity.setCreatedAt(OffsetDateTime.now());
        }
        entity.setModifiedAt(OffsetDateTime.now());
        return toDomain(communityResourceJpaRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        communityResourceJpaRepository.deleteById(id);
    }

    private CommunityResource toDomain(CommunityResourceEntity entity) {
        return new CommunityResource(
                entity.getId(),
                entity.getServiceName(),
                entity.getProviderName(),
                entity.getDescription(),
                entity.getLocation(),
                entity.getContactPhone(),
                entity.getContactEmail(),
                entity.getCategory(),
                entity.getHoursOfOperation(),
                entity.getIsActive(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getCreatedBy()
        );
    }

    private CommunityResourceEntity toEntity(CommunityResource resource) {
        CommunityResourceEntity entity = new CommunityResourceEntity();
        entity.setId(resource.id());
        entity.setServiceName(resource.serviceName());
        entity.setProviderName(resource.providerName());
        entity.setDescription(resource.description());
        entity.setLocation(resource.location());
        entity.setContactPhone(resource.contactPhone());
        entity.setContactEmail(resource.contactEmail());
        entity.setCategory(resource.category());
        entity.setHoursOfOperation(resource.hoursOfOperation());
        entity.setIsActive(resource.isActive());
        entity.setCreatedBy(resource.createdBy());
        return entity;
    }
}
