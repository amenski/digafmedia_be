-- liquibase formatted sql

-- changeset aman:create-tables
CREATE TABLE IF NOT EXISTS product
(
    id          BIGINT AUTO_INCREMENT,
    route_name  VARCHAR(255) NOT NULL,
    name        VARCHAR(255) NOT NULL,
    created_at  DATETIME     NULL,
    modified_at DATETIME     NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS item
(
    id           BIGINT AUTO_INCREMENT,
    title        VARCHAR(255) NOT NULL,
    contact      VARCHAR(255) NOT NULL,
    description  TEXT         NOT NULL,
    published_on DATETIME     NOT NULL,
    product_id   BIGINT       NOT NULL,
    created_at   DATETIME     NULL,
    modified_at  DATETIME     NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);

CREATE TABLE IF NOT EXISTS image
(
    id            BIGINT AUTO_INCREMENT,
    file_path     VARCHAR(255) NOT NULL,
    alt_text      VARCHAR(255) NULL,
    display_order INT DEFAULT 0,
    product_id    BIGINT       NULL,
    item_id       BIGINT       NULL,
    created_at    DATETIME     NULL,
    modified_at   DATETIME     NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES item (id) ON DELETE CASCADE,
    CHECK ((product_id IS NULL AND item_id IS NOT NULL) OR
           (product_id IS NOT NULL AND item_id IS NULL))
);

-- Indexes for better performance
CREATE INDEX idx_image_product ON image (product_id);
CREATE INDEX idx_image_item ON image (item_id);


-- changeset aman:add-sample-data
INSERT INTO product (id, route_name, name, created_at, modified_at)
VALUES (1, 'afalgun', 'afalgun', NULL, NULL);
INSERT INTO item (id, title, contact, description, published_on, product_id, created_at, modified_at)
VALUES (1, 'test', 'test', 'test', '2025-04-12 10:31:38', 1, NULL, NULL);
INSERT INTO image (id, file_path, alt_text, display_order, product_id, item_id, created_at, modified_at)
VALUES (1, 'https://edu.google.com/coursebuilder/courses/pswg/1.2/assets/notes/Lesson4.1/images/image05.png?mmfb=1441065600', 'test', 0, 1, Null, NULL, NULL);
INSERT INTO image (id, file_path, alt_text, display_order, product_id, item_id, created_at, modified_at)
VALUES (2, 'https://static.semrush.com/blog/uploads/media/5b/f9/5bf9fd188f1ed082797a82c91df2345b/c779ec5bf5399f124185a68fb314c3cd/original.jpeg', 'test', 0, NULL, 1, NULL, NULL);
INSERT INTO image (id, file_path, alt_text, display_order, product_id, item_id, created_at, modified_at)
VALUES (3, 'https://www.mekina.net/_next/image?url=https%3A%2F%2Fmekina.s3.eu-west-1.amazonaws.com%2Fcars%2Fprivate%2Ff8fdc442-543d-476b-b264-b06c1c01eb3f%2Ffc7aac20-03b8-479f-a781-a52fa10465e5%2F1000016675_424f40f2a9.jpg&w=3840&q=75', 'test', 0, NULL, 1, NULL, NULL);
