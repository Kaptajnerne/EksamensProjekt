CREATE DATABASE IF NOT EXISTS pct_db2;
USE pct_db2;

DROP TABLE IF EXISTS subtask;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS user;

CREATE TABLE user
(user_id int not null auto_increment,
 username varchar(255) not null,
 password varchar(255),
 primary key(user_id));

CREATE TABLE project
(project_id int not null auto_increment,
 project_name varchar(255) not null,
 project_description varchar(1000),
 start_date date,
 end_date date,
 user_id int not null,
 primary key(project_id),
 foreign key(user_id) references user (user_id));

CREATE TABLE task
(task_id int not null auto_increment,
 task_name varchar(255),
 hours double,
 start_date date,
 end_date date,
 status int,
 project_id int not null,
 primary key(task_id),
 foreign key(project_id) references project (project_id));

CREATE TABLE subtask
(subtask_id int not null auto_increment,
 subtask_name varchar(255),
 hours double,
 start_date date,
 end_date date,
 status int,
 task_id int not null,
 primary key(subtask_id),
 foreign key(task_id) references task (task_id));