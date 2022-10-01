
set search_path TO public;

CREATE TABLE address (
    id          BIGSERIAL  NOT NULL PRIMARY KEY,
    user_id     INT8       NOT NULL REFERENCES app_user (id),
    line1       VARCHAR    NOT NULL DEFAULT '',
    city        VARCHAR    NOT NULL DEFAULT '',
    state       VARCHAR    NOT NULL DEFAULT '',
    zip         VARCHAR    NOT NULL DEFAULT ''
);

CREATE INDEX index_address_user ON address(user_id);

