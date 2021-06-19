CREATE TABLE jarand_user
(
    id               UUID PRIMARY KEY,
    email            VARCHAR NOT NULL UNIQUE,
    username         VARCHAR NOT NULL UNIQUE,
    display_name     VARCHAR NOT NULL UNIQUE,
    password         VARCHAR NOT NULL,
    time_of_creation VARCHAR NOT NULL
);

CREATE TABLE refresh_token
(
    jti       VARCHAR NOT NULL,
    subject   VARCHAR NOT NULL,
    issued_at VARCHAR NOT NULL
);
