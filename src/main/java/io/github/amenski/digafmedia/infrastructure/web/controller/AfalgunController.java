package io.github.amenski.digafmedia.infrastructure.web.controller;

import io.github.amenski.digafmedia.domain.ValidationGroups;
import io.github.amenski.digafmedia.infrastructure.web.mapper.AfalgunWebMapper;
import io.github.amenski.digafmedia.infrastructure.web.model.AfalgunPostResponse;
import io.github.amenski.digafmedia.infrastructure.web.model.AfalgunSearchRequest;
import io.github.amenski.digafmedia.infrastructure.web.model.CreateAfalgunPostRequest;
import io.github.amenski.digafmedia.infrastructure.web.model.PaginatedResponse;
import io.github.amenski.digafmedia.infrastructure.web.model.UpdateAfalgunPostRequest;
import io.github.amenski.digafmedia.infrastructure.web.security.CurrentUserAdapter;
import io.github.amenski.digafmedia.infrastructure.web.security.UserPrincipal;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/afalgun")
@Transactional(readOnly = true)
public class AfalgunController {

    private final GetAllAfalgunPostsUseCase getAllAfalgunPostsUseCase;
    private final GetAfalgunPostByIdUseCase getAfalgunPostByIdUseCase;
    private final CreateAfalgunPostUseCase createAfalgunPostUseCase;
    private final UpdateAfalgunPostStatusUseCase updateAfalgunPostStatusUseCase;
    private final DeleteAfalgunPostUseCase deleteAfalgunPostUseCase;
    private final AfalgunWebMapper afalgunWebMapper;

    public AfalgunController(
            GetAllAfalgunPostsUseCase getAllAfalgunPostsUseCase,
            GetAfalgunPostByIdUseCase getAfalgunPostByIdUseCase,
            CreateAfalgunPostUseCase createAfalgunPostUseCase,
            UpdateAfalgunPostStatusUseCase updateAfalgunPostStatusUseCase,
            DeleteAfalgunPostUseCase deleteAfalgunPostUseCase,
            AfalgunWebMapper afalgunWebMapper) {
        this.getAllAfalgunPostsUseCase = getAllAfalgunPostsUseCase;
        this.getAfalgunPostByIdUseCase = getAfalgunPostByIdUseCase;
        this.createAfalgunPostUseCase = createAfalgunPostUseCase;
        this.updateAfalgunPostStatusUseCase = updateAfalgunPostStatusUseCase;
        this.deleteAfalgunPostUseCase = deleteAfalgunPostUseCase;
        this.afalgunWebMapper = afalgunWebMapper;
    }

    @GetMapping
    @Operation(summary = "List afalgun posts", description = "Search and filter afalgun posts with pagination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Posts retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PaginatedResponse<AfalgunPostResponse>> getAfalgunPosts(
            @Validated({ValidationGroups.Search.class})
            AfalgunSearchRequest searchRequest) {
        
        var command = afalgunWebMapper.from(searchRequest);
        var searchResult = getAllAfalgunPostsUseCase.execute(command);

        var response = new PaginatedResponse<>(
            searchResult.getContent().stream()
                .map(afalgunWebMapper::toResponse)
                .toList(),
            searchResult.getPage(),
            searchResult.getSize(),
            searchResult.getTotalElements(),
            searchResult.getTotalPages()
        );
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get afalgun post by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Post retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Post not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AfalgunPostResponse> getAfalgunPostById(
            @Parameter(description = "Post ID") @PathVariable Long id) {
        
        return getAfalgunPostByIdUseCase.execute(id)
                .map(afalgunWebMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    @Operation(summary = "Create afalgun post")
    @PreAuthorize("isAuthenticated()")
    @Transactional
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Post created successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AfalgunPostResponse> createAfalgunPost(
            @Validated(ValidationGroups.Create.class) @RequestBody CreateAfalgunPostRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        
        var command = afalgunWebMapper.create(request);
        var savedAfalgun = createAfalgunPostUseCase.execute(command, new CurrentUserAdapter(userPrincipal));
        return ResponseEntity.status(HttpStatus.CREATED).body(AfalgunPostResponse.fromAfalgunPost(savedAfalgun));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update afalgun post status")
    @PreAuthorize("isAuthenticated()")
    @Transactional
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Post status updated successfully"),
        @ApiResponse(responseCode = "400", description = "Validation error"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Post not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AfalgunPostResponse> updateAfalgunPostStatus(
            @Parameter(description = "Post ID") @PathVariable Long id,
            @Validated(ValidationGroups.Update.class) @RequestBody UpdateAfalgunPostRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        
        var command = afalgunWebMapper.update(request);
        var updatedAfalgun = updateAfalgunPostStatusUseCase.execute(id, command, new CurrentUserAdapter(userPrincipal));
        return ResponseEntity.ok(AfalgunPostResponse.fromAfalgunPost(updatedAfalgun));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete afalgun post")
    @PreAuthorize("isAuthenticated()")
    @Transactional
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Post deleted successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "404", description = "Post not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteAfalgunPost(
            @Parameter(description = "Post ID") @PathVariable Long id,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        
        deleteAfalgunPostUseCase.execute(id, new CurrentUserAdapter(userPrincipal));
        return ResponseEntity.noContent().build();
    }
}
