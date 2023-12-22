create table payment (
    id bigint auto_increment,
    amount int,
    time timestamp,
    contract_id bigint,
--    card_id bigint,
    constraint payment_pk primary key (id),
    constraint payment_contract_fk foreign key (contract_id) references contract (id),
--    constraint payment_card_fk foreign key (card_id) references card (id)
);

create table customer (
    id bigint auto_increment,
    tax_id bigint,
    name varchar(40),
    address varchar(60),
    contract_id bigint,
    constraint customer_pk primary key (id),
    constraint customer_contract_fk foreign key (contract_id) references contract (id)
);

--date field not sure that correct
create table contract (
    id bigint auto_increment,
    name varchar(40),
    subject varchar(40),
    sum int,
    comments varchar(255),
    date date,
    payment_id bigint,
    constraint contract_pk primary key (id),
    constraint contract_payment_fk foreign key (payment_id) references payment (id)
);

create table card (
    id bigint auto_increment,
    payment_id bigint,
    constraint card_pk primary key (id),
    constraint card_fk foreign key (payment_id) references payment (id)
);