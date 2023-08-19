--     id              bigserial    not null, oid


drop table if exists bug_attachments cascade;
drop table if exists bug_comments cascade;
drop table if exists bugs cascade;
drop table if exists companies cascade;
drop table if exists projects cascade;
drop table if exists roles cascade;
drop table if exists task_attachments cascade;
drop table if exists task_comments cascade;
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
--     company_name varchar(255) not null,
    company_id   bigint,
    primary key (id),
    constraint fk_projects_company_id
        foreign key (company_id)
            references companies
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
    first_name    varchar(20)  not null,
    last_name     varchar(20)  not null,
    username      varchar(30) unique,
    email         varchar(45)  not null unique,
    password      varchar(256) not null,
    is_active     boolean      not null,
    user_token    varchar(256),
    profile_photo oid,
    primary key (id)
);

create table users_roles
(
    role_id bigint not null,
    user_id bigint not null,
    primary key (role_id, user_id),
    constraint fk_roles_users
        foreign key (role_id)
            references roles,
    constraint fk_users_roles
        foreign key (user_id)
            references users
);

create table users_projects
(
    project_id bigint not null,
    user_id    bigint not null,
    primary key (project_id, user_id),
    constraint fk_projects_users
        foreign key (project_id)
            references projects,
    constraint fk_users_projects
        foreign key (user_id)
            references users
);

create table bugs
(
    id            bigserial    not null,
    title         varchar(50)  not null,
    description   varchar(255) not null,
    priority      varchar(255) check (priority in ('LOW', 'MEDIUM', 'HIGH', 'SEVERE')),
    status       varchar(255) check (status in
                                      ('NOT_STARTED', 'IN_PROGRESS', 'OPEN', 'TO_BE_TESTED', 'REVIEWING', 'POSTPONED',
                                       'NOT_AN_ISSUE', 'NOT_A_BUG', 'CANCELLED', 'RESOLVED', 'CLOSED')),
    created_date  timestamp(6) not null,
    due_date      timestamp(6) not null,
    modified_date timestamp(6),
    reporter_id bigint,
    user_id       bigint,
    project_id    bigint,
    primary key (id),
    constraint fk_bugs_reporter_id
        foreign key (reporter_id)
            references users,
    constraint fk_bugs_user_id
        foreign key (user_id)
            references users,
    constraint fk_bugs_project_id
        foreign key (project_id)
            references projects
);

create table bug_comments
(
    id              bigserial    not null,
    author_id          bigint,
    comment_content varchar(255) not null,
    created_date    timestamp(6) not null,
    bug_id          bigint,
    primary key (id),
    constraint fk_bug_comments_bug_id
        foreign key (bug_id)
            references bugs,
    constraint fk_bug_comments_author_id
        foreign key (author_id)
            references users
);

create table bug_attachments
(
    bug_id      bigint,
    id          bigserial not null,
    name        varchar(255),
    type        varchar(255),
    attachments oid,
    primary key (id),
    constraint fk_bug_attachments_bug_id
        foreign key (bug_id)
            references bugs
);

create table tasks
(
    id            bigserial    not null,
    title         varchar(255) not null,
    priority      varchar(255) check (priority in ('LOW', 'MEDIUM', 'HIGH', 'SEVERE')),
    status        varchar(255) check (status in
                                      ('NOT_STARTED', 'IN_PROGRESS', 'POSTPONED', 'COMPLETED', 'CANCELLED', 'ON_HOLD')),


    created_date  timestamp(6) not null,
    due_date      timestamp(6) not null,
    modified_date timestamp(6),
    creator_id bigint,
    user_id       bigint,
    project_id    bigint,
    primary key (id),
    constraint fk_tasks_user_id
        foreign key (user_id)
            references users,
    constraint fk_tasks_creator_id
        foreign key (creator_id)
            references users,
    constraint fk_tasks_project_id
        foreign key (project_id)
            references projects
);

create table task_comments
(
    id              bigserial    not null,
    comment_content varchar(255) not null,
    author_id          bigint,
    created_date    timestamp(6) not null,
    task_id         bigint,
    primary key (id),
    constraint fk_task_comments_task_id
        foreign key (task_id)
            references tasks,
    constraint fk_task_comments_author_id
        foreign key (author_id)
            references users
);

create table task_attachments
(
    id          bigserial not null,
    name        varchar(255),
    type        varchar(255),
    attachments oid,
    task_id     bigint,
    primary key (id),
    constraint fk_task_attachments_task_id
        foreign key (task_id)
            references tasks
);
