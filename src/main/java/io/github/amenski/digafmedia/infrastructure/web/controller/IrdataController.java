package io.github.amenski.digafmedia.infrastructure.web.controller;

import io.github.amenski.digafmedia.domain.irdata.IrdataPost;
import io.github.amenski.digafmedia.domain.irdata.IrdataPosts;
import io.github.amenski.digafmedia.domain.irdata.IrdataStatus;
import io.github.amenski.digafmedia.usecase.irdata.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("v1/irdata")
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
    public ResponseEntity<IrdataPosts> getAllPosts(@RequestParam(required = false) IrdataStatus status) {
        try {
            IrdataPosts posts = getAllIrdataPostsUseCase.invoke(status);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            log.error("Error getting all irdata posts", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<IrdataPost> getPostById(@PathVariable Long id) {
        try {
            return getIrdataPostByIdUseCase.invoke(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("Error getting irdata post by id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
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
        } catch (IllegalArgumentException e) {
            log.warn("Validation error creating irdata post: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error creating irdata post", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<IrdataPost> updatePost(@PathVariable Long id, @RequestBody UpdateIrdataPostRequest request) {
        try {
            IrdataPost updated = updateIrdataPostUseCase.invoke(id, request.status(), request.currentAmount());
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            log.warn("Validation error updating irdata post: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Error updating irdata post for id: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
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
