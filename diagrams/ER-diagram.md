<h2>ER diagram:</h2>

```SQL

Table User {
  userID int [pk]
  email varchar [pk]
  username varchar
  password varchar
}

Table Project {
  projectID [pk]
  projectName varchar
  estimateDeadline deadline
  userID int
}

Table Tasks {
  taskID int [pk]
  taskName varchar
  startDate deadline
  endDate deadline
  projectID deadline
}

Table Subproject {
  subprojectID int [pk]
  taskName varchar
  startDate deadline
  endDate deadline
  projectID deadline
}

Table Subprojecttask {
  subprojecttaskID int [pk]
  taskName varchar
  tartDate deadline
  endDate deadline
  subprojectID int
}

ref: public.User.userID - public.Project.userID
ref: public.Project.projectID < public.Tasks.projectID
ref: public.Project.projectID < public.Subproject.projectID
ref: public.Subproject.subprojectID < public.Subprojecttask.subprojectID

```
![ER diagram](https://user-images.githubusercontent.com/113116068/235443386-54c5faa1-9dd7-4c7c-80fd-da8a90222036.png)

