package io.github.amenski.digafmedia.domain.fundraising;

import java.math.BigDecimal;

public record CreateFundraisingCampaignCommand(
    String title,
    String description,
    BigDecimal goalAmount,
    String bankName,
    String accountNumber,
    String accountHolder,
    String contactName,
    String contactPhone,
    String contactEmail,
    CampaignStatus status
) {
    // Validation could be added here if needed
}
