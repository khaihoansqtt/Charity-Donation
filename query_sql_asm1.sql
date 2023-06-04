create database prj321x_asm1;
create table users(
	id int not null primary key auto_increment,
    user_name varchar(255) not null,
    password varchar(255) not null,
    full_name varchar(255) not null,
    email varchar(255) not null,
    address varchar(255) not null,
    phone_number varchar(255) not null,
    note varchar(255),
    status int(11) not null,
    role_id int(11) not null,
	created_at datetime not null default now(),
    foreign key (role_id) references roles(id));
insert into users(id, user_name, password, full_name, email, address, phone_number, note, status, role_id)
values (1, "khai_hoan", "123", "khai hoan", "khaihoan@gmail.com", "song binh", "12345", "hihi", 1, 1);
delete from users;
select * from users;

create table roles(
	id int not null primary key,
    role_name varchar(255) not null);
insert into roles values (1, "ADMIN"), (2, "USER");
select * from roles;
    
create table donations(
	id int not null primary key auto_increment,
    code varchar(255) not null,
    name varchar(255) not null,
    description varchar(255) not null,
    money int(11) not null,
    start_date date not null,
    end_date date not null,
    status int(11),
    organization_name varchar(255) not null,
    phone_number varchar(255) not null,
    created_at datetime not null default now());
insert into donations values (1, "code", "name", "description", 0, now(), now(), 1, "organization", "09778743214", now());
update donations set money = 0;
delete from donations;
select * from donations;

create table users_donations(
	id int not null primary key auto_increment,
    user_id int(11) not null,
    donation_id int(11) not null,
    money int(11) not null,
    name varchar(255) not null,
    status int(11) not null,
    text varchar(255),
    created_at datetime not null default now(),
    foreign key (user_id) references users(id) on delete cascade,
    foreign key (donation_id) references donations(id) on delete cascade);
insert into users_donations(id, user_id, donation_id, money, name, status, text) values (22, 2, 103, 1200, "hi", 0, "text");
select * from users_donations;
delete from users_donations;