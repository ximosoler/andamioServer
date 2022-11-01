-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 21-10-2022 a las 17:27:48
-- Versión del servidor: 10.4.25-MariaDB
-- Versión de PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Base de datos: `andamio`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `developer`
--

CREATE TABLE `developer` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `surname` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `last_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `id_usertype` bigint(20) NOT NULL,
  `id_team` bigint(20),
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `developer`
--

INSERT INTO `developer` (`id`, `name`, `surname`, `last_name`, `email`, `id_usertype`, `id_team`, `username`, `password`) VALUES
(1, 'raimon', 'vilar', 'morera', 'test@email.com', 1, 1, 'raivi', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6'),
(2, 'alvaro', 'talaya', 'romance', 'test@email.com', 3, 1, 'alta', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6'),
(3, 'mario', 'tomas', 'zanon', 'test@email.com', 3, 1, 'mato', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6'),
(4, 'aitana', 'collado', 'soler', 'test@email.com', 3, 1, 'aico', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6'),
(5, 'carlos', 'merlos', 'pilar', 'test@email.com', 3, 1, 'came', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6'),
(6, 'luis', 'perez', 'derecho', 'test@email.com', 3, 1, 'lupe', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6'),
(7, 'estefania', 'boriko', 'izquierdo', 'test@email.com', 3, 1, 'esbo', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6'),
(8, 'quique', 'aroca', 'garcia', 'test@email.com', 3, 1, 'quiga', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6'),
(9, 'adrian', 'duyang', 'liang', 'test@email.com', 3, 1, 'adu', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6'),
(10, 'rafael', 'aznar', 'aparici', 'test@email.com', 2, 1, 'raza', '73ec8dee81ea4c9e7515d63c9e5bbb707c7bc4789363c5afa401d3aa780630f6');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `help`
--

CREATE TABLE `help` (
  `id` bigint(20) NOT NULL,
  `id_resolution` bigint(20) NOT NULL,
  `id_developer` bigint(20) NOT NULL,
  `percentage` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `help`
--

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `issue`
--

CREATE TABLE `issue` (
  `id` bigint(20) NOT NULL,
  `open_datetime` datetime DEFAULT NULL,
  `id_task` bigint(20) NOT NULL,
  `id_developer` bigint(20) NOT NULL,
  `observations` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `value` int(2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `issue`
--

INSERT INTO `issue` (`id`, `open_datetime`, `id_task`, `id_developer`, `observations`, `value`) VALUES
(1, '2022-09-25 00:00:00', 2, 1, 'example observation 1', 6),
(2, '2022-09-10 00:00:00', 2, 2, 'example observation 2', 4),
(3, '2022-10-01 00:00:00', 2, 3, 'example observation 3', 1),
(4, '2022-10-06 00:00:00', 3, 4, 'example observation 4', 9),
(5, '2022-10-15 00:00:00', 4, 5, 'example observation 5', 5),
(6, '2022-10-25 00:00:00', 6, 6, 'example observation 6', 2),
(7, '2022-11-11 00:00:00', 3, 7, 'example observation 7', 0),
(8, '2022-11-25 00:00:00', 8, 8, 'example observation 8', 3),
(9, '2022-12-02 00:00:00', 9, 9, 'example observation 9', 2),
(10, '2022-12-15 00:00:00', 2, 10, 'example observation 10', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `project`
--

CREATE TABLE `project` (
  `id` bigint(20) NOT NULL,
  `project_code` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `project_description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `url` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `id_team` bigint(20)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `project`
--

