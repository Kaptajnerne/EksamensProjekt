<h2>ER diagram:</h2>

```SQL
Table Organisation {
  organisationID int [pk]
  organisationName varchar
  password varchar
}

Table Employee {
  empID int [pk]
  email varchar
  username varchar
  organisationID int
}

Table Project {
  projectID int [pk]
  projectName varchar
  estimateDeadline deadline
  empID int
  organisationID int
}

Table Task {
  taskID int [pk]
  taskName varchar
  startDate deadline
  endDate deadline
  projectID int
}

Table Subtask {
  subprojectID int [pk]
  taskName varchar
  startDate deadline
  endDate deadline
  taskID int
}


Table User_task {
  taskID int [pk]
  empID int [pk]
}

Table User_subtask {
  taskID int [pk]
  empID int [pk]
}

ref: public.Organisation.organisationID < public.Project.organisationID
ref: public.Employee.empID < public.Project.empID
ref: public.Project.projectID < public.Task.projectID
ref: public.Task.taskID < public.Subtask.taskID
ref: public.Organisation.organisationID < Employee.organisationID
ref: public.Project.empID < public.User_task.empID
ref: public.User_task.taskID - public.Task.taskID
ref: public.Project.empID < public.User_subtask.empID
ref: public.User_subtask.taskID - public.Subtask.taskID

```
![Er diagram](https://user-images.githubusercontent.com/113116068/235455153-4e5d4655-9691-44f8-897d-1530b8ccdb4f.png)




