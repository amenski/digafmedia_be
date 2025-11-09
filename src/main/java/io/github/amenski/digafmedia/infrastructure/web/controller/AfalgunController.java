package io.github.amenski.digafmedia.infrastructure.web.controller;

import io.github.amenski.digafmedia.domain.afalgun.AfalgunPost;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunPosts;
import io.github.amenski.digafmedia.domain.afalgun.AfalgunStatus;
import io.github.amenski.digafmedia.usecase.afalgun.CreateAfalgunPostUseCase;
import io.github.amenski.digafmedia.usecase.afalgun.DeleteAfalgunPostUseCase;
import io.github.amenski.digafmedia.usecase.afalgun.GetAfalgunPostByIdUseCase;
import io.github.amenski.digafmedia.usecase.afalgun.GetAllAfalgunPostsUseCase;
import io.github.amenski.digafmedia.usecase.afalgun.UpdateAfalgunPostStatusUseCase;
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
@RequestMapping("v1/afalgun")
public class AfalgunController {

    private static final Logger log = LoggerFactory.getLogger(AfalgunController.class);

    private final GetAllAfalgunPostsUseCase getAllAfalgunPostsUseCase;
    private final GetAfalgunPostByIdUseCase getAfalgunPostByIdUseCase;
    private final CreateAfalgunPostUseCase createAfalgunPostUseCase;
    private final UpdateAfalgunPostStatusUseCase updateAfalgunPostStatusUseCase;
    private final DeleteAfalgunPostUseCase deleteAfalgunPostUseCase;

    public AfalgunController(
            GetAllAfalgunPostsUseCase getAllAfalgunPostsUseCase,
            GetAfalgunPostByIdUseCase getAfalgunPostByIdUseCase,
            CreateAfalgunPostUseCase createAfalgunPostUseCase,
            UpdateAfalgunPostStatusUseCase updateAfalgunPostStatusUseCase,
            DeleteAfalgunPostUseCase deleteAfalgunPostUseCase) {
        this.getAllAfalgunPostsUseCase = getAllAfalgunPostsUseCase;
        this.getAfalgunPostByIdUseCase = getAfalgunPostByIdUseCase;
        this.createAfalgunPostUseCase = createAfalgunPostUseCase;
        this.updateAfalgunPostStatusUseCase = updateAfalgunPostStatusUseCase;
        this.deleteAfalgunPostUseCase = deleteAfalgunPostUseCase;
    }

    @GetMapping
    @Operation(summary = "Get Afalgun posts", description = "Retrieve Afalgun posts with optional filtering and pagination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Posts retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid pagination parameters"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AfalgunPosts> getAllPosts(
            @Parameter(description = "Filter by post status") @RequestParam(required = false) AfalgunStatus status,
            @Parameter(description = "Page number (0-based)", example = "0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page", example = "20") @RequestParam(defaultValue = "20") int size) {
        try {
            // Validate pagination parameters
            if (page < 0) {
                return ResponseEntity.badRequest().body(null);
            }
            if (size <= 0) {
                return ResponseEntity.badRequest().body(null);
            }
            
            AfalgunPosts posts = getAllAfalgunPostsUseCase.invoke(status, page, size);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            log.error("Error getting all afalgun posts", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AfalgunPost> getPostById(@PathVariable Long id) {
        try {
            return getAfalgunPostByIdUseCase.invoke(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Error getting afalgun post by id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<AfalgunPost> createPost(@RequestBody AfalgunPostRequest request) {
        try {
            AfalgunPost post = new AfalgunPost(
                    null,
                    request.missingPersonName(),
                    request.age(),
                    request.lastSeenLocation(),
                    request.contactName(),
                    request.contactPhone(),
                    request.contactEmail(),
                    request.description(),
                    request.status(),
                    null,
                    null
            );
            AfalgunPost createdPost = createAfalgunPostUseCase.invoke(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        } catch (IllegalArgumentException e) {
            log.warn("Validation error creating afalgun post: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error creating afalgun post", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AfalgunPost> updatePostStatus(@PathVariable Long id, @RequestBody UpdateStatusRequest request) {
        try {
            AfalgunPost updated = updateAfalgunPostStatusUseCase.invoke(id, request.status());
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            log.warn("Validation error updating afalgun post status: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error updating afalgun post status for id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        try {
            deleteAfalgunPostUseCase.invoke(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting afalgun post with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public record AfalgunPostRequest(
            String missingPersonName,
            Integer age,
            String lastSeenLocation,
            String contactName,
            String contactPhone,
            String contactEmail,
            String description,
            AfalgunStatus status
    ) {}

    public record UpdateStatusRequest(AfalgunStatus status) {}
}
