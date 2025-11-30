package io.github.amenski.digafmedia.infrastructure.persistence.entity;

import io.github.amenski.digafmedia.domain.irdata.IrdataStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "irdata_post")
public class IrdataPostEntity extends BaseEntity {

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "goal_amount", precision = 15, scale = 2)
    private BigDecimal goalAmount;

    @Column(name = "current_amount", precision = 15, scale = 2)
    private BigDecimal currentAmount = BigDecimal.ZERO;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "account_number", length = 100)
    private String accountNumber;

    @Column(name = "account_holder")
    private String accountHolder;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    @Column(name = "contact_email")
    private String contactEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private IrdataStatus status = IrdataStatus.ACTIVE;

    @Column(name = "created_by")
    private Long createdBy;

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(BigDecimal goalAmount) {
        this.goalAmount = goalAmount;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
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

    public IrdataStatus getStatus() {
        return status;
    }

    public void setStatus(IrdataStatus status) {
        this.status = status;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
}
