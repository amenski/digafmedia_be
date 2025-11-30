--liquibase formatted sql

--changeset amenski:add_deleted_at_to_all_tables
ALTER TABLE users ADD COLUMN deleted_at DATETIME NULL;
ALTER TABLE afalgun_post ADD COLUMN deleted_at DATETIME NULL;
ALTER TABLE irdata_post ADD COLUMN deleted_at DATETIME NULL;
ALTER TABLE tikoma_alert ADD COLUMN deleted_at DATETIME NULL;
ALTER TABLE free_service ADD COLUMN deleted_at DATETIME NULL;
ALTER TABLE with_you_testimonial ADD COLUMN deleted_at DATETIME NULL;
ALTER TABLE post_image ADD COLUMN deleted_at DATETIME NULL;
ALTER TABLE comment ADD COLUMN deleted_at DATETIME NULL;
