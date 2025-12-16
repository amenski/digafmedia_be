package io.github.amenski.digafmedia.infrastructure.web.controller;

import io.github.amenski.digafmedia.domain.ValidationGroups;
import io.github.amenski.digafmedia.infrastructure.web.mapper.FundraisingCampaignWebMapper;
import io.github.amenski.digafmedia.infrastructure.web.model.FundraisingCampaignResponse;
import io.github.amenski.digafmedia.infrastructure.web.model.FundraisingCampaignSearchRequest;
import io.github.amenski.digafmedia.infrastructure.web.model.CreateFundraisingCampaignRequest;
import io.github.amenski.digafmedia.infrastructure.web.model.PaginatedResponse;
import io.github.amenski.digafmedia.infrastructure.web.security.CurrentUserAdapter;
import io.github.amenski.digafmedia.infrastructure.web.security.UserPrincipal;
import io.github.amenski.digafmedia.usecase.fundraising.CreateFundraisingCampaignUseCase;
import io.github.amenski.digafmedia.usecase.fundraising.DeleteFundraisingCampaignUseCase;
import io.github.amenski.digafmedia.usecase.fundraising.GetFundraisingCampaignByIdUseCase;
import io.github.amenski.digafmedia.usecase.fundraising.GetAllFundraisingCampaignsUseCase;
import io.github.amenski.digafmedia.usecase.fundraising.UpdateFundraisingCampaignAmountUseCase;
import io.github.amenski.digafmedia.usecase.fundraising.UpdateFundraisingCampaignStatusUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fundraising/campaigns")
@Transactional(readOnly = true)
public class FundraisingCampaignController {

    private final GetAllFundraisingCampaignsUseCase getAllFundraisingCampaignsUseCase;
    private final GetFundraisingCampaignByIdUseCase getFundraisingCampaignByIdUseCase;
    private final CreateFundraisingCampaignUseCase createFundraisingCampaignUseCase;
    private final UpdateFundraisingCampaignAmountUseCase updateFundraisingCampaignAmountUseCase;
    private final UpdateFundraisingCampaignStatusUseCase updateFundraisingCampaignStatusUseCase;
    private final DeleteFundraisingCampaignUseCase deleteFundraisingCampaignUseCase;
    private final FundraisingCampaignWebMapper fundraisingCampaignWebMapper;

    public FundraisingCampaignController(
            GetAllFundraisingCampaignsUseCase getAllFundraisingCampaignsUseCase,
            GetFundraisingCampaignByIdUseCase getFundraisingCampaignByIdUseCase,
            CreateFundraisingCampaignUseCase createFundraisingCampaignUseCase,
            UpdateFundraisingCampaignAmountUseCase updateFundraisingCampaignAmountUseCase,
            UpdateFundraisingCampaignStatusUseCase updateFundraisingCampaignStatusUseCase,
            DeleteFundraisingCampaignUseCase deleteFundraisingCampaignUseCase,
            FundraisingCampaignWebMapper fundraisingCampaignWebMapper) {
        this.getAllFundraisingCampaignsUseCase = getAllFundraisingCampaignsUseCase;
        this.getFundraisingCampaignByIdUseCase = getFundraisingCampaignByIdUseCase;
        this.createFundraisingCampaignUseCase = createFundraisingCampaignUseCase;
        this.updateFundraisingCampaignAmountUseCase = updateFundraisingCampaignAmountUseCase;
        this.updateFundraisingCampaignStatusUseCase = updateFundraisingCampaignStatusUseCase;
        this.deleteFundraisingCampaignUseCase = deleteFundraisingCampaignUseCase;
        this.fundraisingCampaignWebMapper = fundraisingCampaignWebMapper;
    }

    @GetMapping
    @Operation(summary = "List fundraising campaigns", description = "Search and filter fundraising campaigns with pagination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Campaigns retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PaginatedResponse<FundraisingCampaignResponse>> getFundraisingCampaigns(
            @Validated({ValidationGroups.Search.class})
            FundraisingCampaignSearchRequest searchRequest) {

        var command = fundraisingCampaignWebMapper.from(searchRequest);
        var searchResult = getAllFundraisingCampaignsUseCase.invoke(command);

        var response = new PaginatedResponse<>(
            searchResult.getContent().stream()
                .map(fundraisingCampaignWebMapper::toResponse)
                .toList(),
            searchResult.getPage(),
            searchResult.getSize(),
            searchResult.getTotalElements(),
            searchResult.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get fundraising campaign by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Campaign retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Campaign not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<FundraisingCampaignResponse> getFundraisingCampaignById(
            @Parameter(description = "Campaign ID") @PathVariable Long id) {

        return getFundraisingCampaignByIdUseCase.invoke(id)
                .map(fundraisingCampaignWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    @Operation(summary = "Create fundraising campaign")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @Transactional
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Campaign created successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<FundraisingCampaignResponse> createFundraisingCampaign(
            @Validated(ValidationGroups.Create.class) @RequestBody CreateFundraisingCampaignRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        var command = fundraisingCampaignWebMapper.create(request);
        var savedCampaign = createFundraisingCampaignUseCase.execute(command, new CurrentUserAdapter(userPrincipal));
        return ResponseEntity.status(HttpStatus.CREATED).body(FundraisingCampaignResponse.fromFundraisingCampaign(savedCampaign));
    }

    @PatchMapping("/{id}/amount")
    @Operation(summary = "Update fundraising campaign amount")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @Transactional
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Campaign amount updated successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Campaign not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<FundraisingCampaignResponse> updateFundraisingCampaignAmount(
            @Parameter(description = "Campaign ID") @PathVariable Long id,
            @RequestBody @Validated(ValidationGroups.Update.class) CreateFundraisingCampaignRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        var updatedCampaign = updateFundraisingCampaignAmountUseCase.invoke(
            id,
            request.getGoalAmount(),
            new CurrentUserAdapter(userPrincipal)
        );
        return ResponseEntity.ok(FundraisingCampaignResponse.fromFundraisingCampaign(updatedCampaign));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update fundraising campaign status")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @Transactional
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Campaign status updated successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Campaign not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<FundraisingCampaignResponse> updateFundraisingCampaignStatus(
            @Parameter(description = "Campaign ID") @PathVariable Long id,
            @RequestBody @Validated(ValidationGroups.Update.class) CreateFundraisingCampaignRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        var updatedCampaign = updateFundraisingCampaignStatusUseCase.invoke(
            id,
            request.getStatus(),
            new CurrentUserAdapter(userPrincipal)
        );
        return ResponseEntity.ok(FundraisingCampaignResponse.fromFundraisingCampaign(updatedCampaign));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete fundraising campaign")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Campaign deleted successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Campaign not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteFundraisingCampaign(
            @Parameter(description = "Campaign ID") @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        deleteFundraisingCampaignUseCase.invoke(id, new CurrentUserAdapter(userPrincipal));
        return ResponseEntity.noContent().build();
    }
}
