package io.github.amenski.digafmedia.infrastructure.web.model;

import io.github.amenski.digafmedia.domain.Comment;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;

@Schema(description = "Comment response model")
public class CommentResponse {
    private Long id;
    private String name;
    private String email;
    private String content;
    private OffsetDateTime createdAt;

    public CommentResponse() {
    }

    public static CommentResponse fromComment(Comment comment) {
        if (comment == null) return null;
        
        CommentResponse response = new CommentResponse();
        response.setId(comment.id());
        response.setName(comment.name());
        response.setEmail(comment.email());
        response.setContent(comment.content());
        response.setCreatedAt(comment.createdAt());
        return response;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
