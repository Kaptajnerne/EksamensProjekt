CREATE DATABASE IF NOT EXISTS pct_db2;
USE pct_db2;

DROP TABLE IF EXISTS subtask;
DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS user;

CREATE TABLE user
(user_id INT NOT NULL auto_increment,
 username VARCHAR(255) NOT NULL,
 password VARCHAR(255),
 PRIMARY KEY(user_id));

CREATE TABLE project (
project_id INT NOT NULL AUTO_INCREMENT,
project_name VARCHAR(255) NOT NULL,
project_description VARCHAR(1000),
start_date DATE,
end_date DATE,
user_id INT NOT NULL,
PRIMARY KEY (project_id),
FOREIGN KEY (user_id) REFERENCES user (user_id)
);

CREATE TABLE task (
task_id INT NOT NULL AUTO_INCREMENT,
task_name VARCHAR(255),
hours DOUBLE CHECK (hours >= 0 AND hours <= 9999),
start_date DATE,
end_date DATE,
status INT,
project_id INT NOT NULL,
PRIMARY KEY (task_id),
FOREIGN KEY (project_id) REFERENCES project (project_id)
);

CREATE TABLE subtask (
subtask_id INT NOT NULL AUTO_INCREMENT,
subtask_name VARCHAR(255),
hours DOUBLE CHECK (hours >= 0 AND hours <= 9999),
start_date DATE,
end_date DATE,
status INT,
task_id INT NOT NULL,
PRIMARY KEY (subtask_id),
FOREIGN KEY (task_id) REFERENCES task (task_id)
);
