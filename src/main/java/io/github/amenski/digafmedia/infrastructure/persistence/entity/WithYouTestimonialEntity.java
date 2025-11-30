package io.github.amenski.digafmedia.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "with_you_testimonial")
public class WithYouTestimonialEntity extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "story", nullable = false, columnDefinition = "TEXT")
    private String story;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "author_location")
    private String authorLocation;

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved = false;

    @Column(name = "created_by")
    private Long createdBy;

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorLocation() {
        return authorLocation;
    }

    public void setAuthorLocation(String authorLocation) {
        this.authorLocation = authorLocation;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
}
