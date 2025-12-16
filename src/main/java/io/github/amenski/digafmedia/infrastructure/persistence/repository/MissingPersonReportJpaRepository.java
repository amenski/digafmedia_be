package io.github.amenski.digafmedia.infrastructure.persistence.repository;

import io.github.amenski.digafmedia.domain.missingperson.ReportStatus;
import io.github.amenski.digafmedia.infrastructure.persistence.entity.MissingPersonReportEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MissingPersonReportJpaRepository extends JpaRepository<MissingPersonReportEntity, Long>, JpaSpecificationExecutor<MissingPersonReportEntity> {
    
    List<MissingPersonReportEntity> findByStatus(ReportStatus status);
    
    List<MissingPersonReportEntity> findByStatus(ReportStatus status, Pageable pageable);
    
    long countByStatus(ReportStatus status);
    
    // Full-text search across relevant fields (case-insensitive, partial match)
    @Query("SELECT r FROM MissingPersonReportEntity r WHERE " +
           "(LOWER(r.missingPersonName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.contactName) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<MissingPersonReportEntity> searchByQuery(@Param("query") String query, Pageable pageable);
    
    @Query("SELECT COUNT(r) FROM MissingPersonReportEntity r WHERE " +
           "(LOWER(r.missingPersonName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.contactName) LIKE LOWER(CONCAT('%', :query, '%')))")
    long countSearchByQuery(@Param("query") String query);
    
    // Search by location (partial match, case-insensitive)
    Page<MissingPersonReportEntity> findByLastSeenLocationContainingIgnoreCase(String location, Pageable pageable);
    
    long countByLastSeenLocationContainingIgnoreCase(String location);
    
    // Combined search: query + status
    @Query("SELECT r FROM MissingPersonReportEntity r WHERE " +
           "(LOWER(r.missingPersonName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.contactName) LIKE LOWER(CONCAT('%', :query, '%'))) " +
           "AND r.status = :status")
    Page<MissingPersonReportEntity> searchByQueryAndStatus(
        @Param("query") String query,
        @Param("status") ReportStatus status,
        Pageable pageable);
    
    @Query("SELECT COUNT(r) FROM MissingPersonReportEntity r WHERE " +
           "(LOWER(r.missingPersonName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.contactName) LIKE LOWER(CONCAT('%', :query, '%'))) " +
           "AND r.status = :status")
    long countSearchByQueryAndStatus(@Param("query") String query, @Param("status") ReportStatus status);
    
    // Combined search: query + location
    @Query("SELECT r FROM MissingPersonReportEntity r WHERE " +
           "(LOWER(r.missingPersonName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.contactName) LIKE LOWER(CONCAT('%', :query, '%'))) " +
           "AND LOWER(r.lastSeenLocation) LIKE LOWER(CONCAT('%', :location, '%'))")
    Page<MissingPersonReportEntity> searchByQueryAndLocation(
        @Param("query") String query,
        @Param("location") String location,
        Pageable pageable);
    
    @Query("SELECT COUNT(r) FROM MissingPersonReportEntity r WHERE " +
           "(LOWER(r.missingPersonName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.contactName) LIKE LOWER(CONCAT('%', :query, '%'))) " +
           "AND LOWER(r.lastSeenLocation) LIKE LOWER(CONCAT('%', :location, '%'))")
    long countSearchByQueryAndLocation(@Param("query") String query, @Param("location") String location);
    
    // Combined search: status + location
    Page<MissingPersonReportEntity> findByStatusAndLastSeenLocationContainingIgnoreCase(
        ReportStatus status,
        String location,
        Pageable pageable);
    
    long countByStatusAndLastSeenLocationContainingIgnoreCase(ReportStatus status, String location);
    
    // Combined search: query + status + location
    @Query("SELECT r FROM MissingPersonReportEntity r WHERE " +
           "(LOWER(r.missingPersonName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.contactName) LIKE LOWER(CONCAT('%', :query, '%'))) " +
           "AND r.status = :status " +
           "AND LOWER(r.lastSeenLocation) LIKE LOWER(CONCAT('%', :location, '%'))")
    Page<MissingPersonReportEntity> searchByQueryStatusAndLocation(
        @Param("query") String query,
        @Param("status") ReportStatus status,
        @Param("location") String location,
        Pageable pageable);
    
    @Query("SELECT COUNT(r) FROM MissingPersonReportEntity r WHERE " +
           "(LOWER(r.missingPersonName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.description) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(r.contactName) LIKE LOWER(CONCAT('%', :query, '%'))) " +
           "AND r.status = :status " +
           "AND LOWER(r.lastSeenLocation) LIKE LOWER(CONCAT('%', :location, '%'))")
    long countSearchByQueryStatusAndLocation(
        @Param("query") String query,
        @Param("status") ReportStatus status,
        @Param("location") String location);
}