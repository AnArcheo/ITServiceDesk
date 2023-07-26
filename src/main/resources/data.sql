INSERT INTO roles
VALUES (1, 'USER'),
       (2, 'ADMIN'),
       (3, 'MANAGER'),
       (4, 'IT TECHNICIAN'),
       (5, 'DEVELOPER'),
       (6, 'TESTER');


INSERT INTO users(username, password, email, first_name, last_name, gender, is_active, profile_photo)
VALUES
('user1', '$2a$12$SlcIQKRYR.mECgOsMzNWJ.xOfzJkcB3F2Vu7aWUjiKm7bb7Ypsmiy', 'user1@gmail.com', 'Matt', 'Matter', 'MALE', true, lo_import('C:\Users\anarc\IdeaProjects\ITServiceDesk\src\main\resources\static\images\avatars\male\male1.png')),
('user2', '$2a$12$LzZlbLqpFz4gm08f8z8POeGyQTPl7pK49ycF/SEnTjN/yCF/AvSDi', 'user2@gmail.com', 'Emma', 'Smith', 'FEMALE', true, lo_import('C:\Users\anarc\IdeaProjects\ITServiceDesk\src\main\resources\static\images\avatars\female\female1.png')),
('user3', '$2a$12$ST0p..BUpI4OEVjahWU4te.x26cjTPvX..LJTo1oI3FGjJoxL4gJm', 'user3@gmail.com', 'Jason', 'Butter', 'MALE', true, lo_import('C:\Users\anarc\IdeaProjects\ITServiceDesk\src\main\resources\static\images\avatars\male\male2.png')),
('user4', '$2a$12$VMvXiw/gIGnCGm/s2XUIf.9wrVFya9rPqeN9oGUSXMoCbRFAgIsLi', 'user4@gmail.com', 'Tara', 'Anderson', 'FEMALE', true, lo_import('C:\Users\anarc\IdeaProjects\ITServiceDesk\src\main\resources\static\images\avatars\female\female2.png')),
('user5', '$2a$12$mdsiY63nDtYjx6EzVFt7BeK3Kc9pFVmxJpJMRbI9dlXDGJeGm4zvq', 'user5@gmail.com', 'Tobi', 'Ferguson', 'MALE', true, lo_import('C:\Users\anarc\IdeaProjects\ITServiceDesk\src\main\resources\static\images\avatars\male\male3.png')),
('user6', '$2a$12$FwH1s4eaGgpHoyR/PMKT2OMDofwuvfKdyiHz9fZk7zudqMvS2en0m', 'user6@gmail.com', 'Anna', 'Dubois', 'FEMALE', true, lo_import('C:\Users\anarc\IdeaProjects\ITServiceDesk\src\main\resources\static\images\avatars\female\female3.png')),
('user7', '$2a$12$kIX9fVWnKNB4DRTWYIeP7Or493CK4YbbGpNYDzpdzbXyx0TBiGO26', 'user7@gmail.com', 'Stacey', 'Rossenthal', 'FEMALE', true, lo_import('C:\Users\anarc\IdeaProjects\ITServiceDesk\src\main\resources\static\images\avatars\female\female4.png')),
('user8', '$2a$12$Dfbdj2pw4gRXqOip2ywimOB/Qg3TrhvxzOXM7Gah9zGDZhFvLEXne', 'user8@gmail.com', 'Alan', 'McMillan', 'MALE', true, lo_import('C:\Users\anarc\IdeaProjects\ITServiceDesk\src\main\resources\static\images\avatars\male\male4.png')),
('user9', '$2a$12$pwBWRyKg.3OXlAmzh54xUexcM.wDtpB8CqZXc5DqrA5xtmwtcD0SW', 'user9@gmail.com', 'Ciara', 'Cornwall', 'FEMALE', true, lo_import('C:\Users\anarc\IdeaProjects\ITServiceDesk\src\main\resources\static\images\avatars\female\female5.png')),
('user10', '$2a$12$7E9zHQeA81VinBlnwclNnuoXz0caK.G5UkAB0HW8h5m3.gq5Yhqd2', 'user10@gmail.com', 'Tommy', 'Bishop', 'MALE', true, lo_import('C:\Users\anarc\IdeaProjects\ITServiceDesk\src\main\resources\static\images\avatars\male\male5.png')),
('user11', '$2a$12$N3xxN4L.24oBtmeCinkZiu.i5RGUDXizxiskkk6hVcwRgEBquOame', 'user11@gmail.com', 'Bart', 'West', 'MALE', true, lo_import('C:\Users\anarc\IdeaProjects\ITServiceDesk\src\main\resources\static\images\avatars\male\male6.png')),
('user12', '$2a$12$HzgJXzbfgCPaHfsIuCLiJe1kqEV12VqYXF4UpT3AB1GTB8AMsXfem', 'user12@gmail.com', 'Sara', 'Brady', 'FEMALE', true, lo_import('C:\Users\anarc\IdeaProjects\ITServiceDesk\src\main\resources\static\images\avatars\female\female6.png'));

INSERT INTO users_roles(user_id, role_id)
VALUES
    (1, 1),(1, 5),
    (2, 2), (2, 5),
    (3, 1), (3, 5),
    (4, 3), (4, 4),
    (5, 1),
    (6, 2), (6, 3),
    (7, 4),
    (8, 5), (8, 1),
    (9, 5),
    (10, 6), (10, 3),
    (11, 6),
    (12, 3);

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
(1, 4), (1, 2), (1, 6), (1, 8), (1, 10),
(2, 6), (2, 2), (2, 4), (2, 8), (2, 3), (2, 11),
(3, 10), (3, 2), (3, 6), (3, 7), (3, 9), (3, 1), (3, 3),
(4, 12), (4, 2), (4, 6), (4, 7), (4, 9), (4, 3), (4, 11),
(5, 6), (5, 2), (5, 4), (5, 1), (5, 8), (5, 10), (5, 11);
