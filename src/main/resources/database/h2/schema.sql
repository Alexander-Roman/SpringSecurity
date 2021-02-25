CREATE TABLE accounts
(
    id BIGINT AUTO_INCREMENT,
    username  VARCHAR(45)                      NOT NULL UNIQUE,
    password   VARCHAR(255)                      NOT NULL,
    role       ENUM ('USER', 'EDITOR', 'ADMIN') NOT NULL,
    blocked    BOOLEAN                          NOT NULL,
    PRIMARY KEY (id)
);