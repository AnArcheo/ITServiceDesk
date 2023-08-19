create table users
(
    id            bigserial    not null,
    username      varchar(30) unique,
    password      varchar(256) not null,
    email         varchar(45)  not null unique,
    first_name    varchar(20)  not null,
    last_name     varchar(20)  not null,
--     gender        varchar(10)  check (gender in ('FEMALE', 'MALE')),
    is_active     boolean      not null,
    user_token    varchar(256),
    profile_photo oid,
    primary key (id)
);

create table users_projects
(
    project_id bigint not null,
    user_id    bigint not null,
    primary key (project_id, user_id),
    constraint fk_project_id
        foreign key (project_id)
            references projects,
    constraint fk_user_id
        foreign key (user_id)
            references users
);

create table users_roles
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    constraint fk_user_id
        foreign key (user_id)
            references users,
    constraint fk_role_id
        foreign key (role_id)
            references roles
);

