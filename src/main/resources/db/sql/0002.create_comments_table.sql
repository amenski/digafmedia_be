-- liquibase formatted sql

-- changeset aman:create-comments-table
CREATE TABLE IF NOT EXISTS comment
(
    id          BIGINT AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL,
    content     TEXT         NOT NULL,
    created_at  DATETIME     NULL,
    modified_at DATETIME     NULL,
    PRIMARY KEY (id)
);

-- changeset aman:add-sample-comment
INSERT INTO comment (id, name, email, content, created_at, modified_at)
VALUES (1, 'John Doe', 'john@example.com', 'This is a sample comment.', NOW(), NOW());