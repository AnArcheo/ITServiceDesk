create table task_attachments
(
    id          bigserial           not null,
    name        varchar(255) unique not null,
    type        varchar(255)        not null,
    attachments oid,
    task_id     bigint,
    primary key (id),
    constraint fk_task_id
        foreign key (task_id)
            references tasks
);