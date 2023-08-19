create table companies
(
    id           bigserial    not null,
    company_name varchar(255) not null unique,
    primary key (id)
);