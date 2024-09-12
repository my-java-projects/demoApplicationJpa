Hibernate
:

create table customer
(
    id            bigint       not null auto_increment,
    city_of_birth varchar(255),
    fathers_name  varchar(255),
    first_name    varchar(255),
    last_name     varchar(255),
    national_id   varchar(255) not null,
    phone_number  varchar(255),
    primary key (id)
) engine=InnoDB
Hibernate:

create table deposit
(
    id                   bigint not null auto_increment,
    initial_balance      decimal(38, 2),
    opening_date         datetime(6),
    withdrawable_balance decimal(38, 2),
    customer_id          bigint not null,
    deposit_type_id      bigint not null,
    primary key (id)
) engine=InnoDB
Hibernate:

create table deposit_type
(
    id            bigint       not null auto_increment,
    code          integer      not null,
    deposit_tools varchar(255),
    title         varchar(255) not null,
    primary key (id)
) engine=InnoDB
Hibernate:

alter table customer
    add constraint UK_rbkymfrrw8b539pyoy0x1qh5q unique (national_id) Hibernate:

alter table deposit_type
    add constraint UK_j0oo653mssbv9x3d1n69oe7pb unique (code) Hibernate:

alter table deposit
    add constraint FKkgx6p9skdj31daexc1kmvptl2
        foreign key (customer_id)
            references customer (id) Hibernate:

alter table deposit
    add constraint FK125vxvqlv5psqwt6kkayiu67v
        foreign key (deposit_type_id)
            references deposit_type (id)



////////////////////////////////////

-- Create the Customer table
CREATE TABLE IF NOT EXISTS customer (
    id BIGINT NOT NULL AUTO_INCREMENT,
    national_id VARCHAR(20) NOT NULL UNIQUE,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    fathers_name VARCHAR(255),
    city_of_birth VARCHAR(255),
    phone_number VARCHAR(255),
    PRIMARY KEY (id)
    );

-- Create the DepositType table
CREATE TABLE IF NOT EXISTS deposit_type (
    id BIGINT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    deposit_tools VARCHAR(255),
    code INT NOT NULL UNIQUE,
    PRIMARY KEY (id)
    );

-- Create the Deposit table
CREATE TABLE IF NOT EXISTS deposit (
    id BIGINT NOT NULL AUTO_INCREMENT,
    customer_id BIGINT NOT NULL,
    deposit_type_id BIGINT NOT NULL,
    initial_balance DECIMAL(38, 2) NOT NULL,
    withdrawable_balance DECIMAL(38, 2) NOT NULL,
    opening_date DATETIME(6) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (deposit_type_id) REFERENCES deposit_type(id)
    );
