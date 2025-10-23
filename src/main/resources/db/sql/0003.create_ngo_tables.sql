--liquibase formatted sql

--changeset amenski:create_afalgun_post_table
CREATE TABLE afalgun_post (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    missing_person_name VARCHAR(255) NOT NULL,
    age INT,
    last_seen_location VARCHAR(500),
    contact_name VARCHAR(255),
    contact_phone VARCHAR(20),
    contact_email VARCHAR(255),
    description TEXT,
    status ENUM('ACTIVE', 'FOUND', 'CLOSED') DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

--changeset amenski:create_irdata_post_table
CREATE TABLE irdata_post (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    goal_amount DECIMAL(15,2),
    current_amount DECIMAL(15,2) DEFAULT 0,
    bank_name VARCHAR(255),
    account_number VARCHAR(100),
    account_holder VARCHAR(255),
    contact_name VARCHAR(255),
    contact_phone VARCHAR(20),
    contact_email VARCHAR(255),
    status ENUM('ACTIVE', 'FUNDED', 'CLOSED') DEFAULT 'ACTIVE',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

--changeset amenski:create_tikoma_alert_table
CREATE TABLE tikoma_alert (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    contact_name VARCHAR(255),
    contact_phone VARCHAR(20),
    urgency ENUM('LOW', 'MEDIUM', 'HIGH') DEFAULT 'MEDIUM',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

--changeset amenski:create_free_service_table
CREATE TABLE free_service (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    service_name VARCHAR(255) NOT NULL,
    provider_name VARCHAR(255) NOT NULL,
    description TEXT,
    location VARCHAR(500),
    contact_phone VARCHAR(20),
    contact_email VARCHAR(255),
    category VARCHAR(100),
    hours_of_operation VARCHAR(255),
    is_active BOOLEAN DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

--changeset amenski:create_with_you_testimonial_table
CREATE TABLE with_you_testimonial (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    story TEXT NOT NULL,
    author_name VARCHAR(255),
    author_location VARCHAR(255),
    is_approved BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

--changeset amenski:create_post_image_table
CREATE TABLE post_image (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    post_type ENUM('AFALGUN', 'IRDATA', 'TIKOMA', 'FREE_SERVICE', 'WITH_YOU') NOT NULL,
    post_id BIGINT NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    alt_text VARCHAR(255),
    display_order INT DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_post_type_id (post_type, post_id)
);

--changeset amenski:create_indexes_for_ngo_tables
CREATE INDEX idx_afalgun_status ON afalgun_post(status);
CREATE INDEX idx_afalgun_created_at ON afalgun_post(created_at DESC);

CREATE INDEX idx_irdata_status ON irdata_post(status);
CREATE INDEX idx_irdata_created_at ON irdata_post(created_at DESC);

CREATE INDEX idx_tikoma_urgency ON tikoma_alert(urgency);
CREATE INDEX idx_tikoma_created_at ON tikoma_alert(created_at DESC);

CREATE INDEX idx_free_service_active ON free_service(is_active);
CREATE INDEX idx_free_service_category ON free_service(category);
CREATE INDEX idx_free_service_location ON free_service(location(255));

CREATE INDEX idx_with_you_approved ON with_you_testimonial(is_approved);
CREATE INDEX idx_with_you_created_at ON with_you_testimonial(created_at DESC);
