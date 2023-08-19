INSERT INTO roles
VALUES (1, 'USER'),
       (2, 'ADMIN'),
       (3, 'MANAGER'),
       (4, 'IT TECHNICIAN'),
       (5, 'DEVELOPER'),
       (6, 'TESTER');


INSERT INTO users(id, username, password, email, first_name, last_name, is_active)
VALUES
    (101, 'Matt.Matter', '$2a$12$SlcIQKRYR.mECgOsMzNWJ.xOfzJkcB3F2Vu7aWUjiKm7bb7Ypsmiy', 'user1@gmail.com', 'Matt', 'Matter',  true),
    (102, 'Emma.Smith', '$2a$12$LzZlbLqpFz4gm08f8z8POeGyQTPl7pK49ycF/SEnTjN/yCF/AvSDi', 'user2@gmail.com', 'Emma', 'Smith',  true ),
    (103, 'Jason.Butter', '$2a$12$ST0p..BUpI4OEVjahWU4te.x26cjTPvX..LJTo1oI3FGjJoxL4gJm', 'user3@gmail.com', 'Jason', 'Butter',  true),
    (104, 'Tara.Anderson', '$2a$12$VMvXiw/gIGnCGm/s2XUIf.9wrVFya9rPqeN9oGUSXMoCbRFAgIsLi', 'user4@gmail.com', 'Tara', 'Anderson', true),
    (105, 'Tobi.Ferguson', '$2a$12$mdsiY63nDtYjx6EzVFt7BeK3Kc9pFVmxJpJMRbI9dlXDGJeGm4zvq', 'user5@gmail.com', 'Tobi', 'Ferguson',  true),
    (106, 'Anna.Dubois', '$2a$12$FwH1s4eaGgpHoyR/PMKT2OMDofwuvfKdyiHz9fZk7zudqMvS2en0m', 'user6@gmail.com', 'Anna', 'Dubois', true),
    (107, 'Stacey.Rossenthal', '$2a$12$kIX9fVWnKNB4DRTWYIeP7Or493CK4YbbGpNYDzpdzbXyx0TBiGO26', 'user7@gmail.com', 'Stacey', 'Rossenthal',  true),
    (108, 'Alan.McMillan', '$2a$12$Dfbdj2pw4gRXqOip2ywimOB/Qg3TrhvxzOXM7Gah9zGDZhFvLEXne', 'user8@gmail.com', 'Alan', 'McMillan',  true),
    (109, 'Ciara.Cornwall', '$2a$12$pwBWRyKg.3OXlAmzh54xUexcM.wDtpB8CqZXc5DqrA5xtmwtcD0SW', 'user9@gmail.com', 'Ciara', 'Cornwall',  true),
    (110, 'Tommy.Bishop', '$2a$12$7E9zHQeA81VinBlnwclNnuoXz0caK.G5UkAB0HW8h5m3.gq5Yhqd2', 'user10@gmail.com', 'Tommy', 'Bishop',  true),
    (111, 'Bart.West', '$2a$12$N3xxN4L.24oBtmeCinkZiu.i5RGUDXizxiskkk6hVcwRgEBquOame', 'user11@gmail.com', 'Bart', 'West',  true),
    (112, 'Sara.Brady', '$2a$12$HzgJXzbfgCPaHfsIuCLiJe1kqEV12VqYXF4UpT3AB1GTB8AMsXfem', 'user12@gmail.com', 'Sara', 'Brady',  true);

INSERT INTO users_roles(user_id, role_id)
VALUES
    (101, 1),(101, 5),
    (102, 2), (102, 5),
    (103, 1), (103, 5),
    (104, 3), (104, 4),
    (105, 1),
    (106, 2), (106, 3),
    (107, 4),
    (108, 5), (108, 1),
    (109, 5),
    (110, 6), (110, 3),
    (111, 6),
    (112, 3);

INSERT INTO companies(id, company_name)
VALUES
    (1, 'Big Deal Development'),
    (2, 'IT Bug'),
    (3, 'Service Now'),
    (4, 'Salesforce');

INSERT INTO projects(project_name, company_id)
VALUES
    ('Project Rainbow',  1),
    ('Project Bluebird', 2),
    ('Dragonfly 2', 3),
    ('NBI',4),
    ('Gigaclear', 4);


INSERT INTO users_projects(project_id, user_id)
VALUES
    (1, 104), (1, 102), (1, 106), (1, 108), (1, 110),
    (2, 106), (2, 102), (2, 104), (2, 108), (2, 103), (2, 111),
    (3, 110), (3, 102), (3, 106), (3, 107), (3, 109), (3, 101), (3, 103),
    (4, 112), (4, 102), (4, 106), (4, 107), (4, 109), (4, 103), (4, 111),
    (5, 106), (5, 102), (5, 104), (5, 101), (5, 108), (5, 110), (5, 111);

INSERT INTO tasks(id, title, priority, status, created_date, due_date, created_by, assigned_to, project_name)
VALUES
    (100, 'Meeting with stakeholders', 'LOW', 'NOT_STARTED', '2023-07-30 14:30:00', '2023-08-05 10:30:00', 'Emma.Smith', 'Tara.Anderson', 'Project Rainbow'),
    (101, 'Project Planning and meeting', 'MEDIUM', 'POSTPONED', '2023-08-01 10:30:00', '2023-08-10 09:00:00', 'Emma.Smith', 'Matt.Matter', 'Project Bluebird'),
    (103, 'Create teams chats for users', 'HIGH', 'IN_PROGRESS', '2023-08-04 12:35:00', '2023-08-07 12:00:00', 'Tobi.Ferguson', 'Alan.McMillan', 'Dragonfly 2'),
    (104, 'Create Project requirements description', 'SEVERE', 'COMPLETED', '2023-07-30 14:30:00', '2023-08-05 10:30:00', 'Anna.Dubois', 'Tommy.Bishop', 'Project Bluebird');

INSERT INTO task_comments(id, comment_content, author, created_date, task_id)
VALUES
    (200, 'All materials gathered and documents created', 'Tommy.Bishop', '2023-08-03 09:30:00', 100),
    (300, 'Email sent to stakeholder to place a meeting', 'Tommy.Bishop', '2023-08-04 15:30:00', 100);
