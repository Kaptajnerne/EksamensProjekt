CREATE DATABASE IF NOT EXISTS pmdb;
USE pmdb;

DROP TABLE IF EXISTS project;

CREATE TABLE project
(projectID int not null auto_increment,
 projectName varchar(100) not null,
 duration double not null,
 primary key(projectID));