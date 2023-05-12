USE pct_db2;

INSERT INTO user (username, password) VALUES ('KEA', '1234');

INSERT INTO project (project_name, project_description, user_id) VALUES ('KEA Project', 'Cool Project developed by Kaptajnerne', 1);

INSERT INTO task (task_name, hours, project_id) VALUES ('KEA Task', '6', 1);

INSERT INTO subtask (subtask_name, hours, task_id) VALUES ('KEA Subtask', '5', 1);


