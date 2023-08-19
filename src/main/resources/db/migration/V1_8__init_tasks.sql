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
    primary key (id),
    constraint fk_project_id
        foreign key (project_id)
            references projects,
    constraint fk_user_id
        foreign key (user_id)
            references users

);




