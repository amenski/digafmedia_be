package io.github.amenski.digafmedia.infrastructure.web.model;

import io.github.amenski.digafmedia.domain.ValidationGroups;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

@Schema(description = "Request to create a new afalgun post")
public class CreateAfalgunPostRequest {
    
    @NotBlank(message = "Missing person name is required", groups = ValidationGroups.Create.class)
    @Size(min = 2, max = 100, message = "Missing person name must be between 2 and 100 characters", groups = ValidationGroups.Create.class)
    private String missingPersonName;

    @NotNull(message = "Age is required", groups = ValidationGroups.Create.class)
    @PositiveOrZero(message = "Age must be positive or zero", groups = ValidationGroups.Create.class)
    private Integer age;

    @NotBlank(message = "Last seen location is required", groups = ValidationGroups.Create.class)
    @Size(min = 5, max = 200, message = "Last seen location must be between 5 and 200 characters", groups = ValidationGroups.Create.class)
    private String lastSeenLocation;

    @NotBlank(message = "Contact name is required", groups = ValidationGroups.Create.class)
    @Size(min = 2, max = 100, message = "Contact name must be between 2 and 100 characters", groups = ValidationGroups.Create.class)
    private String contactName;

    @NotBlank(message = "Contact phone is required", groups = ValidationGroups.Create.class)
    @Size(min = 5, max = 20, message = "Contact phone must be between 5 and 20 characters", groups = ValidationGroups.Create.class)
    private String contactPhone;

    @Email(message = "Contact email must be valid", groups = ValidationGroups.Create.class)
    @Size(max = 100, message = "Contact email must be less than 100 characters", groups = ValidationGroups.Create.class)
    private String contactEmail;

    @NotBlank(message = "Description is required", groups = ValidationGroups.Create.class)
    @Size(min = 10, max = 2000, message = "Description must be between 10 and 2000 characters", groups = ValidationGroups.Create.class)
    private String description;

    private AfalgunStatus status;

    public CreateAfalgunPostRequest() {
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

    public AfalgunStatus getStatus() {
        return status;
    }

    public void setStatus(AfalgunStatus status) {
        this.status = status;
    }
}