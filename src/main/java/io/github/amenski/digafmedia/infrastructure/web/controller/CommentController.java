package io.github.amenski.digafmedia.infrastructure.web.controller;

import io.github.amenski.digafmedia.domain.Comment;
import io.github.amenski.digafmedia.domain.Comments;
import io.github.amenski.digafmedia.domain.DomainValidationException;
import io.github.amenski.digafmedia.domain.PagedResult;
import io.github.amenski.digafmedia.domain.ValidationGroups;
import io.github.amenski.digafmedia.infrastructure.web.mapper.CommentWebMapper;
import io.github.amenski.digafmedia.infrastructure.web.model.CommentResponse;
import io.github.amenski.digafmedia.infrastructure.web.model.CreateCommentRequest;
import io.github.amenski.digafmedia.infrastructure.web.model.PaginatedResponse;
import io.github.amenski.digafmedia.infrastructure.web.security.CurrentUserAdapter;
import io.github.amenski.digafmedia.infrastructure.web.util.PaginationUtils;
import io.github.amenski.digafmedia.usecase.CreateCommentUseCase;
import io.github.amenski.digafmedia.usecase.DeleteCommentUseCase;
import io.github.amenski.digafmedia.usecase.GetAllCommentsUseCase;
import io.github.amenski.digafmedia.usecase.GetCommentByIdUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.amenski.digafmedia.infrastructure.web.security.UserPrincipal;

@RestController
@RequestMapping("/api/v1/comments")
@Transactional(readOnly = true)
public class CommentController {

    private static final Logger log = LoggerFactory.getLogger(CommentController.class);

    private final GetAllCommentsUseCase getAllCommentsUseCase;
    private final GetCommentByIdUseCase getCommentByIdUseCase;
    private final CreateCommentUseCase createCommentUseCase;
    private final DeleteCommentUseCase deleteCommentUseCase;
    private final CommentWebMapper commentWebMapper;

    public CommentController(
            GetAllCommentsUseCase getAllCommentsUseCase,
            GetCommentByIdUseCase getCommentByIdUseCase,
            CreateCommentUseCase createCommentUseCase,
            DeleteCommentUseCase deleteCommentUseCase,
            CommentWebMapper commentWebMapper) {
        this.getAllCommentsUseCase = getAllCommentsUseCase;
        this.getCommentByIdUseCase = getCommentByIdUseCase;
        this.createCommentUseCase = createCommentUseCase;
        this.deleteCommentUseCase = deleteCommentUseCase;
        this.commentWebMapper = commentWebMapper;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<CommentResponse>> getAllComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            // Validate pagination parameters using centralized utility
            ResponseEntity<?> validationError = PaginationUtils.validatePaginationParameters(page, size);
            if (validationError != null) {
                return (ResponseEntity<PaginatedResponse<CommentResponse>>) validationError;
            }
            
            var pagedResult = getAllCommentsUseCase.invoke(page, size);
            
            List<CommentResponse> commentResponses = pagedResult.getContent().stream()
                    .map(commentWebMapper::toResponse)
                    .toList();
            
            PaginatedResponse<CommentResponse> response = new PaginatedResponse<>(
                    commentResponses,
                    pagedResult.getPage(),
                    pagedResult.getSize(),
                    pagedResult.getTotalElements(),
                    pagedResult.getTotalPages()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error getting all comments", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        try {
            Comment comment = getCommentByIdUseCase.invoke(id);
            return ResponseEntity.ok(comment);
        } catch (Exception e) {
            log.error("Error getting comment by id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'MODERATOR', 'ADMIN')")
    @Transactional
    public ResponseEntity<Comment> createComment(
            @RequestBody CommentRequest request,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            Comment comment = new Comment(
                    null,
                    request.name(),
                    request.email(),
                    request.content(),
                    null,
                    userPrincipal.getId()
            );
            Comment createdComment = createCommentUseCase.invoke(comment, new CurrentUserAdapter(userPrincipal));
            return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
        } catch (DomainValidationException e) {
            log.warn("Validation error creating comment: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error creating comment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('MODERATOR', 'ADMIN')")
    @Transactional
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        try {
            deleteCommentUseCase.invoke(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting comment with id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public record CommentRequest(String name, String email, String content) {
    }
}
