CREATE TABLE IF NOT EXISTS "weather_data" (
                              id BIGSERIAL PRIMARY KEY,
                              city_id BIGINT NOT NULL,
                              temperature DOUBLE NOT NULL,
                              FOREIGN KEY (city_id) REFERENCES cities (id)
);