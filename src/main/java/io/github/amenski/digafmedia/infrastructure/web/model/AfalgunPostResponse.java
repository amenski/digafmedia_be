package io.github.amenski.digafmedia.infrastructure.web.model;

import io.github.amenski.digafmedia.domain.afalgun.AfalgunPost;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.UUID;

@Schema(description = "Afalgun post response model")
public class AfalgunPostResponse {
    private Long id;
    private String missingPersonName;
    private Integer age;
    private String lastSeenLocation;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String description;
    private AfalgunStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public AfalgunPostResponse() {
    }

    // Static factory method for domain-to-web mapping
    public static AfalgunPostResponse fromAfalgunPost(AfalgunPost afalgunPost) {
        if (afalgunPost == null) return null;
        
        AfalgunPostResponse response = new AfalgunPostResponse();
        response.setId(afalgunPost.id());
        response.setMissingPersonName(afalgunPost.missingPersonName());
        response.setAge(afalgunPost.age());
        response.setLastSeenLocation(afalgunPost.lastSeenLocation());
        response.setContactName(afalgunPost.contactName());
        response.setContactPhone(afalgunPost.contactPhone());
        response.setContactEmail(afalgunPost.contactEmail());
        response.setDescription(afalgunPost.description());
        response.setStatus(afalgunPost.status());
        response.setCreatedAt(afalgunPost.createdAt());
        response.setUpdatedAt(afalgunPost.modifiedAt());
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

    public AfalgunStatus getStatus() {
        return status;
    }

    public void setStatus(AfalgunStatus status) {
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