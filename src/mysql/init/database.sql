CREATE DATABASE IF NOT EXISTS pmdb;
USE pmdb;

DROP TABLE IF EXISTS project;

CREATE TABLE project
(projectid int not null auto_increment,
 projectname varchar(100) not null,
 duration double not null,
 primary key(projectid));