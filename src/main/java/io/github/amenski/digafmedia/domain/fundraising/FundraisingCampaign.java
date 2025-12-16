package io.github.amenski.digafmedia.domain.fundraising;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

public record FundraisingCampaign(
    Long id,
    String title,
    String description,
    BigDecimal goalAmount,
    BigDecimal currentAmount,
    String bankName,
    String accountNumber,
    String accountHolder,
    String contactName,
    String contactPhone,
    String contactEmail,
    CampaignStatus status,
    OffsetDateTime createdAt,
    OffsetDateTime modifiedAt,
    Long createdBy
) {
    /**
     * Factory method to create a new FundraisingCampaign from a command.
     * Encapsulates the business rule that new campaigns should have a creation timestamp.
     */
    public static FundraisingCampaign fromCommand(CreateFundraisingCampaignCommand command, Long createdBy) {
        Objects.requireNonNull(command, "Command cannot be null");
        
        return new FundraisingCampaign(
                null,
                command.title(),
                command.description(),
                command.goalAmount(),
                BigDecimal.ZERO, // Starting amount is zero
                command.bankName(),
                command.accountNumber(),
                command.accountHolder(),
                command.contactName(),
                command.contactPhone(),
                command.contactEmail(),
                command.status(),
                OffsetDateTime.now(),
                null,
                createdBy
        );
    }

    /**
     * Domain method to update the amount raised for a campaign.
     * Encapsulates the business rule that amount updates should update the modifiedAt timestamp.
     */
    public FundraisingCampaign updateAmount(BigDecimal newAmount) {
        Objects.requireNonNull(newAmount, "Amount cannot be null");
        
        if (this.currentAmount().compareTo(newAmount) == 0) {
            return this; // No change needed
        }
        
        return new FundraisingCampaign(
                this.id(),
                this.title(),
                this.description(),
                this.goalAmount(),
                newAmount,
                this.bankName(),
                this.accountNumber(),
                this.accountHolder(),
                this.contactName(),
                this.contactPhone(),
                this.contactEmail(),
                this.status(),
                this.createdAt(),
                OffsetDateTime.now(),
                this.createdBy()
        );
    }

    /**
     * Domain method to update the status of an existing campaign.
     * Encapsulates the business rule that status updates should update the modifiedAt timestamp.
     */
    public FundraisingCampaign updateStatus(CampaignStatus newStatus) {
        Objects.requireNonNull(newStatus, "Status cannot be null");
        
        if (this.status() == newStatus) {
            return this; // No change needed
        }
        
        return new FundraisingCampaign(
                this.id(),
                this.title(),
                this.description(),
                this.goalAmount(),
                this.currentAmount(),
                this.bankName(),
                this.accountNumber(),
                this.accountHolder(),
                this.contactName(),
                this.contactPhone(),
                this.contactEmail(),
                newStatus,
                this.createdAt(),
                OffsetDateTime.now(),
                this.createdBy()
        );
    }

    /**
     * Business rule: A campaign is considered active if its status is ACTIVE
     */
    public boolean isActive() {
        return this.status() == CampaignStatus.ACTIVE;
    }

    /**
     * Business rule: A campaign is considered funded if its current amount meets or exceeds the goal
     */
    public boolean isFunded() {
        return this.currentAmount().compareTo(this.goalAmount()) >= 0;
    }

    /**
     * Business rule: Check if campaign is approaching funding goal
     */
    public boolean isNearFundingGoal() {
        if (this.goalAmount() == null || this.goalAmount().compareTo(BigDecimal.ZERO) == 0) {
            return false;
        }
        BigDecimal percentage = this.currentAmount().multiply(BigDecimal.valueOf(100))
                .divide(this.goalAmount(), 2, BigDecimal.ROUND_HALF_UP);
        return percentage.compareTo(BigDecimal.valueOf(80)) >= 0;
    }

    /**
     * Business rule: Validate if the campaign has required information
     */
    public boolean hasValidInformation() {
        return title() != null && !title().trim().isEmpty() &&
               description() != null && !description().trim().isEmpty() &&
               goalAmount() != null && goalAmount().compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Business rule: Get a summary of the campaign for display purposes
     */
    public String getSummary() {
        return String.format("%s - Goal: %s - Raised: %s",
            title(),
            goalAmount() != null ? goalAmount().toString() : "N/A",
            currentAmount() != null ? currentAmount().toString() : "N/A");
    }
}
