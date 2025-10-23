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
) {}
