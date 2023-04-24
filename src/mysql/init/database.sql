CREATE DATABASE IF NOT EXISTS pmdb;
USE pmdb;

DROP TABLE IF EXISTS project;

CREATE TABLE project
(project_id int not null auto_increment,
 project_name varchar(100) not null,
 duration double not null,
 primary key(projectID));