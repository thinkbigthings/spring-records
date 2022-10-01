
set search_path TO public;

CREATE TABLE app_user (
    id                  BIGSERIAL    NOT NULL PRIMARY KEY,
    username            VARCHAR(255) NOT NULL UNIQUE,
    email               VARCHAR(255) NOT NULL DEFAULT '',
    display_name        VARCHAR(255) NOT NULL DEFAULT '',
    enabled             BOOLEAN      NOT NULL DEFAULT FALSE,
    registration_time   TIMESTAMPTZ  NOT NULL DEFAULT now()
);
