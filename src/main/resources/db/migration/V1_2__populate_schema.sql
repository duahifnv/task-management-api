insert into public.users (user_id, email, password, role, name, surname)
values ('9e976257-f60b-418e-ae39-e34bb78d9e58',
        'admin@mail.ru',
        '$2a$12$rdGXuv4foTUqYMPINsDdqOt/OFK9YHdkwoXZ/8a5Teo.lFM2V33VS',
        'ROLE_ADMIN',
        'Admin',
        'Adminov'),
       ('7b2f2680-556b-4bcd-96e7-344c9c6658d7',
        'user1@mail.ru',
        '$2a$12$a8LXtS.k6ZwfKlHldGDivuG.FvyiSP5sWqX7ukLqMo6eOgpEQ0Td2',
        'ROLE_USER',
        'Jane',
        'Doe');

insert into public.tasks (task_id, label, description, status,
                                          priority, author_id, creation_date)
values ('fd505668-3311-4094-817b-0c44660bb2ca', 'Basic task', 'This is just a default task', 'DONE',
        'LOW', '9e976257-f60b-418e-ae39-e34bb78d9e58', CURRENT_DATE),
       ('a74bc275-25e0-4102-b790-a5817b248706', 'Cool task', 'This task better then default', 'IN_PROGRESS',
        'MIDDLE', '7b2f2680-556b-4bcd-96e7-344c9c6658d7', CURRENT_DATE),
       ('1b1d4956-92c1-4d2d-87d4-7722adb310a1', 'Best task', 'Best task ever', 'PENDING',
        'HIGH', '7b2f2680-556b-4bcd-96e7-344c9c6658d7', CURRENT_DATE);