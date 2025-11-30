package io.github.amenski.digafmedia.infrastructure.persistence.entity;

import io.github.amenski.digafmedia.domain.afalgun.AfalgunStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "afalgun_post")
public class AfalgunPostEntity extends BaseEntity {

    @Column(name = "missing_person_name", nullable = false)
    private String missingPersonName;

    @Column(name = "age")
    private Integer age;

    @Column(name = "last_seen_location", length = 500)
    private String lastSeenLocation;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AfalgunStatus status = AfalgunStatus.ACTIVE;

    @Column(name = "created_by")
    private Long createdBy;

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

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
}
