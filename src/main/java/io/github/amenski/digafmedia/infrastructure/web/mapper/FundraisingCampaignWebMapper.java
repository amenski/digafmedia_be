package io.github.amenski.digafmedia.infrastructure.web.mapper;

import io.github.amenski.digafmedia.domain.fundraising.FundraisingCampaign;
import io.github.amenski.digafmedia.domain.fundraising.CreateFundraisingCampaignCommand;
import io.github.amenski.digafmedia.domain.fundraising.SearchFundraisingCampaignsCommand;
import io.github.amenski.digafmedia.infrastructure.web.model.CreateFundraisingCampaignRequest;
import io.github.amenski.digafmedia.infrastructure.web.model.FundraisingCampaignResponse;
import io.github.amenski.digafmedia.infrastructure.web.model.FundraisingCampaignSearchRequest;
import io.github.amenski.digafmedia.infrastructure.web.model.PaginatedResponse;
import io.github.amenski.digafmedia.domain.PagedResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FundraisingCampaignWebMapper {

    public CreateFundraisingCampaignCommand create(CreateFundraisingCampaignRequest request) {
        return new CreateFundraisingCampaignCommand(
                request.getTitle(),
                request.getDescription(),
                request.getGoalAmount(),
                request.getBankName(),
                request.getAccountNumber(),
                request.getAccountHolder(),
                request.getContactName(),
                request.getContactPhone(),
                request.getContactEmail(),
                request.getStatus()
        );
    }

    public FundraisingCampaignResponse toResponse(FundraisingCampaign fundraisingCampaign) {
        return FundraisingCampaignResponse.fromFundraisingCampaign(fundraisingCampaign);
    }

    public PaginatedResponse<FundraisingCampaignResponse> toPaginatedResponse(PagedResult<FundraisingCampaign> pagedResult) {
        List<FundraisingCampaignResponse> responses = pagedResult.getContent().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return new PaginatedResponse<>(
                responses,
                pagedResult.getPage(),
                pagedResult.getSize(),
                pagedResult.getTotalElements(),
                pagedResult.getTotalPages()
        );
    }

    public SearchFundraisingCampaignsCommand from(FundraisingCampaignSearchRequest searchRequest) {
        return new SearchFundraisingCampaignsCommand(
                searchRequest.getQuery(),
                searchRequest.getStatus(),
                searchRequest.getPage(),
                searchRequest.getSize(),
                searchRequest.getSortBy(),
                searchRequest.getSortDirection()
        );
    }
}
