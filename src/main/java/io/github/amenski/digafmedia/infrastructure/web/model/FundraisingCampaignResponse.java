package io.github.amenski.digafmedia.infrastructure.web.model;

import io.github.amenski.digafmedia.domain.fundraising.FundraisingCampaign;
import io.github.amenski.digafmedia.domain.fundraising.CampaignStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Schema(description = "Fundraising campaign response model")
public class FundraisingCampaignResponse {
    private Long id;
    private String title;
    private String description;
    private BigDecimal goalAmount;
    private BigDecimal currentAmount;
    private String bankName;
    private String accountNumber;
    private String accountHolder;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private CampaignStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime modifiedAt;

    public FundraisingCampaignResponse() {
    }

    // Static factory method for domain-to-web mapping
    public static FundraisingCampaignResponse fromFundraisingCampaign(FundraisingCampaign fundraisingCampaign) {
        if (fundraisingCampaign == null) return null;

        FundraisingCampaignResponse response = new FundraisingCampaignResponse();
        response.setId(fundraisingCampaign.id());
        response.setTitle(fundraisingCampaign.title());
        response.setDescription(fundraisingCampaign.description());
        response.setGoalAmount(fundraisingCampaign.goalAmount());
        response.setCurrentAmount(fundraisingCampaign.currentAmount());
        response.setBankName(fundraisingCampaign.bankName());
        response.setAccountNumber(fundraisingCampaign.accountNumber());
        response.setAccountHolder(fundraisingCampaign.accountHolder());
        response.setContactName(fundraisingCampaign.contactName());
        response.setContactPhone(fundraisingCampaign.contactPhone());
        response.setContactEmail(fundraisingCampaign.contactEmail());
        response.setStatus(fundraisingCampaign.status());
        response.setCreatedAt(fundraisingCampaign.createdAt());
        response.setModifiedAt(fundraisingCampaign.modifiedAt());
        return response;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public CampaignStatus getStatus() {
        return status;
    }

    public void setStatus(CampaignStatus status) {
        this.status = status;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(OffsetDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
