create database andamio;

use andamio;
--
-- Estructura de tabla para la tabla `project`
--
CREATE TABLE `project` (
`id` bigint auto_increment NOT NULL,
`project_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
`project_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
`url` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
PRIMARY KEY (id)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


insert into project (id,project_code, project_description, url) values
(1,"aabb","Example1","https://example1/andamios.net");
insert into project (id,project_code, project_description, url) values
(2,"aaabbb","Example2","https://example2/andamios.net");
insert into project (id,project_code, project_description, url) values
(3,"ccdd","Example3","https://example3/andamios.net");
insert into project (id,project_code, project_description, url) values
(4,"cccddd","Example4","https://example4/andamios.net");
insert into project (id,project_code, project_description, url) values
(5,"eeff","Example5","https://example5/andamios.net");
insert into project (id,project_code, project_description, url) values
(6,"eeefff","Example6","https://example6/andamios.net");
insert into project (id,project_code, project_description, url) values
(7,"gghh","Example7","https://example7/andamios.net");
insert into project (id,project_code, project_description, url) values
(8,"ggghhh","Example8","https://example8/andamios.net");
insert into project (id,project_code, project_description, url) values
(9,"iijj","Example9","https://example9/andamios.net");
insert into project (id,project_code, project_description, url) values
(10,"iiijjj","Example10","https://example10/andamios.net");

