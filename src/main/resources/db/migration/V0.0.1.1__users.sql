
set search_path TO public;

CREATE TABLE app_user (
    id                  BIGSERIAL    NOT NULL PRIMARY KEY,
    username            VARCHAR(255) NOT NULL UNIQUE,
    email               VARCHAR(255) NOT NULL DEFAULT ''
);

CREATE TABLE address (
     id          BIGSERIAL  NOT NULL PRIMARY KEY,
     user_id     INT8       NOT NULL REFERENCES app_user (id),
     line1       VARCHAR    NOT NULL DEFAULT '',
     city        VARCHAR    NOT NULL DEFAULT '',
     state       VARCHAR    NOT NULL DEFAULT '',
     zip         VARCHAR    NOT NULL DEFAULT ''
);

CREATE INDEX index_address_user ON address(user_id);
