use expandapis;

create table users
(
    user_id   bigint auto_increment,
    user_name varchar(30) unique not null,
    password  varchar(255)       not null,
    constraint users_user_id_PK primary key (user_id)
);