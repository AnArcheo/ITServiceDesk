alter table if exists bug_attachments
    drop
        constraint if exists fk_bug_id;

alter table if exists bug_comments
    drop
        constraint if exists fk_user_id;

alter table if exists bug_comments
    drop
        constraint if exists fk_bug_id;

alter table if exists task_comments
    drop
        constraint if exists fk_user_id;

alter table if exists task_comments
    drop
        constraint if exists fk_task_id;

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

drop table if exists task_attachments cascade;
drop table if exists bug_attachments cascade;
drop table if exists task_comments cascade;
drop table if exists bug_comments cascade;
drop table if exists bugs cascade;
drop table if exists companies cascade;
drop table if exists projects cascade;
drop table if exists roles cascade;
drop table if exists tasks cascade;
drop table if exists users cascade;
drop table if exists users_projects cascade;
drop table if exists users_roles cascade;
DROP TABLE IF EXISTS flyway_schema_history CASCADE;