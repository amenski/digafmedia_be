package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.freeservice.FreeService;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.FreeServiceEntity;
import io.github.amenski.digafmedia.domain.repository.FreeServiceRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class FreeServiceDbRepository implements FreeServiceRepository {

    private final FreeServiceJpaRepository freeServiceJpaRepository;

    public FreeServiceDbRepository(FreeServiceJpaRepository freeServiceJpaRepository) {
        this.freeServiceJpaRepository = freeServiceJpaRepository;
    }

    @Override
    public List<FreeService> findAll() {
        return freeServiceJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<FreeService> findByActive(Boolean isActive) {
        return freeServiceJpaRepository.findByIsActive(isActive).stream().map(this::toDomain).toList();
    }

    @Override
    public List<FreeService> findByCategory(String category) {
        return freeServiceJpaRepository.findByCategory(category).stream().map(this::toDomain).toList();
    }

    @Override
    public List<FreeService> findByLocationContaining(String location) {
        return freeServiceJpaRepository.findByLocationContaining(location).stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<FreeService> findById(Long id) {
        return freeServiceJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public FreeService save(FreeService service) {
        FreeServiceEntity entity = toEntity(service);
        if (entity.getId() == null) {
            entity.setCreatedAt(OffsetDateTime.now());
        }
        entity.setModifiedAt(OffsetDateTime.now());
        return toDomain(freeServiceJpaRepository.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        freeServiceJpaRepository.deleteById(id);
    }

    private FreeService toDomain(FreeServiceEntity entity) {
        return new FreeService(
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
                entity.getModifiedAt()
        );
    }

    private FreeServiceEntity toEntity(FreeService service) {
        FreeServiceEntity entity = new FreeServiceEntity();
        entity.setId(service.id());
        entity.setServiceName(service.serviceName());
        entity.setProviderName(service.providerName());
        entity.setDescription(service.description());
        entity.setLocation(service.location());
        entity.setContactPhone(service.contactPhone());
        entity.setContactEmail(service.contactEmail());
        entity.setCategory(service.category());
        entity.setHoursOfOperation(service.hoursOfOperation());
        entity.setIsActive(service.isActive());
        return entity;
    }
}
