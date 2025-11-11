package io.github.amenski.digafmedia.infrastructure.web.controller;

import io.github.amenski.digafmedia.domain.DomainValidationException;
import io.github.amenski.digafmedia.domain.tikoma.TikomaAlert;
import io.github.amenski.digafmedia.domain.tikoma.TikomaAlerts;
import io.github.amenski.digafmedia.domain.tikoma.TikomaUrgency;
import io.github.amenski.digafmedia.infrastructure.web.util.PaginationUtils;
import io.github.amenski.digafmedia.usecase.tikoma.CreateTikomaAlertUseCase;
import io.github.amenski.digafmedia.usecase.tikoma.DeleteTikomaAlertUseCase;
import io.github.amenski.digafmedia.usecase.tikoma.GetAllTikomaAlertsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/tikoma")
public class TikomaController {

    private static final Logger log = LoggerFactory.getLogger(TikomaController.class);

    private final GetAllTikomaAlertsUseCase getAllTikomaAlertsUseCase;
    private final CreateTikomaAlertUseCase createTikomaAlertUseCase;
    private final DeleteTikomaAlertUseCase deleteTikomaAlertUseCase;

    public TikomaController(
            GetAllTikomaAlertsUseCase getAllTikomaAlertsUseCase,
            CreateTikomaAlertUseCase createTikomaAlertUseCase,
            DeleteTikomaAlertUseCase deleteTikomaAlertUseCase) {
        this.getAllTikomaAlertsUseCase = getAllTikomaAlertsUseCase;
        this.createTikomaAlertUseCase = createTikomaAlertUseCase;
        this.deleteTikomaAlertUseCase = deleteTikomaAlertUseCase;
    }

    @GetMapping
    @Operation(summary = "List tikoma alerts", description = "Get all tikoma alerts with optional urgency filtering and pagination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Alerts retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PaginatedResponse<TikomaAlert>> getAllAlerts(
            @Parameter(description = "Filter by urgency") @RequestParam(required = false) TikomaUrgency urgency,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        try {
            // Validate pagination parameters using centralized utility
            ResponseEntity<?> validationError = PaginationUtils.validatePaginationParameters(page, size);
            if (validationError != null) {
                return (ResponseEntity<PaginatedResponse<TikomaAlert>>) validationError;
            }
            
            var pagedResult = getAllTikomaAlertsUseCase.invoke(urgency, page, size);
            
            PaginatedResponse<TikomaAlert> response = new PaginatedResponse<>(
                    pagedResult.getContent(),
                    pagedResult.getPage(),
                    pagedResult.getSize(),
                    pagedResult.getTotalElements(),
                    pagedResult.getTotalPages()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting all tikoma alerts", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @Operation(summary = "Create tikoma alert")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Alert created successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<TikomaAlert> createAlert(@RequestBody TikomaAlertRequest request) {
        try {
            TikomaAlert alert = new TikomaAlert(
                    null,
                    request.title(),
                    request.message(),
                    request.contactName(),
                    request.contactPhone(),
                    request.urgency(),
                    null,
                    null
            );
            TikomaAlert createdAlert = createTikomaAlertUseCase.invoke(alert);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAlert);
        } catch (DomainValidationException | IllegalArgumentException e) {
            log.warn("Validation error creating tikoma alert: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error creating tikoma alert", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete tikoma alert")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Alert deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Alert not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteAlert(
            @Parameter(description = "Alert ID") @PathVariable Long id) {
        try {
            deleteTikomaAlertUseCase.invoke(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting tikoma alert with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public record TikomaAlertRequest(
            String title,
            String message,
            TikomaUrgency urgency,
            String contactName,
            String contactPhone
    ) {}
}
