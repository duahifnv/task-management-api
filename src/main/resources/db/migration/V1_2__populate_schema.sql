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
        'Doe'),
       ('094c52a9-8146-415e-882b-5156308ee6fb',
        'user2@mail.ru',
        '$2a$12$amMY2qOubnHPHJs4lU6l.ej5Cgx9W0/koVU7zbqzI9u6s2Fv70yp6',
        'ROLE_USER',
        'Max',
        'Toy');

insert into public.tasks (task_id, label, description, priority, author_id)
values ('fd505668-3311-4094-817b-0c44660bb2ca', 'Basic task', 'This is just a default task',
        'LOW', '9e976257-f60b-418e-ae39-e34bb78d9e58'),
       ('a74bc275-25e0-4102-b790-a5817b248706', 'Cool task', 'This task better then default',
        'MIDDLE', '9e976257-f60b-418e-ae39-e34bb78d9e58'),
       ('1b1d4956-92c1-4d2d-87d4-7722adb310a1', 'Best task', 'Best task ever',
        'HIGH', '9e976257-f60b-418e-ae39-e34bb78d9e58');

insert into public.tasks_executors (task_id, user_id)
values ('fd505668-3311-4094-817b-0c44660bb2ca', '7b2f2680-556b-4bcd-96e7-344c9c6658d7'),
       ('a74bc275-25e0-4102-b790-a5817b248706', '7b2f2680-556b-4bcd-96e7-344c9c6658d7'),
       ('a74bc275-25e0-4102-b790-a5817b248706', '094c52a9-8146-415e-882b-5156308ee6fb'),
       ('1b1d4956-92c1-4d2d-87d4-7722adb310a1', '7b2f2680-556b-4bcd-96e7-344c9c6658d7'),
       ('1b1d4956-92c1-4d2d-87d4-7722adb310a1', '094c52a9-8146-415e-882b-5156308ee6fb');

insert into public.comments (comment_id, label, message, task_id, user_id)
values ('a979690a-0dc4-4d9f-84d4-fb182b30bd46', 'My opinion', 'This task is bullshit',
        'fd505668-3311-4094-817b-0c44660bb2ca', '7b2f2680-556b-4bcd-96e7-344c9c6658d7'),
       ('bf4e2b91-6629-4a18-a934-151af0bc52e7', 'Wow', 'Im impressed how hard it is',
        'a74bc275-25e0-4102-b790-a5817b248706', '094c52a9-8146-415e-882b-5156308ee6fb'),
       ('2ee5d228-cc49-4f3a-9a7a-d8035a618ce6', 'Hmm', 'My friend stuck with this task',
        'a74bc275-25e0-4102-b790-a5817b248706', '7b2f2680-556b-4bcd-96e7-344c9c6658d7');