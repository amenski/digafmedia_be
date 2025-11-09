package io.github.amenski.digafmedia.infrastructure.web.mapper;

import io.github.amenski.digafmedia.domain.afalgun.AfalgunPost;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunStatus;
import io.github.amenski.digafmedia.domain.afalgun.CreateAfalgunPostCommand;
import io.github.amenski.digafmedia.domain.afalgun.UpdateAfalgunPostCommand;
import io.github.amenski.digafmedia.domain.afalgun.SearchAfalgunPostsCommand;
import io.github.amenski.digafmedia.infrastructure.web.model.CreateAfalgunPostRequest;
import io.github.amenski.digafmedia.infrastructure.web.model.UpdateAfalgunPostRequest;
import io.github.amenski.digafmedia.infrastructure.web.model.AfalgunSearchRequest;
import io.github.amenski.digafmedia.infrastructure.web.model.AfalgunPostResponse;
import io.github.amenski.digafmedia.infrastructure.web.model.PaginatedResponse;
import io.github.amenski.digafmedia.domain.PagedResult;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AfalgunWebMapper {

    public CreateAfalgunPostCommand create(CreateAfalgunPostRequest request) {
        return new CreateAfalgunPostCommand(
                request.getMissingPersonName(),
                request.getAge(),
                request.getLastSeenLocation(),
                request.getContactName(),
                request.getContactPhone(),
                request.getContactEmail(),
                request.getDescription(),
                request.getStatus()
        );
    }

    public UpdateAfalgunPostCommand update(UpdateAfalgunPostRequest request) {
        return new UpdateAfalgunPostCommand(
                request.getStatus()
        );
    }

    public AfalgunPostResponse toResponse(AfalgunPost afalgunPost) {
        return AfalgunPostResponse.fromAfalgunPost(afalgunPost);
    }

    public PaginatedResponse<AfalgunPostResponse> toPaginatedResponse(PagedResult<AfalgunPost> pagedResult) {
        List<AfalgunPostResponse> responses = pagedResult.getContent().stream()
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

    public SearchAfalgunPostsCommand from(AfalgunSearchRequest searchRequest) {
        return new SearchAfalgunPostsCommand(
                searchRequest.getQuery(),
                searchRequest.getStatus(),
                searchRequest.getLocation(),
                searchRequest.getPage(),
                searchRequest.getSize(),
                searchRequest.getSortBy(),
                searchRequest.getSortDirection()
        );
    }

}
