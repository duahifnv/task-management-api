alter table public.tasks
    add column creation_date date not null default current_date;
alter table public.comments
    add column creation_time timestamp not null default current_timestamp;