# TimesheetBackend
 
This project was created during a fifteen-day internship for a software engineer in the 2nd year of the Faculty of Technical Sciences in Novi Sad. Developed by [@stefan](https://www.linkedin.com/in/stefanvlajkovic/)

## Getting started

To run this project you will need the following languages and libraries:
- [Java ](https://openjdk.org/) version 17
- [MySql](https://dev.mysql.com/doc/) version 8.0


The Easiest way to start the spring-boot app would be to open this project in some Java IDE and hit run. Then navigate to `http://localhost:8080/api` to test endpoints. You can also run frontend which I have written in this [ReactApp](https://github.com/Vlajkovic01/Timesheet-Frontend). 
To have some initial test data go to `application.properties` and check these two lines (**NOTE:** you will not have admin acc if you don't run this SQL script):
```
    spring.jpa.hibernate.ddl-auto = create-drop
    spring.sql.init.mode=always
    spring.jpa.defer-datasource-initialization=true
    
    //Demo passwords in this script all are 12345
```
Also, you can set your database parameters by editing the following lines in the same file
```
    spring.datasource.url=jdbc:mysql://localhost:3306/timesheet 
    spring.datasource.username= username
    spring.datasource.password= password
```
Also, you can set your Java email sender parameters by editing the following lines in the same file
```
    spring.mail.host=smtp.gmail.com
    spring.mail.port=587
    spring.mail.username= email
    spring.mail.password= password
    spring.mail.properties.mail.smtp.auth=true
    spring.mail.properties.mail.smtp.starttls.enable=true
```
## Features
- Login/Register with JWT
- Multiple user roles(Admin, Employee)
- CRUD for all entities in the system (Category, Client, Employee, Project, WorkLog)
- Employee registration with email confirmation
- Generation of reports for working hours on projects according to various parameters(by category, project, employee, client, date, quick date week, month...)
- Search all entities by a specific filter
- Pagination for all searches
