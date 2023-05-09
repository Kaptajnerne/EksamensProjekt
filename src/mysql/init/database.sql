CREATE DATABASE IF NOT EXISTS pct_db;
USE pct_db;

DROP TABLE IF EXISTS user_subtask;
DROP TABLE IF EXISTS user_task;
DROP TABLE IF EXISTS subtask;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS project_employee;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS organization;

CREATE TABLE organization
(organization_id int not null auto_increment,
 organization_name varchar(100) not null,
 password varchar(30),
 primary key(organization_id));

CREATE TABLE employee
(employee_id int not null auto_increment,
 employee_firstname varchar(100) not null,
 employee_lastname varchar(100),
 email varchar(320),
 organization_id int not null,
 primary key(employee_id));

CREATE TABLE project
(project_id int not null auto_increment,
 project_name varchar(100) not null,
 estimated_time double,
 organization_id int not null,
 primary key(project_id),
 foreign key(organization_id) references organization (organization_id));

CREATE TABLE project_employee
(project_id int,
 employee_id int,
 primary key(project_id, employee_id),
 foreign key(project_id) references project (project_id),
 foreign key(employee_id) references employee (employee_id));

CREATE TABLE task
(task_id int not null auto_increment,
 task_name varchar(100),
 start_date date,
 end_date date,
 project_id int not null,
 primary key(task_id),
 foreign key(project_id) references project (project_id));

CREATE TABLE subtask
(subtask_id int not null auto_increment,
 subtask_name varchar(100),
 start_date date,
 end_date date,
 task_id int not null,
 primary key(subtask_id),
 foreign key(task_id) references task (task_id));

CREATE TABLE user_task
(task_id int,
 employee_id int,
 primary key(task_id, employee_id),
 foreign key(task_id) references task (task_id),
 foreign key(employee_id) references employee (employee_id));

CREATE TABLE user_subtask
(subtask_id int,
 employee_id int,
 primary key (subtask_id, employee_id),
 foreign key(subtask_id) references subtask (subtask_id),
 foreign key(employee_id) references employee (employee_id));