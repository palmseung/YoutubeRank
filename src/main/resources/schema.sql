create table keyword (
    keyword_id bigint not null auto_increment,
    keyword varchar(255) not null,
    primary key (keyword_id)
) engine=InnoDB

create table member (
    user_id bigint not null auto_increment,
    created_date datetime(6),
    modified_date datetime(6),
    email varchar(255) not null,
    name varchar(255) not null,
    password varchar(255) not null,
    primary key (user_id)
) engine=InnoDB

create table member_roles (
    member_user_id bigint not null,
    roles varchar(255)
) engine=InnoDB

create table my_keyword (
    memberkeyword_id bigint not null auto_increment,
    created_date datetime(6),
    modified_date datetime(6),
    keyword_id bigint,
    member_id bigint,
    primary key (memberkeyword_id)
) engine=InnoDB