package io.github.amenski.digafmedia.infrastructure.web.controller;

import io.github.amenski.digafmedia.domain.DomainValidationException;
import io.github.amenski.digafmedia.domain.irdata.IrdataPost;
import io.github.amenski.digafmedia.domain.irdata.IrdataPosts;
import io.github.amenski.digafmedia.domain.irdata.IrdataStatus;
import io.github.amenski.digafmedia.infrastructure.web.model.PaginatedResponse;
import io.github.amenski.digafmedia.infrastructure.web.util.PaginationUtils;
import io.github.amenski.digafmedia.usecase.irdata.CreateIrdataPostUseCase;
import io.github.amenski.digafmedia.usecase.irdata.DeleteIrdataPostUseCase;
import io.github.amenski.digafmedia.usecase.irdata.GetAllIrdataPostsUseCase;
import io.github.amenski.digafmedia.usecase.irdata.GetIrdataPostByIdUseCase;
import io.github.amenski.digafmedia.usecase.irdata.UpdateIrdataPostUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/irdata")
public class IrdataController {

    private static final Logger log = LoggerFactory.getLogger(IrdataController.class);

    private final GetAllIrdataPostsUseCase getAllIrdataPostsUseCase;
    private final GetIrdataPostByIdUseCase getIrdataPostByIdUseCase;
    private final CreateIrdataPostUseCase createIrdataPostUseCase;
    private final UpdateIrdataPostUseCase updateIrdataPostUseCase;
    private final DeleteIrdataPostUseCase deleteIrdataPostUseCase;

    public IrdataController(
            GetAllIrdataPostsUseCase getAllIrdataPostsUseCase,
            GetIrdataPostByIdUseCase getIrdataPostByIdUseCase,
            CreateIrdataPostUseCase createIrdataPostUseCase,
            UpdateIrdataPostUseCase updateIrdataPostUseCase,
            DeleteIrdataPostUseCase deleteIrdataPostUseCase) {
        this.getAllIrdataPostsUseCase = getAllIrdataPostsUseCase;
        this.getIrdataPostByIdUseCase = getIrdataPostByIdUseCase;
        this.createIrdataPostUseCase = createIrdataPostUseCase;
        this.updateIrdataPostUseCase = updateIrdataPostUseCase;
        this.deleteIrdataPostUseCase = deleteIrdataPostUseCase;
    }

    @GetMapping
    @Operation(summary = "List irdata posts", description = "Get all irdata posts with optional status filtering and pagination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Posts retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PaginatedResponse<IrdataPost>> getAllPosts(
            @Parameter(description = "Filter by status") @RequestParam(required = false) IrdataStatus status,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        try {
            // Validate pagination parameters using centralized utility
            ResponseEntity<?> validationError = PaginationUtils.validatePaginationParameters(page, size);
            if (validationError != null) {
                return (ResponseEntity<PaginatedResponse<IrdataPost>>) validationError;
            }
            
            var pagedResult = getAllIrdataPostsUseCase.invoke(status, page, size);
            
            PaginatedResponse<IrdataPost> response = new PaginatedResponse<>(
                    pagedResult.getContent(),
                    pagedResult.getPage(),
                    pagedResult.getSize(),
                    pagedResult.getTotalElements(),
                    pagedResult.getTotalPages()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting all irdata posts", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get irdata post by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Post retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Post not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<IrdataPost> getPostById(
            @Parameter(description = "Post ID") @PathVariable Long id) {
        try {
            IrdataPost post = getIrdataPostByIdUseCase.invoke(id);
            return ResponseEntity.ok(post);
        } catch (Exception e) {
            log.error("Error getting irdata post by id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @Transactional
    @Operation(summary = "Create irdata post")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Post created successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<IrdataPost> createPost(@RequestBody IrdataPostRequest request) {
        try {
            IrdataPost post = new IrdataPost(
                    null,
                    request.title(),
                    request.description(),
                    request.goalAmount(),
                    request.currentAmount(),
                    request.bankName(),
                    request.accountNumber(),
                    request.accountHolder(),
                    request.contactName(),
                    request.contactPhone(),
                    request.contactEmail(),
                    request.status(),
                    null,
                    null
            );
            IrdataPost createdPost = createIrdataPostUseCase.invoke(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        } catch (DomainValidationException | IllegalArgumentException e) {
            log.warn("Validation error creating irdata post: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error creating irdata post", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @Transactional
    @Operation(summary = "Update irdata post")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Post updated successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error"),
        @ApiResponse(responseCode = "404", description = "Post not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<IrdataPost> updatePost(
            @Parameter(description = "Post ID") @PathVariable Long id,
            @RequestBody UpdateIrdataPostRequest request) {
        try {
            IrdataPost updated = updateIrdataPostUseCase.invoke(id, request.status(), request.currentAmount());
            return ResponseEntity.ok(updated);
        } catch (DomainValidationException | IllegalArgumentException e) {
            log.warn("Validation error updating irdata post: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error updating irdata post for id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @Operation(summary = "Delete irdata post")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Post deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Post not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deletePost(
            @Parameter(description = "Post ID") @PathVariable Long id) {
        try {
            deleteIrdataPostUseCase.invoke(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting irdata post with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public record IrdataPostRequest(
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
            IrdataStatus status
    ) {}

    public record UpdateIrdataPostRequest(
            BigDecimal currentAmount,
            IrdataStatus status
    ) {}
}
