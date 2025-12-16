package io.github.amenski.digafmedia.domain.repository;

import io.github.amenski.digafmedia.domain.missingperson.MissingPersonReport;
import io.github.amenski.digafmedia.domain.missingperson.ReportStatus;

import java.util.List;
import java.util.Optional;

public interface MissingPersonReportRepository {

    List<MissingPersonReport> findAll();

    List<MissingPersonReport> findByStatus(ReportStatus status);

    List<MissingPersonReport> findAllPaginated(int page, int size);

    List<MissingPersonReport> findByStatusPaginated(ReportStatus status, int page, int size);

    long count();

    long countByStatus(ReportStatus status);

    // Search methods with pagination
    List<MissingPersonReport> searchByQuery(String query, int page, int size);
    long countByQuery(String query);

    List<MissingPersonReport> searchByLocation(String location, int page, int size);
    long countByLocation(String location);

    List<MissingPersonReport> searchByQueryAndStatus(String query, ReportStatus status, int page, int size);
    long countByQueryAndStatus(String query, ReportStatus status);

    List<MissingPersonReport> searchByQueryAndLocation(String query, String location, int page, int size);
    long countByQueryAndLocation(String query, String location);

    List<MissingPersonReport> searchByStatusAndLocation(ReportStatus status, String location, int page, int size);
    long countByStatusAndLocation(ReportStatus status, String location);

    List<MissingPersonReport> searchByQueryStatusAndLocation(String query, ReportStatus status, String location, int page, int size);
    long countByQueryStatusAndLocation(String query, ReportStatus status, String location);

    Optional<MissingPersonReport> findById(Long id);

    boolean existsById(Long id);

    MissingPersonReport save(MissingPersonReport report);

    void deleteById(Long id);
}