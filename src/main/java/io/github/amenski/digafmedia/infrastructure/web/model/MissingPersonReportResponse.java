package io.github.amenski.digafmedia.infrastructure.web.model;

import io.github.amenski.digafmedia.domain.missingperson.MissingPersonReport;
import io.github.amenski.digafmedia.domain.missingperson.ReportStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

@Schema(description = "Missing person report response model")
public class MissingPersonReportResponse {
    private Long id;
    private String missingPersonName;
    private Integer age;
    private String lastSeenLocation;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String description;
    private ReportStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public MissingPersonReportResponse() {
    }

    // Static factory method for domain-to-web mapping
    public static MissingPersonReportResponse fromMissingPersonReport(MissingPersonReport missingPersonReport) {
        if (missingPersonReport == null) return null;

        MissingPersonReportResponse response = new MissingPersonReportResponse();
        response.setId(missingPersonReport.id());
        response.setMissingPersonName(missingPersonReport.missingPersonName());
        response.setAge(missingPersonReport.age());
        response.setLastSeenLocation(missingPersonReport.lastSeenLocation());
        response.setContactName(missingPersonReport.contactName());
        response.setContactPhone(missingPersonReport.contactPhone());
        response.setContactEmail(missingPersonReport.contactEmail());
        response.setDescription(missingPersonReport.description());
        response.setStatus(missingPersonReport.status());
        response.setCreatedAt(missingPersonReport.createdAt());
        response.setUpdatedAt(missingPersonReport.modifiedAt());
        return response;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMissingPersonName() {
        return missingPersonName;
    }

    public void setMissingPersonName(String missingPersonName) {
        this.missingPersonName = missingPersonName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getLastSeenLocation() {
        return lastSeenLocation;
    }

    public void setLastSeenLocation(String lastSeenLocation) {
        this.lastSeenLocation = lastSeenLocation;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}