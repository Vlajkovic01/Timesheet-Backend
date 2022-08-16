select * from employee;
select * from report;
select * from project;
select * from client;
select * from country;
select * from category;

insert into employee(name, username, password, email, status, role) 
	values ('Stefan Vlajkovic', 'stefan', '$2a$04$Bh0cdJ19RbVcJ1LEEtY8Bu2shfhH5Vkm/xVa.DdozXuMUt8tOCXCW', 'vlajkovicstefan01@gmail.com', 'ACTIVE', 'ADMIN');    
insert into employee(name, username, password, email, status, role) 
	values ('Nikola Nikolic', 'nikola', '$2a$04$2fft5WW4rqrPyT60YLcIe.vQHZd.bI5JpSrCsKwYoNBj3SYcIomqu', 'nikolanikolic01@gmail.com', 'ACTIVE', 'WORKER');
insert into employee(name, username, password, email, status, role) 
	values ('Petar Petrovic', 'petar', '$2a$04$J8Wxff0jw8yiA.YyGhB3ROUW/dAWYSO2E9Hw23s9byq7/H03RsfZy', 'nikolanikolic01@gmail.com', 'ACTIVE', 'WORKER');
    
insert into country (name) values ('California');    
insert into country (name) values ('Serbia');    
insert into country (name) values ('France');    
insert into country (name) values ('Russia');
    
insert into category (name) values ('Ui');    
insert into category (name) values ('UX');    
insert into category (name) values ('Backend');    
insert into category (name) values ('Security');    
    
insert into client (name, address, city, zip, country_id) 
	values ('Apple', '1 infinitive loop', 'Cupertino', 95014, 1);
    
insert into client (name, address, city, zip, country_id) 
	values ('Vega', 'Novosadskog Sajma 2', 'Novi Sad', 21000, 2);
    
insert into project (name, description, status, employee_id, client_id)
	values ('Test Project 1', 'Description for test project 1', 'ACTIVE', 2, 1);
insert into project (name, description, status, employee_id, client_id)
	values ('Test Project 2', 'Description for test project 2', 'ACTIVE', 2, 2);
    
insert into report(date, description, hours, overtime, client_id, project_id, employee_id, category_id)
	values ('2022-08-10', 'Test description for report 1', 7.5, 0, 1, 1, 2, 3);
    
insert into report(date, description, hours, overtime, client_id, project_id, employee_id, category_id)
	values ('2022-08-13', 'Test description for report 2', 7.0, 0, 2, 2, 2, 4);
    