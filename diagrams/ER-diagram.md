<h2>ER diagram:</h2>

```SQL

Table User {
  userID int [pk]
  email varchar
  username varchar
  password varchar
}

Table Project {
  projectID int [pk]
  projectName varchar
  estimateDeadline deadline
  userID int
}

Table Tasks {
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


ref: public.User.userID < public.Project.userID
ref: public.Project.projectID < public.Tasks.projectID
ref: public.Tasks.taskID < public.Subtask.taskID

```
![ER diagram](https://user-images.githubusercontent.com/113116068/235444121-1a2ef191-b79b-4038-b75a-bd8ece783fcc.png)



