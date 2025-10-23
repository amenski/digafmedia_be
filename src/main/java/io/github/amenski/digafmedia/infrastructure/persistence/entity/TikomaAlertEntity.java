package io.github.amenski.digafmedia.infrastructure.persistence.entity;

import io.github.amenski.digafmedia.domain.tikoma.TikomaUrgency;
import jakarta.persistence.*;

@Entity
@Table(name = "tikoma_alert")
public class TikomaAlertEntity extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    @Enumerated(EnumType.STRING)
    @Column(name = "urgency", nullable = false)
    private TikomaUrgency urgency = TikomaUrgency.MEDIUM;

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public TikomaUrgency getUrgency() {
        return urgency;
    }

    public void setUrgency(TikomaUrgency urgency) {
        this.urgency = urgency;
    }
}
