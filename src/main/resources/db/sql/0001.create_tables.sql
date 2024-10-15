-- liquibase formatted sql

-- changeset aman:create-tables
create table if not exists product
(
    id          bigint auto_increment,
    route_name  varchar(255) not null,
    name        varchar(255) not null,
    image       varchar(255) not null,
    created_at  datetime     NULL,
    modified_at datetime     NULL,
    primary key (id)
);

-- changeset aman:create-tables-items
create table if not exists item
(
    id           bigint auto_increment,
    title        varchar(255) not null,
    contact      varchar(255) not null,
    description  text not null,
    images       varchar(255) not null,
    published_on datetime     not null,
    product_id   bigint       not null,
    created_at   datetime     NULL,
    modified_at  datetime     NULL,
    primary key (id)
);