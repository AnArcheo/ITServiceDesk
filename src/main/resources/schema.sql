-- Drop table users_roles;
-- drop table users;
-- drop table roles;
--
-- CREATE TABLE roles
-- (
--     id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
--     name VARCHAR(45)                             NOT NULL,
--     CONSTRAINT pk_roles PRIMARY KEY (id)
-- );
--
-- ALTER TABLE roles
--     ADD CONSTRAINT uc_roles_name UNIQUE (name);
--
-- CREATE TABLE users
-- (
--     id         BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
--     email      VARCHAR(45)                             NOT NULL,
--     password   VARCHAR(64)                             NOT NULL,
--     first_name VARCHAR(20)                             NOT NULL,
--     last_name  VARCHAR(20)                             NOT NULL,
--     CONSTRAINT pk_users PRIMARY KEY (id)
-- );
--
-- CREATE TABLE users_roles
-- (
--     role_id BIGINT NOT NULL,
--     user_id BIGINT NOT NULL,
--     CONSTRAINT pk_users_roles PRIMARY KEY (role_id, user_id)
-- );
--
-- ALTER TABLE users
--     ADD CONSTRAINT uc_users_email UNIQUE (email);
--
-- ALTER TABLE users_roles
--     ADD CONSTRAINT fk_userol_on_role FOREIGN KEY (role_id) REFERENCES roles (id);
--
-- ALTER TABLE users_roles
--     ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES users (id);


alter table if exists bug_attachments
drop
constraint if exists fk_bug_id;

alter table if exists bugs
drop
constraint if exists fk_user_id;

alter table if exists bugs
drop
constraint if exists fk_project_id;

alter table if exists projects
drop
constraint if exists fk_company_id;

alter table if exists task_attachments
drop
constraint if exists fk_task_id;

alter table if exists tasks
drop
constraint if exists fk_user_id;

alter table if exists tasks
drop
constraint if exists fk_project_id;

alter table if exists users_projects
drop
constraint if exists fk_project_id;

alter table if exists users_projects
drop
constraint if exists fk_user_id;

alter table if exists users_roles
drop
constraint if exists fk_role_id;

alter table if exists users_roles
drop
constraint if exists fk_user_id;


drop table if exists bug_attachments cascade;

drop table if exists bugs cascade;

drop table if exists companies cascade;

drop table if exists projects cascade;

drop table if exists roles cascade;

drop table if exists task_attachments cascade;

drop table if exists tasks cascade;

drop table if exists users cascade;

drop table if exists users_projects cascade;

drop table if exists users_roles cascade;

create table companies
(
    id           bigserial    not null,
    company_name varchar(255) not null unique,
    primary key (id)
);


create table projects
(
    id           bigserial    not null,
    project_name varchar(255) not null unique,
    company_id   bigint,
    primary key (id)
);


create table roles
(
    id   bigserial   not null,
    name varchar(45) not null unique,
    primary key (id)
);

create table users
(
    id            bigserial    not null,
    username      varchar(30) unique,
    password      varchar(256) not null,
    email         varchar(45)  not null unique,
    first_name    varchar(20)  not null,
    last_name     varchar(20)  not null,
    gender        varchar(10)  not null check (gender in ('FEMALE', 'MALE')),
    is_active     boolean      not null,
    profile_photo oid,
    primary key (id)
);

create table users_projects
(
    project_id bigint not null,
    user_id    bigint not null,
    primary key (project_id, user_id)
);

create table users_roles
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id)
);





create table bugs
(
    id            bigserial    not null,
    title         varchar(30)  not null,
    description   varchar(255) not null,
    priority      varchar(255) check
        (priority in ('LOW', 'MEDIUM', 'HIGH', 'SEVERE')),
    status        varchar(255) check
        (status in ('NOT_STARTED', 'IN_PROGRESS', 'OPEN', 'TO_BE_TESTED', 'REVIEWING', 'POSTPONED',
                    'NOT_AN_ISSUE', 'NOT_A_BUG', 'CANCELLED', 'RESOLVED', 'CLOSED')),
    created_date  timestamp(6) not null,
    due_date      timestamp(6) not null,
    modified_date timestamp(6),
    reported_by   VARCHAR(255) not null,
    assigned_to   VARCHAR(255) not null,
    project_name  VARCHAR(255) not null,
    project_id    bigint,
    user_id       bigint,
    primary key (id)
);


create table tasks
(
    id            bigserial    not null,
    title         varchar(255) not null,
    priority      varchar(255) check (priority in ('LOW', 'MEDIUM', 'HIGH', 'SEVERE')),
    status        varchar(255) check (status in ('NOT_STARTED', 'IN_PROGRESS', 'POSTPONED', 'COMPLETED')),
    created_date  timestamp(6) not null,
    due_date      timestamp(6) not null,
    modified_date timestamp(6),
    created_by    VARCHAR(255) not null,
    assigned_to   VARCHAR(255) not null,
    project_name  VARCHAR(255) not null,
    project_id    bigint,
    user_id       bigint,
    primary key (id)
);

create table bug_attachments
(
    id          bigserial           not null,
    name        varchar(255) unique not null,
    type        varchar(255)        not null,
    attachments oid,
    bug_id      bigint,
    primary key (id)
);


create table task_attachments
(
    id          bigserial           not null,
    name        varchar(255) unique not null,
    type        varchar(255)        not null,
    attachments oid,
    task_id     bigint,
    primary key (id)
);

alter table if exists bug_attachments
    add constraint fk_bug_id
    foreign key (bug_id)
    references bugs;

alter table if exists bugs
    add constraint fk_user_id
    foreign key (user_id)
    references users;

alter table if exists bugs
    add constraint fk_project_id
    foreign key (project_id)
    references projects;

alter table if exists projects
    add constraint fk_company_id
    foreign key (company_id)
    references companies;

alter table if exists task_attachments
    add constraint fk_task_id
    foreign key (task_id)
    references tasks;

alter table if exists tasks
    add constraint fk_user_id
    foreign key (user_id)
    references users;

alter table if exists tasks
    add constraint fk_project_id
    foreign key (project_id)
    references projects;

alter table if exists users_projects
    add constraint fk_project_id
    foreign key (project_id)
    references projects;

alter table if exists users_projects
    add constraint fk_user_id
    foreign key (user_id)
    references users;

alter table if exists users_roles
    add constraint fk_role_id
    foreign key (role_id)
    references roles;

alter table if exists users_roles
    add constraint fk_user_id
    foreign key (user_id)
    references users;