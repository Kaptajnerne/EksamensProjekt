USE pct_db;

INSERT INTO organization (organization_name, password) VALUES ('KEA', '1234');
>>>>>>> simons-branch

INSERT INTO employee (employee_firstname, employee_lastname, email, organization_id) VALUES ('Jan', 'Mikkelsen', 'janautofirma@live.dk', 1);

INSERT INTO project (project_name, estimated_time, employee_id, organization_id) VALUES ('Project Auto Jan', null, 1, 1);

INSERT INTO task (task_name, start_date, end_date, project_id) VALUES ('Hjulmontering', '2023-05-01', '2023-05-09', 1);
INSERT INTO task (task_name, start_date, end_date, project_id) VALUES ('Lakering', null, null, 1);

INSERT INTO subtask (subtask_name, start_date, end_date, task_id) VALUES ('Find farvekode', '2023-05-02', '2023-05-03', 2);

INSERT INTO user_task (task_id, employee_id) VALUES (1, 1);
INSERT INTO user_task (task_id, employee_id) VALUES (2, 1);

INSERT INTO user_subtask (subtask_id, employee_id) VALUES (1, 1);
>>>>>>> simons-branch
