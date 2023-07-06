CREATE TABLE deals
(
    id                      BIGSERIAL       PRIMARY KEY,
    is_active               BOOLEAN         NOT NULL DEFAULT true,
    creation_timestamp      TIMESTAMP       NOT NULL,
    update_timestamp        TIMESTAMP       NOT NULL,
    from_currency           VARCHAR(255)    NOT NULL,
    to_currency             VARCHAR(255)    NOT NULL,
    amount                  NUMERIC         NOT NULL
);