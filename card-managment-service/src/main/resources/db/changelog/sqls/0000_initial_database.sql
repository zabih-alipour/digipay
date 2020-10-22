-- liquibase format sql

-- changeset alipour:13990730_1
CREATE TABLE card (
    id int not null primary key AUTO_INCREMENT,
    card_number VARCHAR(255) NOT NULL,
    owner_name VARCHAR(50) NOT NULL,
    vcc2 int  NOT NULL,
    current_balance int  NOT NULL,
    expiration_date date  NOT NULL,
    online_pass VARCHAR(100)  NOT NULL,
    card_status VARCHAR(20) NOT NULL,
    create_date date NOT NULL,
    update_date date NOT NULL,
    CONSTRAINT uq_card_number_of_card UNIQUE (card_number),
    CONSTRAINT uq_number__vcc2_of_card UNIQUE (card_number, vcc2)
);