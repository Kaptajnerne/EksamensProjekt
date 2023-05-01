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
![Er diagram](https://user-images.githubusercontent.com/113116068/235443568-abb7e365-8e8a-4150-b255-5ddffb169621.png)


