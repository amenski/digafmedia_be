package io.github.amenski.digafmedia.domain.irdata;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record IrdataPost(
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
    IrdataStatus status,
    OffsetDateTime createdAt,
    OffsetDateTime modifiedAt
) {
    /**
     * Factory method to create an IrdataPost with defaults if missing.
     * Encapsulates business rules: new posts default to ACTIVE status and ZERO current amount.
     */
    public static IrdataPost withDefaults(IrdataPost post) {
        if (post.status() != null && post.currentAmount() != null) {
            return post;
        }
        return new IrdataPost(
                post.id(),
                post.title(),
                post.description(),
                post.goalAmount(),
                post.currentAmount() != null ? post.currentAmount() : BigDecimal.ZERO,
                post.bankName(),
                post.accountNumber(),
                post.accountHolder(),
                post.contactName(),
                post.contactPhone(),
                post.contactEmail(),
                post.status() != null ? post.status() : IrdataStatus.ACTIVE,
                post.createdAt(),
                post.modifiedAt()
        );
    }

    /**
     * Domain method to update status and/or current amount.
     * Encapsulates the business rule that updates should preserve existing values if new ones are null.
     */
    public IrdataPost update(IrdataStatus newStatus, BigDecimal newCurrentAmount) {
        return new IrdataPost(
                this.id(),
                this.title(),
                this.description(),
                this.goalAmount(),
                newCurrentAmount != null ? newCurrentAmount : this.currentAmount(),
                this.bankName(),
                this.accountNumber(),
                this.accountHolder(),
                this.contactName(),
                this.contactPhone(),
                this.contactEmail(),
                newStatus != null ? newStatus : this.status(),
                this.createdAt(),
                this.modifiedAt()
        );
    }
}
