--liquibase formatted sql

--changeset amenski:create_accounts_table
CREATE TABLE accounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('USER', 'MODERATOR', 'ADMIN') NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME NULL
);

--changeset amenski:create_user_profiles_table
CREATE TABLE user_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    phone_number VARCHAR(20),
    location VARCHAR(500),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME NULL,
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE,
    UNIQUE KEY unique_account_profile (account_id)
);

--changeset amenski:update_foreign_keys_to_accounts
SET FOREIGN_KEY_CHECKS = 0;

ALTER TABLE afalgun_post
    CHANGE COLUMN created_by created_by BIGINT,
    ADD FOREIGN KEY (created_by) REFERENCES accounts(id);

ALTER TABLE irdata_post
    CHANGE COLUMN created_by created_by BIGINT,
    ADD FOREIGN KEY (created_by) REFERENCES accounts(id);

ALTER TABLE tikoma_alert
    CHANGE COLUMN created_by created_by BIGINT,
    ADD FOREIGN KEY (created_by) REFERENCES accounts(id);

ALTER TABLE free_service
    CHANGE COLUMN created_by created_by BIGINT,
    ADD FOREIGN KEY (created_by) REFERENCES accounts(id);

ALTER TABLE with_you_testimonial
    CHANGE COLUMN created_by created_by BIGINT,
    ADD FOREIGN KEY (created_by) REFERENCES accounts(id);

ALTER TABLE post_image
    CHANGE COLUMN created_by created_by BIGINT,
    ADD FOREIGN KEY (created_by) REFERENCES accounts(id);

ALTER TABLE comment
    CHANGE COLUMN created_by created_by BIGINT,
    ADD FOREIGN KEY (created_by) REFERENCES accounts(id);

SET FOREIGN_KEY_CHECKS = 1;

--changeset amenski:create_indexes_for_accounts_and_profiles
CREATE INDEX idx_accounts_username ON accounts(username);
CREATE INDEX idx_accounts_email ON accounts(email);
CREATE INDEX idx_accounts_active ON accounts(active);
CREATE INDEX idx_accounts_role ON accounts(role);

CREATE INDEX idx_user_profiles_account_id ON user_profiles(account_id);
CREATE INDEX idx_user_profiles_first_last_name ON user_profiles(first_name, last_name);
CREATE INDEX idx_user_profiles_location ON user_profiles(location(255));