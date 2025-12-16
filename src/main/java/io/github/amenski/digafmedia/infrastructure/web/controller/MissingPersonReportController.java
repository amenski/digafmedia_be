package io.github.amenski.digafmedia.infrastructure.web.controller;

import io.github.amenski.digafmedia.domain.ValidationGroups;
import io.github.amenski.digafmedia.infrastructure.web.mapper.MissingPersonReportWebMapper;
import io.github.amenski.digafmedia.infrastructure.web.model.MissingPersonReportResponse;
import io.github.amenski.digafmedia.infrastructure.web.model.MissingPersonReportSearchRequest;
import io.github.amenski.digafmedia.infrastructure.web.model.CreateMissingPersonReportRequest;
import io.github.amenski.digafmedia.infrastructure.web.model.PaginatedResponse;
import io.github.amenski.digafmedia.infrastructure.web.model.UpdateMissingPersonReportRequest;
import io.github.amenski.digafmedia.infrastructure.web.security.CurrentUserAdapter;
import io.github.amenski.digafmedia.infrastructure.web.security.UserPrincipal;
import io.github.amenski.digafmedia.usecase.missingperson.CreateMissingPersonReportUseCase;
import io.github.amenski.digafmedia.usecase.missingperson.DeleteMissingPersonReportUseCase;
import io.github.amenski.digafmedia.usecase.missingperson.GetMissingPersonReportByIdUseCase;
import io.github.amenski.digafmedia.usecase.missingperson.GetAllMissingPersonReportsUseCase;
import io.github.amenski.digafmedia.usecase.missingperson.UpdateMissingPersonReportStatusUseCase;
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
@RequestMapping("/api/v1/missing-persons/reports")
@Transactional(readOnly = true)
public class MissingPersonReportController {

    private final GetAllMissingPersonReportsUseCase getAllMissingPersonReportsUseCase;
    private final GetMissingPersonReportByIdUseCase getMissingPersonReportByIdUseCase;
    private final CreateMissingPersonReportUseCase createMissingPersonReportUseCase;
    private final UpdateMissingPersonReportStatusUseCase updateMissingPersonReportStatusUseCase;
    private final DeleteMissingPersonReportUseCase deleteMissingPersonReportUseCase;
    private final MissingPersonReportWebMapper missingPersonReportWebMapper;

    public MissingPersonReportController(
            GetAllMissingPersonReportsUseCase getAllMissingPersonReportsUseCase,
            GetMissingPersonReportByIdUseCase getMissingPersonReportByIdUseCase,
            CreateMissingPersonReportUseCase createMissingPersonReportUseCase,
            UpdateMissingPersonReportStatusUseCase updateMissingPersonReportStatusUseCase,
            DeleteMissingPersonReportUseCase deleteMissingPersonReportUseCase,
            MissingPersonReportWebMapper missingPersonReportWebMapper) {
        this.getAllMissingPersonReportsUseCase = getAllMissingPersonReportsUseCase;
        this.getMissingPersonReportByIdUseCase = getMissingPersonReportByIdUseCase;
        this.createMissingPersonReportUseCase = createMissingPersonReportUseCase;
        this.updateMissingPersonReportStatusUseCase = updateMissingPersonReportStatusUseCase;
        this.deleteMissingPersonReportUseCase = deleteMissingPersonReportUseCase;
        this.missingPersonReportWebMapper = missingPersonReportWebMapper;
    }

    @GetMapping
    @Operation(summary = "List missing person reports", description = "Search and filter missing person reports with pagination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reports retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PaginatedResponse<MissingPersonReportResponse>> getMissingPersonReports(
            @Validated({ValidationGroups.Search.class})
            MissingPersonReportSearchRequest searchRequest) {

        var command = missingPersonReportWebMapper.from(searchRequest);
        var searchResult = getAllMissingPersonReportsUseCase.invoke(command);

        var response = new PaginatedResponse<>(
            searchResult.getContent().stream()
                .map(missingPersonReportWebMapper::toResponse)
                .toList(),
            searchResult.getPage(),
            searchResult.getSize(),
            searchResult.getTotalElements(),
            searchResult.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get missing person report by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Report retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Report not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<MissingPersonReportResponse> getMissingPersonReportById(
            @Parameter(description = "Report ID") @PathVariable Long id) {

        return getMissingPersonReportByIdUseCase.invoke(id)
                .map(missingPersonReportWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    @Operation(summary = "Create missing person report")
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @Transactional
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Report created successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<MissingPersonReportResponse> createMissingPersonReport(
            @Validated(ValidationGroups.Create.class) @RequestBody CreateMissingPersonReportRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        var command = missingPersonReportWebMapper.create(request);
        var savedReport = createMissingPersonReportUseCase.execute(command, new CurrentUserAdapter(userPrincipal));
        return ResponseEntity.status(HttpStatus.CREATED).body(MissingPersonReportResponse.fromMissingPersonReport(savedReport));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update missing person report status")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @Transactional
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Report status updated successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Report not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<MissingPersonReportResponse> updateMissingPersonReportStatus(
            @Parameter(description = "Report ID") @PathVariable Long id,
            @Validated(ValidationGroups.Update.class) @RequestBody UpdateMissingPersonReportRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        var updatedReport = updateMissingPersonReportStatusUseCase.invoke(
            id,
            request.getStatus(),
            new CurrentUserAdapter(userPrincipal)
        );
        return ResponseEntity.ok(MissingPersonReportResponse.fromMissingPersonReport(updatedReport));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete missing person report")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Report deleted successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Report not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteMissingPersonReport(
            @Parameter(description = "Report ID") @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {

        deleteMissingPersonReportUseCase.invoke(id, new CurrentUserAdapter(userPrincipal));
        return ResponseEntity.noContent().build();
    }
}