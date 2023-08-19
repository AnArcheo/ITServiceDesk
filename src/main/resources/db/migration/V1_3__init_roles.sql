create table roles
(
    id   bigserial   not null,
    name varchar(45) not null unique,
    primary key (id)
);