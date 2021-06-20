CREATE TABLE jarand_user
(
    id               UUID PRIMARY KEY,
    email            VARCHAR NOT NULL UNIQUE,
    username         VARCHAR NOT NULL UNIQUE,
    display_name     VARCHAR NOT NULL UNIQUE,
    password         VARCHAR NOT NULL,
    time_of_creation VARCHAR NOT NULL
);

CREATE TABLE jarand_client
(
    id               UUID PRIMARY KEY,
    client_id        VARCHAR NOT NULL UNIQUE,
    client_secret    VARCHAR NOT NULL UNIQUE,
    owner_id         UUID    NOT NULL REFERENCES jarand_user (id),
    time_of_creation VARCHAR NOT NULL
);

CREATE TABLE scope
(
    id               VARCHAR NOT NULL UNIQUE,
    description      VARCHAR NOT NULL,
    time_of_creation VARCHAR NOT NULL
);

CREATE TABLE scope_connection
(
    scope_id         VARCHAR NOT NULL REFERENCES scope (id),
    jarand_client_id UUID    NOT NULL REFERENCES jarand_client (id),
    PRIMARY KEY (scope_id, jarand_client_id)
);

CREATE TABLE refresh_token
(
    jti       VARCHAR NOT NULL UNIQUE,
    subject   VARCHAR NOT NULL,
    issued_at VARCHAR NOT NULL
);
