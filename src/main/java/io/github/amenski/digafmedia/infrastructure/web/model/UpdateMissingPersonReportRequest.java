package io.github.amenski.digafmedia.infrastructure.web.model;

import io.github.amenski.digafmedia.domain.ValidationGroups;
import io.github.amenski.digafmedia.domain.missingperson.ReportStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Schema(description = "Request to update a missing person report")
public class UpdateMissingPersonReportRequest {

    @Size(min = 2, max = 100, message = "Missing person name must be between 2 and 100 characters", groups = ValidationGroups.Update.class)
    private String missingPersonName;

    @PositiveOrZero(message = "Age must be positive or zero", groups = ValidationGroups.Update.class)
    private Integer age;

    @Size(min = 5, max = 200, message = "Last seen location must be between 5 and 200 characters", groups = ValidationGroups.Update.class)
    private String lastSeenLocation;

    @Size(min = 2, max = 100, message = "Contact name must be between 2 and 100 characters", groups = ValidationGroups.Update.class)
    private String contactName;

    @Size(min = 5, max = 20, message = "Contact phone must be between 5 and 20 characters", groups = ValidationGroups.Update.class)
    private String contactPhone;

    @Email(message = "Contact email must be valid", groups = ValidationGroups.Update.class)
    @Size(max = 100, message = "Contact email must be less than 100 characters", groups = ValidationGroups.Update.class)
    private String contactEmail;

    @Size(min = 10, max = 2000, message = "Description must be between 10 and 2000 characters", groups = ValidationGroups.Update.class)
    private String description;

    private ReportStatus status;

    public UpdateMissingPersonReportRequest() {
    }

    // Getters and setters
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
}