INSERT INTO `project` (`id`, `project_code`, `project_description`, `url`, `id_team`) VALUES
(1, 'aabb', 'Example1', 'https://example1/andamios.net', 1),
(2, 'aaabbb', 'Example2', 'https://example2/andamios.net', 1),
(3, 'ccdd', 'Example3', 'https://example3/andamios.net', 1),
(4, 'cccddd', 'Example4', 'https://example4/andamios.net', 1),
(5, 'eeff', 'Example5', 'https://example5/andamios.net', 1),
(6, 'eeefff', 'Example6', 'https://example6/andamios.net', 1),
(7, 'gghh', 'Example7', 'https://example7/andamios.net', 1),
(8, 'ggghhh', 'Example8', 'https://example8/andamios.net', 1),
(9, 'iijj', 'Example9', 'https://example9/andamios.net', 1),
(10, 'iiijjj', 'Example10', 'https://example10/andamios.net', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `resolution`
--

CREATE TABLE `resolution` (
  `id` bigint(20) NOT NULL,
  `id_issue` bigint(20) NOT NULL,
  `observations` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `integration_turn` bigint(20) DEFAULT NULL,
  `integration_datetime` datetime DEFAULT NULL,
  `pullrequest_url` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `value` tinyint(4) DEFAULT NULL,
  `id_developer` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `resolution`
--

INSERT INTO `resolution` (`id`, `id_issue`, `observations`, `integration_turn`, `integration_datetime`, `pullrequest_url`, `value`, `id_developer`) VALUES
(1, 4, 'It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.', 1, '2022-09-10 20:13:33', 'https://www.example.com/', 4, 3),
(2, 7, 'It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.', 2, '2022-10-03 20:13:33', 'https://www.example.com/', 3, 4),
(3, 5, 'It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.', 3, '2022-11-03 20:13:33', 'https://www.example.com/', 5, 5),
(4, 1, 'It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.', 4, '2022-12-25 20:13:33', 'https://www.example.com/', 2, 6),
(5, 3, 'working in projects', 5, '2022-10-04 19:00:44', 'https://www.example.com/', 1, 7),
(6, 3, 'It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.', 9, NULL, NULL, 5, 8),
(7, 10, 'It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.', 6, '2023-02-03 20:13:33', 'https://www.example.com/', 2, 3),
(8, 2, 'It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.', 7, '2023-03-03 20:13:33', 'https://www.example.com/', 0, 4),
(9, 6, 'It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.', 8, '2023-04-03 20:13:33', 'https://www.example.com/', 3, 5),
(10, 8, 'It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.', 9, '2023-05-03 20:13:33', 'https://www.example.com/', 5, 6);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `task`
--

CREATE TABLE `task` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `id_project` bigint(20) NOT NULL,
  `priority` int(11) NOT NULL DEFAULT 0,
  `complexity` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `task`
--

INSERT INTO `task` (`id`, `description`, `id_project`, `priority`, `complexity`) VALUES
(1, 'SQL db test', 2, 4, 8),
(2, 'Inno db is cool', 4, 3, 9),
(3, 'administrador SQL test', 2, 4, 8),
(4, 'MongoDB', 2, 4, 8),
(5, 'Hola mundo!', 4, 4, 8),
(6, 'Adios Mundo!', 6, 3, 8),
(7, 'Say hello!', 9, 5, 8),
(8, 'My cat bigotillos', 2, 4, 8),
(9, 'The mexican', 9, 9, 8),
(10, 'Another one', 1, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `team`
--

CREATE TABLE `team` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `id_developer` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `team`
--

INSERT INTO `team` (`id`, `name`, `id_developer`) VALUES
(1, 'DAW2022-2023', 10);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usertype`
--

CREATE TABLE `usertype` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Volcado de datos para la tabla `usertype`
--

INSERT INTO `usertype` (`id`, `name`) VALUES
(1, 'Administrator'),
(2, 'Reviewer'),
(3, 'Developer');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `developer`
--
ALTER TABLE `developer`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `help`
--
ALTER TABLE `help`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `issue`
--
ALTER TABLE `issue`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `project`
--
ALTER TABLE `project`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `resolution`
--
ALTER TABLE `resolution`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `task`
--
ALTER TABLE `task`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `team`
--
ALTER TABLE `team`
  ADD PRIMARY KEY (`id`);
--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `developer`
--
ALTER TABLE `developer`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `help`
--
ALTER TABLE `help`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `team`
--
ALTER TABLE `team`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `issue`
--
ALTER TABLE `issue`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `project`
--
ALTER TABLE `project`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `resolution`
--
ALTER TABLE `resolution`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `task`
--
ALTER TABLE `task`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;
