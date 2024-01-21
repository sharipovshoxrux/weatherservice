CREATE TABLE IF NOT EXISTS "cities" (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        country VARCHAR(255) NOT NULL
);