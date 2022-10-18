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

-- ---------------------------------

-- 
-- Estructura de tabla para la tabla `help`
-- 

CREATE TABLE `help`(
	`id` bigint NOT NULL AUTO_INCREMENT,
    `id_resolution` bigint NOT NULL,
    `id_developer` bigint NOT NULL,
    `percentage` double NOT NULL,
    PRIMARY KEY(id)
)ENGINE = InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ---------------------------------


--
-- Estructura de tabla para la tabla `task`
--

CREATE TABLE `task` (
  `id` bigint NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `id_project` bigint NOT NULL,
  `priority` int NOT NULL DEFAULT '0',
  `complexity` int NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `developer`
--


CREATE TABLE `developer` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
    `surname` VARCHAR(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
    `last_name` VARCHAR(255)CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
    `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
    `administrador` tinyint(1) NOT NULL
    PRIMARY KEY(id)
    )ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

    -- --------------------------------------------------------

INSERT INTO project (id,project_code, project_description, url) VALUES
(1,"aabb","Example1","https://example1/andamios.net"),
(2,"aaabbb","Example2","https://example2/andamios.net"),
(3,"ccdd","Example3","https://example3/andamios.net"),
(4,"cccddd","Example4","https://example4/andamios.net"),
(5,"eeff","Example5","https://example5/andamios.net"),
(6,"eeefff","Example6","https://example6/andamios.net"),
(7,"gghh","Example7","https://example7/andamios.net"),
(8,"ggghhh","Example8","https://example8/andamios.net"),
(9,"iijj","Example9","https://example9/andamios.net"),
(10,"iiijjj","Example10","https://example10/andamios.net");

INSERT INTO `help` (`id`, `id_resolution`, `id_developer`, `percentage`) VALUES 
(1, 1, 1, 10), 
(2, 5, 8, 8), 
(3, 3, 9, 0.1), 
(4, 9, 2, 0.5), 
(5, 7, 5, 5), 
(6, 6, 8, 15), 
(7, 2, 6, 12), 
(8, 8, 2, 3), 
(9, 4, 7, 1), 
(10, 10, 4, 10);


INSERT INTO `task` VALUES
(1, 'SQL db test',2,4,8),
(2, 'Inno db is cool',4,3,9),
(3, 'administrador SQL test',2,4,8),
(4, 'MongoDB',2,4,8),
(5, 'Hola mundo!',4,4,8),
(6, 'Adios Mundo!',6,3,8),
(7, 'Say hello!',9,5,8),
(8, 'My cat bigotillos',2,4,8),
(9, 'The mexican',9,9,8),
(10, 'Another one',1,1,1);

INSERT INTO `developer` (`name`, `surname`, `second_name`, `email`, `administrador`) VALUES 
('raimon', 'vilar', 'morera', 'test@email.com',  0), 
('alvaro', 'talaya', 'romance', 'test@email.com', 0), 
('mario', 'tomas', 'zanon', 'test@email.com', 0),
('aitana', 'collado', 'soler', 'test@email.com', 0), 
('carlos', 'merlos', 'pilar', 'test@email.com', 0), 
('luis', 'perez', 'derecho', 'test@email.com', 0), 
('estefania', 'boriko', 'izquierdo', 'test@email.com', 0),
('quique', 'aroca', 'garcia', 'test@email.com', 0), 
('adrian', 'duyang', 'liang', 'test@email.com', 0), 
('rafael', 'aznar', 'caballero', 'test@email.com', 1);
