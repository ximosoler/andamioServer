create database andamio;

use andamio;

create table project (
    id int auto_increment,
    project_code varchar(255),
    project_description varchar(255),
    url varchar(255),
    primary key (id)
);


insert into project (project_code, project_description, url) values
    ("aabb","Example1","https://example1/andamios.net");
insert into project (project_code, project_description, url) values
    ("aaabbb","Example2","https://example2/andamios.net");
insert into project (project_code, project_description, url) values
    ("ccdd","Example3","https://example3/andamios.net");
insert into project (project_code, project_description, url) values
    ("cccddd","Example4","https://example4/andamios.net");
insert into project (project_code, project_description, url) values
    ("eeff","Example5","https://example5/andamios.net");
insert into project (project_code, project_description, url) values
    ("eeefff","Example6","https://example6/andamios.net");
insert into project (project_code, project_description, url) values
    ("gghh","Example7","https://example7/andamios.net");
insert into project (project_code, project_description, url) values
    ("ggghhh","Example8","https://example8/andamios.net");
insert into project (project_code, project_description, url) values
    ("iijj","Example9","https://example9/andamios.net");
insert into project (project_code, project_description, url) values
    ("iiijjj","Example10","https://example10/andamios.net");

