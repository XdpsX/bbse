--liquibase formatted sql

--changeset xdpsx:issue-1
--comment: Create the media table
CREATE TABLE media
(
    id         bigserial    NOT NULL,
    caption    varchar(255) NULL,
    file_name  varchar(255) NULL,
    file_path  text         NULL,
    file_type  varchar(128) NULL,
    CONSTRAINT media_pk PRIMARY KEY (id)
);