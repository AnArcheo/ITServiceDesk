create table projects
(
    id           bigserial    not null,
    project_name varchar(255) not null unique,
    company_id   bigint,
    primary key (id),
    constraint fk_company_id
        foreign key (company_id)
            references companies
);