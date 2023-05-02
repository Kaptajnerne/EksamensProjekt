<h2>ER diagram:</h2>

Table Organisation {
  organisation_id int [pk]
  organisation_name varchar
  password varchar
}

Table Employee {
  emp_id int [pk]
  email varchar
  username varchar
  organisation_id int
}

Table Project {
  project_id int [pk]
  project_name varchar
  estimated_time double
  emp_id int
  organisation_id int
}

Table Task {
  task_id int [pk]
  task_name varchar
  start_date date
  end_date date
  project_id int
}

Table Subtask {
  subtask_id int [pk]
  subtask_name varchar
  start_date date
  end_date date
  task_id int
}


Table User_task {
  task_id int [pk]
  emp_id int [pk]
}

Table User_subtask {
  subtask_id int [pk]
  emp_id int [pk]
}

ref: public.Organisation.organisationID < public.Project.organisationID
ref: public.Employee.empID <> public.Project.empID
ref: public.Project.projectID < public.Task.projectID
ref: public.Task.taskID < public.Subtask.taskID
ref: public.Organisation.organisationID < Employee.organisationID
ref: public.Project.empID < public.User_task.empID
ref: public.User_task.taskID - public.Task.taskID
ref: public.Project.empID < public.User_subtask.empID
ref: public.User_subtask.subtaskID - public.Subtask.taskID







