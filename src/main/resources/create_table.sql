drop table if exists card;
drop table if exists contract;
drop table if exists customer;
drop table if exists payment;

create table contract (
    id bigint auto_increment,
    name varchar(40) not null,
    subject varchar(40) not null,
    sum bigint not null,
    comments varchar(100) not null,
    date_contract date not null,
    customer_id bigint,
    constraint contract_pk primary key (id),
    constraint contract_customer_fk foreign key (customer_id) references customer (id)
);

create table customer (
    id bigint auto_increment,
    tax_id bigint not null,
    name varchar(40) not null,
    address varchar(60) not null,
    constraint customer_pk primary key (id)
);

create table payment (
    id bigint auto_increment,
    amount int not null,
    time timestamp not null,
    card_id bigint not null,
    constraint payment_pk primary key (id),
    constraint payment_card_fk foreign key (card_id) references card (id)
);

create table card (
    id bigint auto_increment,
    constraint card_pk primary key (id)
);