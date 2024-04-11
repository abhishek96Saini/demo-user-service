create table users
(
    id         int primary key auto_increment,
    name       varchar(50) NOT NULL,
    age        int         NOT NULL,
    experience int         NOT NULL,
    created_at timestamp   not null,
    updated_at timestamp   NOT NULL
);