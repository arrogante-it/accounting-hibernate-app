create TABLE payment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    amount INT,
    time TIMESTAMP,
    contract_id BIGINT,
    card_id BIGINT,
    FOREIGN KEY (contract_id) REFERENCES contract (id),
    FOREIGN KEY (card_id) REFERENCES card (id)
);

create TABLE customer (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    tax_id BIGINT,
    name VARCHAR(255),
    address VARCHAR(255)
);

create TABLE contract (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    subject VARCHAR(255),
    sum INT,
    comments VARCHAR(1000),
    date VARCHAR(10),
    customer_id BIGINT,
    FOREIGN KEY (customer_id) REFERENCES customer (id)
);

create TABLE card (
    id BIGINT PRIMARY KEY AUTO_INCREMENT
);