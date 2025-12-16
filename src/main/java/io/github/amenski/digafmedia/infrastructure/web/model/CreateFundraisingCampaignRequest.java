package io.github.amenski.digafmedia.infrastructure.web.model;

import io.github.amenski.digafmedia.domain.fundraising.CampaignStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Request model for creating a fundraising campaign")
public class CreateFundraisingCampaignRequest {

    @Schema(description = "Campaign title", example = "Help us build a school in rural Ethiopia")
    private String title;

    @Schema(description = "Campaign description", example = "We are raising funds to build a new school with classrooms, library, and playground.")
    private String description;

    @Schema(description = "Goal amount in ETB", example = "50000.00")
    private BigDecimal goalAmount;

    @Schema(description = "Bank name", example = "CBE")
    private String bankName;

    @Schema(description = "Account number", example = "123456789")
    private String accountNumber;

    @Schema(description = "Account holder name", example = "Ethiopia Development Fund")
    private String accountHolder;

    @Schema(description = "Contact name", example = "John Doe")
    private String contactName;

    @Schema(description = "Contact phone number", example = "+251912345678")
    private String contactPhone;

    @Schema(description = "Contact email address", example = "john@example.com")
    private String contactEmail;

    @Schema(description = "Campaign status", example = "ACTIVE")
    private CampaignStatus status;

    // Constructors
    public CreateFundraisingCampaignRequest() {
    }

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
}
