CREATE TABLE subscriptions (
                               id BIGSERIAL PRIMARY KEY,
                               user_id BIGINT NOT NULL,
                               city_id BIGINT NOT NULL,
                               FOREIGN KEY (user_id) REFERENCES users (id),
                               FOREIGN KEY (city_id) REFERENCES cities (id)
);