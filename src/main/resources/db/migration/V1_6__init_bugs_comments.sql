create table bug_comments
(
    id              bigserial    not null,
    comment_content varchar(255) not null,
    author          varchar(255),
    created_date    timestamp(6) not null,
    bug_id          bigint,
    primary key (id),
    constraint fk_bug_id
        foreign key (bug_id)
            references bugs
);
