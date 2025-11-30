package io.github.amenski.digafmedia.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "free_service")
public class FreeServiceEntity extends BaseEntity {

    @Column(name = "service_name", nullable = false)
    private String serviceName;

    @Column(name = "provider_name", nullable = false)
    private String providerName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "location", length = 500)
    private String location;

    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "category", length = 100)
    private String category;

    @Column(name = "hours_of_operation")
    private String hoursOfOperation;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "created_by")
    private Long createdBy;

    // Getters and setters
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHoursOfOperation() {
        return hoursOfOperation;
    }

    public void setHoursOfOperation(String hoursOfOperation) {
        this.hoursOfOperation = hoursOfOperation;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
}
