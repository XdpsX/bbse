--liquibase formatted sql

--changeset xdpsx:issue-3
--comment: Create the roles, users, invalidated_tokens tables
CREATE TABLE roles
(
    name        varchar(32) NOT NULL,
    description text,
    CONSTRAINT  roles_pk PRIMARY KEY (name)
);

CREATE TABLE users
(
    id         bigserial    NOT NULL,
    name       varchar(255) NOT NULL,
    email      varchar(255) NOT NULL UNIQUE,
    password   varchar(255) NOT NULL,
    created_at timestamp(6) DEFAULT now(),
    updated_at timestamp(6),
    role_name  varchar(32) NOT NULL,
    CONSTRAINT users_pk PRIMARY KEY (id),
    CONSTRAINT users_fk_role FOREIGN KEY (role_name) REFERENCES roles(name)
);

CREATE TABLE invalidated_tokens
(
    token        varchar(255) NOT NULL,
    expired_time timestamp(6),
    CONSTRAINT   invalidated_tokens_pk PRIMARY KEY (token)
);