package io.github.amenski.digafmedia.domain.fundraising;

public record SearchFundraisingCampaignsCommand(
    String query,
    CampaignStatus status,
    Integer page,
    Integer size,
    String sortBy,
    String sortDirection
) {
}
