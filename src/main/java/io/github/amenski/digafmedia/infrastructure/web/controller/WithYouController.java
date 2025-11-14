package io.github.amenski.digafmedia.infrastructure.web.controller;

import io.github.amenski.digafmedia.domain.DomainValidationException;
import io.github.amenski.digafmedia.domain.withyou.WithYouTestimonial;
import io.github.amenski.digafmedia.domain.withyou.WithYouTestimonials;
import io.github.amenski.digafmedia.infrastructure.web.model.PaginatedResponse;
import io.github.amenski.digafmedia.infrastructure.web.security.CurrentUserAdapter;
import io.github.amenski.digafmedia.infrastructure.web.security.UserPrincipal;
import io.github.amenski.digafmedia.infrastructure.web.util.PaginationUtils;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import io.github.amenski.digafmedia.usecase.withyou.ApproveWithYouTestimonialUseCase;
import io.github.amenski.digafmedia.usecase.withyou.CreateWithYouTestimonialUseCase;
import io.github.amenski.digafmedia.usecase.withyou.DeleteWithYouTestimonialUseCase;
import io.github.amenski.digafmedia.usecase.withyou.GetAllWithYouTestimonialsUseCase;
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

@RestController
@RequestMapping("/api/v1/with-you")
public class WithYouController {

    private static final Logger log = LoggerFactory.getLogger(WithYouController.class);

    private final GetAllWithYouTestimonialsUseCase getAllWithYouTestimonialsUseCase;
    private final CreateWithYouTestimonialUseCase createWithYouTestimonialUseCase;
    private final ApproveWithYouTestimonialUseCase approveWithYouTestimonialUseCase;
    private final DeleteWithYouTestimonialUseCase deleteWithYouTestimonialUseCase;

    public WithYouController(
            GetAllWithYouTestimonialsUseCase getAllWithYouTestimonialsUseCase,
            CreateWithYouTestimonialUseCase createWithYouTestimonialUseCase,
            ApproveWithYouTestimonialUseCase approveWithYouTestimonialUseCase,
            DeleteWithYouTestimonialUseCase deleteWithYouTestimonialUseCase) {
        this.getAllWithYouTestimonialsUseCase = getAllWithYouTestimonialsUseCase;
        this.createWithYouTestimonialUseCase = createWithYouTestimonialUseCase;
        this.approveWithYouTestimonialUseCase = approveWithYouTestimonialUseCase;
        this.deleteWithYouTestimonialUseCase = deleteWithYouTestimonialUseCase;
    }

    @GetMapping
    @Operation(summary = "List with-you testimonials", description = "Get all with-you testimonials with optional approval filtering and pagination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Testimonials retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PaginatedResponse<WithYouTestimonial>> getAllTestimonials(
            @Parameter(description = "Filter by approval status") @RequestParam(required = false) Boolean isApproved,
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "20") int size) {
        try {
            ResponseEntity<?> validationError = PaginationUtils.validatePaginationParameters(page, size);
            if (validationError != null) {
                return (ResponseEntity<PaginatedResponse<WithYouTestimonial>>) validationError;
            }
            
            var pagedResult = getAllWithYouTestimonialsUseCase.invoke(isApproved, page, size);
            
            PaginatedResponse<WithYouTestimonial> response = new PaginatedResponse<>(
                    pagedResult.getContent(),
                    pagedResult.getPage(),
                    pagedResult.getSize(),
                    pagedResult.getTotalElements(),
                    pagedResult.getTotalPages()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting all with-you testimonials", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @Transactional
    @Operation(summary = "Create with-you testimonial")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Testimonial created successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<WithYouTestimonial> createTestimonial(
            @RequestBody WithYouTestimonialRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            WithYouTestimonial testimonial = new WithYouTestimonial(
                    null,
                    request.title(),
                    request.story(),
                    request.authorName(),
                    request.authorLocation(),
                    request.isApproved(),
                    null,
                    null,
                    userPrincipal.getId()
            );
            WithYouTestimonial createdTestimonial = createWithYouTestimonialUseCase.invoke(testimonial, new CurrentUserAdapter(userPrincipal));
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTestimonial);
        } catch (DomainValidationException | IllegalArgumentException e) {
            log.warn("Validation error creating with-you testimonial: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error creating with-you testimonial", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @Transactional
    @Operation(summary = "Approve with-you testimonial")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Testimonial approved successfully"),
        @ApiResponse(responseCode = "404", description = "Testimonial not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<WithYouTestimonial> approveTestimonial(
            @Parameter(description = "Testimonial ID") @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            WithYouTestimonial approved = approveWithYouTestimonialUseCase.invoke(id, new CurrentUserAdapter(userPrincipal));
            return ResponseEntity.ok(approved);
        } catch (IllegalArgumentException e) {
            log.warn("Testimonial not found with id: {}", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error approving with-you testimonial with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @Operation(summary = "Delete with-you testimonial")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Testimonial deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Testimonial not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteTestimonial(
            @Parameter(description = "Testimonial ID") @PathVariable Long id) {
        try {
            deleteWithYouTestimonialUseCase.invoke(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting with-you testimonial with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public record WithYouTestimonialRequest(
            String title,
            String story,
            String authorName,
            String authorLocation,
            Boolean isApproved
    ) {}
}
