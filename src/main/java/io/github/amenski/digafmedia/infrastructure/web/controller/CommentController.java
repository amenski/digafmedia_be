package io.github.amenski.digafmedia.infrastructure.web.controller;

import io.github.amenski.digafmedia.domain.Comment;
import io.github.amenski.digafmedia.domain.Comments;
import io.github.amenski.digafmedia.infrastructure.web.util.PaginationUtils;
import io.github.amenski.digafmedia.usecase.CreateCommentUseCase;
import io.github.amenski.digafmedia.usecase.DeleteCommentUseCase;
import io.github.amenski.digafmedia.usecase.GetAllCommentsUseCase;
import io.github.amenski.digafmedia.usecase.GetCommentByIdUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/comments")
public class CommentController {

    private static final Logger log = LoggerFactory.getLogger(CommentController.class);

    private final GetAllCommentsUseCase getAllCommentsUseCase;
    private final GetCommentByIdUseCase getCommentByIdUseCase;
    private final CreateCommentUseCase createCommentUseCase;
    private final DeleteCommentUseCase deleteCommentUseCase;

    public CommentController(
            GetAllCommentsUseCase getAllCommentsUseCase,
            GetCommentByIdUseCase getCommentByIdUseCase,
            CreateCommentUseCase createCommentUseCase,
            DeleteCommentUseCase deleteCommentUseCase) {
        this.getAllCommentsUseCase = getAllCommentsUseCase;
        this.getCommentByIdUseCase = getCommentByIdUseCase;
        this.createCommentUseCase = createCommentUseCase;
        this.deleteCommentUseCase = deleteCommentUseCase;
    }

    @GetMapping
    public ResponseEntity<Comments> getAllComments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        try {
            // Validate pagination parameters using centralized utility
            ResponseEntity<?> validationError = PaginationUtils.validatePaginationParameters(page, size);
            if (validationError != null) {
                return (ResponseEntity<Comments>) validationError;
            }
            
            Comments comments = getAllCommentsUseCase.invoke(page, size);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            log.error("Error getting all comments", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        try {
            return getCommentByIdUseCase.invoke(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Error getting comment by id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentRequest request) {
        try {
            Comment comment = new Comment(
                    null,
                    request.name(),
                    request.email(),
                    request.content(),
                    null
            );
            Comment createdComment = createCommentUseCase.invoke(comment);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
        } catch (io.github.amenski.digafmedia.domain.DomainValidationException e) {
            log.warn("Validation error creating comment: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error creating comment", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
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
