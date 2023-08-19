create table bug_attachments
(
    id          bigserial           not null,
    name        varchar(255) unique not null,
    type        varchar(255)        not null,
    attachments oid,
    bug_id      bigint,
    primary key (id),
    constraint fk_bug_id
        foreign key (bug_id)
            references bugs
);