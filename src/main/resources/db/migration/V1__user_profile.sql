CREATE TABLE user_profile
(
    up_id BIGSERIAL PRIMARY KEY,
    email TEXT NOT NULL UNIQUE,
    image BYTEA
);
