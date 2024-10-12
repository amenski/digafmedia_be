-- liquibase formatted sql

-- changeset aman:create-tables
create table product(
    id          bigint auto_increment,
    route_name varchar(255) not null,
    name        varchar(255) not null,
    image       varchar(255) not null,
    primary key (id)
);