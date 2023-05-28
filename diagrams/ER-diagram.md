<h2>ER diagram:</h2>

```SQL

Table User {
  user_id int [pk]
  username varchar
  password varchar 
}

Table Project {
  project_id int [pk]
  project_name varchar
  project_description varchar
  start_date date
  end_Date date
  user_id int
}

Table Task {
  task_id int [pk]
  task_name varchar
  hours double
  start_date date
  end_Date date
  status int
  project_id int
}
Table Subtask {
  subtask_id int [pk]
  subtask_name varchar
  hours double
  start_date date
  end_Date date
  status int
  task_id int
}

ref: public.User.user_id < public.Project.user_id
ref: public.Project.project_id < public.Task.project_id
ref: public.Task.task_id < public.Subtask.task_id

```

![ER diagram](https://github.com/Kaptajnerne/EksamensProjekt/assets/113116068/d2722522-0ac3-4ca8-8170-b9f14cef0ae9)
