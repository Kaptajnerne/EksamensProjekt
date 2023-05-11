USE pct_db2;

INSERT INTO user (username, password) VALUES ('KEA', '1234');

INSERT INTO project (project_name, user_id) VALUES ('KEA Project', 1);

INSERT INTO task (task_name, start_date, end_date, project_id) VALUES ('KEA Task', '2023-05-01', '2023-05-09', 1);

INSERT INTO subtask (subtask_name, start_date, end_date, task_id) VALUES ('KEA Subtask', '2023-05-02', '2023-05-03', 1);


