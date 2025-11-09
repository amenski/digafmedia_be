package io.github.amenski.digafmedia.infrastructure.web.model;

import io.github.amenski.digafmedia.domain.ValidationGroups;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request to create a new comment")
public class CreateCommentRequest {

    @NotBlank(message = "Name is required", groups = ValidationGroups.Create.class)
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters", groups = ValidationGroups.Create.class)
    private String name;

    @NotBlank(message = "Email is required", groups = ValidationGroups.Create.class)
    @Email(message = "Email should be valid", groups = ValidationGroups.Create.class)
    private String email;

    @NotBlank(message = "Content is required", groups = ValidationGroups.Create.class)
    @Size(min = 10, max = 1000, message = "Content must be between 10 and 1000 characters", groups = ValidationGroups.Create.class)
    private String content;

    public CreateCommentRequest() {
    }

    public CreateCommentRequest(String name, String email, String content) {
        this.name = name;
        this.email = email;
        this.content = content;
    }

    // Getters and setters
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
}
