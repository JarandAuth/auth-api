CREATE TABLE jarand_user
(
    id               UUID PRIMARY KEY,
    email            VARCHAR NOT NULL UNIQUE,
    username         VARCHAR NOT NULL UNIQUE,
    display_name     VARCHAR NOT NULL UNIQUE,
    time_of_creation VARCHAR NOT NULL
);

CREATE TABLE jarand_client
(
    client_id        VARCHAR PRIMARY KEY,
    type             VARCHAR NOT NULL,
    owner_id         UUID    NOT NULL REFERENCES jarand_user (id),
    time_of_creation VARCHAR NOT NULL,
    client_secret    VARCHAR UNIQUE,
    username         VARCHAR UNIQUE,
    password         VARCHAR
);

CREATE TABLE grant_type
(
    grant_type       VARCHAR PRIMARY KEY,
    time_of_creation VARCHAR NOT NULL
);

INSERT INTO grant_type(grant_type, time_of_creation)
VALUES ('client_credentials', (now() AT TIME ZONE 'UTC'));
INSERT INTO grant_type(grant_type, time_of_creation)
VALUES ('refresh_token', (now() AT TIME ZONE 'UTC'));
INSERT INTO grant_type(grant_type, time_of_creation)
VALUES ('password', (now() AT TIME ZONE 'UTC'));

CREATE TABLE granted_type
(
    grant_type       VARCHAR NOT NULL REFERENCES grant_type (grant_type),
    client_id        VARCHAR NOT NULL REFERENCES jarand_client (client_id),
    time_of_creation VARCHAR NOT NULL,
    PRIMARY KEY (grant_type, client_id)
);

CREATE TABLE scope
(
    id               VARCHAR PRIMARY KEY,
    description      VARCHAR NOT NULL,
    time_of_creation VARCHAR NOT NULL
);

CREATE TABLE scope_connection
(
    scope_id         VARCHAR NOT NULL REFERENCES scope (id),
    client_id        VARCHAR NOT NULL REFERENCES jarand_client (client_id),
    time_of_creation VARCHAR NOT NULL,
    PRIMARY KEY (scope_id, client_id)
);

CREATE TABLE refresh_token
(
    jti       VARCHAR NOT NULL UNIQUE,
    subject   VARCHAR NOT NULL REFERENCES jarand_client (client_id),
    issued_at VARCHAR NOT NULL
);
