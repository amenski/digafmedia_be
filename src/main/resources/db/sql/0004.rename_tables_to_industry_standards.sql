--liquibase formatted sql

--changeset amenski:rename_tables_to_industry_standards
-- Rename tables from Amharic terms to industry-standard English names

-- Rename afalgun_post to missing_person_report
ALTER TABLE afalgun_post RENAME TO missing_person_report;

-- Rename irdata_post to fundraising_campaign
ALTER TABLE irdata_post RENAME TO fundraising_campaign;

-- Rename tikoma_alert to community_alert
ALTER TABLE tikoma_alert RENAME TO community_alert;

-- Rename free_service to community_resource
ALTER TABLE free_service RENAME TO community_resource;

-- Rename with_you_testimonial to impact_story
ALTER TABLE with_you_testimonial RENAME TO impact_story;

-- Update post_image.post_type enum values and data
-- First, we need to change the ENUM type to include new values and remove old ones.
-- Since MySQL doesn't support removing ENUM values directly, we'll create a new column and copy the data.

-- Step 1: Add a new temporary column with the new ENUM definition
ALTER TABLE post_image 
ADD COLUMN post_type_new ENUM('MISSING_PERSON_REPORT', 'FUNDRAISING_CAMPAIGN', 'COMMUNITY_ALERT', 'COMMUNITY_RESOURCE', 'IMPACT_STORY') AFTER post_type;

-- Step 2: Update the new column based on the old column values
UPDATE post_image SET post_type_new = 
    CASE post_type
        WHEN 'AFALGUN' THEN 'MISSING_PERSON_REPORT'
        WHEN 'IRDATA' THEN 'FUNDRAISING_CAMPAIGN'
        WHEN 'TIKOMA' THEN 'COMMUNITY_ALERT'
        WHEN 'FREE_SERVICE' THEN 'COMMUNITY_RESOURCE'
        WHEN 'WITH_YOU' THEN 'IMPACT_STORY'
    END;

-- Step 3: Drop the old column and rename the new column
ALTER TABLE post_image DROP COLUMN post_type;
ALTER TABLE post_image CHANGE COLUMN post_type_new post_type ENUM('MISSING_PERSON_REPORT', 'FUNDRAISING_CAMPAIGN', 'COMMUNITY_ALERT', 'COMMUNITY_RESOURCE', 'IMPACT_STORY') NOT NULL;

-- Step 4: Update the index on post_type and post_id to use the new column (the index name remains the same)
-- The index idx_post_type_id already exists on (post_type, post_id). Since we changed the column, the index is still valid.

-- Update indexes names to reflect new table names (optional but good for consistency)
-- Note: In MySQL, index names are unique per table, so we don't need to rename indexes on the renamed tables.
-- However, we should update the index names that reference the old table names in their definitions (they don't).
-- The following indexes were created on the old tables and will be automatically renamed because they are attached to the tables.
-- But we need to update the index definitions that reference the old table names? Actually, the indexes are on the tables, and the tables are renamed, so the indexes are renamed accordingly.

-- However, we have indexes like idx_afalgun_status, idx_irdata_status, etc. that are named after the old table names.
-- We should rename these indexes for clarity.
ALTER TABLE missing_person_report RENAME INDEX idx_afalgun_status TO idx_missing_person_report_status;
ALTER TABLE missing_person_report RENAME INDEX idx_afalgun_created_at TO idx_missing_person_report_created_at;

ALTER TABLE fundraising_campaign RENAME INDEX idx_irdata_status TO idx_fundraising_campaign_status;
ALTER TABLE fundraising_campaign RENAME INDEX idx_irdata_created_at TO idx_fundraising_campaign_created_at;

ALTER TABLE community_alert RENAME INDEX idx_tikoma_urgency TO idx_community_alert_urgency;
ALTER TABLE community_alert RENAME INDEX idx_tikoma_created_at TO idx_community_alert_created_at;

ALTER TABLE community_resource RENAME INDEX idx_free_service_active TO idx_community_resource_active;
ALTER TABLE community_resource RENAME INDEX idx_free_service_category TO idx_community_resource_category;
ALTER TABLE community_resource RENAME INDEX idx_free_service_location TO idx_community_resource_location;

ALTER TABLE impact_story RENAME INDEX idx_with_you_approved TO idx_impact_story_approved;
ALTER TABLE impact_story RENAME INDEX idx_with_you_created_at TO idx_impact_story_created_at;

-- Note: The foreign key constraints that reference the users table are still valid because we didn't change the users table.
-- Also, the foreign key constraints from the renamed tables (created_by) still reference users(id).

--changeset amenski:update_post_image_foreign_key_constraints
-- We need to update the post_image table's foreign key constraints? Actually, the foreign key is on (post_type, post_id) but now the post_type values have changed.
-- However, the post_image table does not have a foreign key constraint to the specific tables because of the polymorphic association.
-- So we don't need to update foreign keys.

-- The changes are now complete.
