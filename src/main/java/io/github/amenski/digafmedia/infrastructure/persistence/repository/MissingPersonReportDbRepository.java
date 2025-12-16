package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.missingperson.MissingPersonReport;
import io.github.amenski.digafmedia.domain.missingperson.ReportStatus;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.MissingPersonReportEntity;
import io.github.amenski.digafmedia.domain.repository.MissingPersonReportRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class MissingPersonReportDbRepository implements MissingPersonReportRepository {

    private final MissingPersonReportJpaRepository missingPersonReportJpaRepository;

    public MissingPersonReportDbRepository(MissingPersonReportJpaRepository missingPersonReportJpaRepository) {
        this.missingPersonReportJpaRepository = missingPersonReportJpaRepository;
    }

    @Override
    public List<MissingPersonReport> findAll() {
        return missingPersonReportJpaRepository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<MissingPersonReport> findByStatus(ReportStatus status) {
        return missingPersonReportJpaRepository.findByStatus(status).stream().map(this::toDomain).toList();
    }

    @Override
    public List<MissingPersonReport> findAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return missingPersonReportJpaRepository.findAll(pageable).stream().map(this::toDomain).toList();
    }

    @Override
    public List<MissingPersonReport> findByStatusPaginated(ReportStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return missingPersonReportJpaRepository.findByStatus(status, pageable).stream().map(this::toDomain).toList();
    }

    @Override
    public long count() {
        return missingPersonReportJpaRepository.count();
    }

    @Override
    public long countByStatus(ReportStatus status) {
        return missingPersonReportJpaRepository.countByStatus(status);
    }

    @Override
    public Optional<MissingPersonReport> findById(Long id) {
        return missingPersonReportJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public boolean existsById(Long id) {
        return missingPersonReportJpaRepository.existsById(id);
    }

    @Override
    public MissingPersonReport save(MissingPersonReport report) {
        MissingPersonReportEntity entity = toEntity(report);
        if (entity.getId() == null) {
            entity.setCreatedAt(OffsetDateTime.now());
        }
        entity.setModifiedAt(OffsetDateTime.now());
        return toDomain(missingPersonReportJpaRepository.save(entity));
    }

    @Override
    public List<MissingPersonReport> searchByQuery(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return missingPersonReportJpaRepository.searchByQuery(query, pageable).stream()
                .map(this::toDomain).toList();
    }

    @Override
    public long countByQuery(String query) {
        return missingPersonReportJpaRepository.countSearchByQuery(query);
    }

    @Override
    public List<MissingPersonReport> searchByLocation(String location, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return missingPersonReportJpaRepository.findByLastSeenLocationContainingIgnoreCase(location, pageable).stream()
                .map(this::toDomain).toList();
    }

    @Override
    public long countByLocation(String location) {
        return missingPersonReportJpaRepository.countByLastSeenLocationContainingIgnoreCase(location);
    }

    @Override
    public List<MissingPersonReport> searchByQueryAndStatus(String query, ReportStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return missingPersonReportJpaRepository.searchByQueryAndStatus(query, status, pageable).stream()
                .map(this::toDomain).toList();
    }

    @Override
    public long countByQueryAndStatus(String query, ReportStatus status) {
        return missingPersonReportJpaRepository.countSearchByQueryAndStatus(query, status);
    }

    @Override
    public List<MissingPersonReport> searchByQueryAndLocation(String query, String location, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return missingPersonReportJpaRepository.searchByQueryAndLocation(query, location, pageable).stream()
                .map(this::toDomain).toList();
    }

    @Override
    public long countByQueryAndLocation(String query, String location) {
        return missingPersonReportJpaRepository.countSearchByQueryAndLocation(query, location);
    }

    @Override
    public List<MissingPersonReport> searchByStatusAndLocation(ReportStatus status, String location, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return missingPersonReportJpaRepository.findByStatusAndLastSeenLocationContainingIgnoreCase(status, location, pageable).stream()
                .map(this::toDomain).toList();
    }

    @Override
    public long countByStatusAndLocation(ReportStatus status, String location) {
        return missingPersonReportJpaRepository.countByStatusAndLastSeenLocationContainingIgnoreCase(status, location);
    }

    @Override
    public List<MissingPersonReport> searchByQueryStatusAndLocation(String query, ReportStatus status, String location, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return missingPersonReportJpaRepository.searchByQueryStatusAndLocation(query, status, location, pageable).stream()
                .map(this::toDomain).toList();
    }

    @Override
    public long countByQueryStatusAndLocation(String query, ReportStatus status, String location) {
        return missingPersonReportJpaRepository.countSearchByQueryStatusAndLocation(query, status, location);
    }

    @Override
    public void deleteById(Long id) {
        missingPersonReportJpaRepository.deleteById(id);
    }

    private MissingPersonReport toDomain(MissingPersonReportEntity entity) {
        return new MissingPersonReport(
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
                entity.getModifiedAt(),
                entity.getCreatedBy()
        );
    }

    private MissingPersonReportEntity toEntity(MissingPersonReport report) {
        MissingPersonReportEntity entity = new MissingPersonReportEntity();
        entity.setId(report.id());
        entity.setMissingPersonName(report.missingPersonName());
        entity.setAge(report.age());
        entity.setLastSeenLocation(report.lastSeenLocation());
        entity.setContactName(report.contactName());
        entity.setContactPhone(report.contactPhone());
        entity.setContactEmail(report.contactEmail());
        entity.setDescription(report.description());
        entity.setStatus(report.status());
        return entity;
    }
}
