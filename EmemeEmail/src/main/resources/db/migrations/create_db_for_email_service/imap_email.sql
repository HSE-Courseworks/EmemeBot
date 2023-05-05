CREATE TABLE imapEmail(
    id BIGSERIAL PRIMARY KEY,
    address VARCHAR(256) UNIQUE,
    password VARCHAR(128),
    host VARCHAR(128),
    lastChecked TIMESTAMP,
    lastUpdated TIMESTAMP
);