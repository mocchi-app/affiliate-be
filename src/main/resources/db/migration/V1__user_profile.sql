CREATE TABLE user_profile
(
    up_id             BIGSERIAL PRIMARY KEY,
    email             TEXT NOT NULL UNIQUE,
    about             TEXT,
    location          TEXT,
    image             BYTEA,
    stripe_account_id TEXT
);

CREATE TABLE user_state
(
    us_id    BIGSERIAL PRIMARY KEY,
    us_up_id BIGINT REFERENCES user_profile (up_id)
);
