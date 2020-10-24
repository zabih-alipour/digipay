-- liquibase format sql


-- changeset alipour:13990730_1
CREATE TABLE user
(
    id       int         not null primary key AUTO_INCREMENT,
    name     VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    mobile   VARCHAR(50) NOT NULL,

    CONSTRAINT uq_username_of_user UNIQUE (username)
);

-- changeset alipour:13990730_2
CREATE TABLE card
(
    id          int          not null primary key AUTO_INCREMENT,
    card_number VARCHAR(16) NOT NULL,
    user_id     int          NOT NULL,
    name        VARCHAR(100) NOT NULL,
    create_date date         NOT NULL,
    update_date date         NOT NULL,
    CONSTRAINT uq_user__card_of_card UNIQUE (user_id, card_number)
);

-- changeset alipour:13990730_4
CREATE TABLE cardex
(
    id              int          not null primary key AUTO_INCREMENT,
    source_card     varchar2(20) NOT NULL,
    dest_card       varchar2(20) NOT NULL,
    user_id         int          NOT NULL,
    amount          int          NOT NULL,
    transfer_status VARCHAR(20)  NOT NULL,
    create_date     date         NOT NULL
);
