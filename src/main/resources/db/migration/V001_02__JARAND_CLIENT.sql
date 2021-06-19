CREATE TABLE jarand_client
(
    id               UUID PRIMARY KEY,
    client_id        VARCHAR NOT NULL UNIQUE,
    client_secret    VARCHAR NOT NULL UNIQUE,
    owner_id         UUID    NOT NULL,
    time_of_creation VARCHAR NOT NULL
);
