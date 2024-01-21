CREATE TABLE IF NOT EXISTS "users" (
                       id BIGSERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       status BIGINT NOT NULL,
                       role VARCHAR(30) NOT NULL
);