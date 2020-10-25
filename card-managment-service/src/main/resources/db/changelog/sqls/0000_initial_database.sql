CREATE TABLE user
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    mobile   VARCHAR(50) NOT NULL,

    CONSTRAINT uq_username_of_user UNIQUE (username)
);

CREATE TABLE card
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    card_number VARCHAR(16)                         NOT NULL,
    user_id     int                                 NOT NULL,
    name        VARCHAR(100)                        NOT NULL,
    create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT uq_user__card_of_card UNIQUE (user_id, card_number),
    FOREIGN KEY (user_id)
        REFERENCES user (id)
        ON UPDATE RESTRICT ON DELETE CASCADE
);

CREATE TABLE cardex
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    source_card     VARCHAR(20)                         NOT NULL,
    dest_card       VARCHAR(20)                         NOT NULL,
    user_id         int                                 NOT NULL,
    amount          int                                 NOT NULL,
    transfer_status VARCHAR(20)                         NOT NULL,
    create_date     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
