USE pct_db2;

INSERT INTO user (username, password) VALUES ('KEA', '1234');

INSERT INTO project (project_name, project_description, start_date, end_date, user_id) VALUES ('KEA Project', 'Cool Project developed by Kaptajnerne', '2023-05-01', '2023-06-01', 1);

INSERT INTO task (task_name, hours, start_date, end_date, status, project_id) VALUES ('KEA Task', '6', '2023-05-15', '2023-05-29', 'In progress', 1);

INSERT INTO subtask (subtask_name, hours, start_date, end_date, status, task_id) VALUES ('KEA Subtask', '5', '2023-05-17', '2023-05-20', 'Done', 1);
INSERT INTO subtask (subtask_name, hours, start_date, end_date, status, task_id) VALUES ('KEA Subtask 2', '4', '2023-05-20', '2023-05-24', 'In progress', 1);
INSERT INTO subtask (subtask_name, hours, start_date, end_date, status, task_id) VALUES ('KEA Subtask 3', '2', '2023-05-24', '2023-05-27', 'TODO', 1);


