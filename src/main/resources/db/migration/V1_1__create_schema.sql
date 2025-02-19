create table public.users
(
    user_id uuid primary key,
    email    varchar(255) not null unique,
    password varchar(255) not null,
    role     varchar(100) not null,
    name varchar(100),
    surname varchar(100)
);

create table public.tasks(
    task_id uuid primary key,
    label varchar(100) not null,
    description text,
    status varchar(20) not null,
    priority varchar(20) not null,
    author_id uuid not null references users(user_id) on delete cascade
);

create table public.comments(
    comment_id uuid primary key,
    task_id uuid not null references tasks(task_id) on delete cascade,
    user_id uuid not null references users(user_id) on delete cascade
);

create table public.tasks_executors(
    task_id uuid not null references tasks(task_id) on delete cascade,
    user_id uuid not null references users(user_id) on delete cascade,
    primary key (task_id, user_id)
)