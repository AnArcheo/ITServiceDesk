create table task_comments
(
    id              bigserial    not null,
    comment_content varchar(255) not null,
    author          varchar(255),
    created_date    timestamp(6) not null,
    task_id         bigint,
    primary key (id),
    constraint fk_task_id
        foreign key (task_id)
            references tasks
);