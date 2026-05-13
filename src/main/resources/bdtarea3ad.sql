-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-05-2026 a las 17:51:47
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bdtarea3ad`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `documento`
--

CREATE TABLE `documento` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `ruta` varchar(255) DEFAULT NULL,
  `estudiante_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `formacion_empresa`
--

CREATE TABLE `formacion_empresa` (
  `id` bigint(20) NOT NULL,
  `empresa` varchar(255) NOT NULL,
  `estado` enum('EN_CURSO','FINALIZADO','PENDIENTE') DEFAULT NULL,
  `fecha_fin` date NOT NULL,
  `fecha_inicio` date NOT NULL,
  `estudiante_id` bigint(20) NOT NULL,
  `tutor_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `formacion_empresa`
--

INSERT INTO `formacion_empresa` (`id`, `empresa`, `estado`, `fecha_fin`, `fecha_inicio`, `estudiante_id`, `tutor_id`) VALUES
(1, 'Mecalux', 'EN_CURSO', '2026-08-12', '2026-05-13', 1, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `apellidos` varchar(255) DEFAULT NULL,
  `ciclo` enum('DAM','DAW') DEFAULT NULL,
  `curso` enum('PRIMERO','SEGUNDO') DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `genero` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `perfil` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`id`, `apellidos`, `ciclo`, `curso`, `email`, `fecha_nacimiento`, `genero`, `nombre`, `password`, `perfil`, `telefono`) VALUES
(1, 'Zarauza ', 'DAM', 'PRIMERO', 'alvarozg10@educastur.es', '2005-02-19', 'Hombre', 'Alvaro', 'alvaro', 'ESTUDIANTE', '650954189'),
(2, 'de Blas', 'DAM', 'SEGUNDO', 'luisdbb@educastur.org', '1992-08-17', 'Hombre', 'Luis', 'luis', 'PROFESOR', '649306194'),
(3, 'Rodriguez', 'DAM', 'SEGUNDO', 'marta@gmail.com', '1998-11-14', 'Mujer', 'Marta', 'marta', 'TUTOR_EMPRESA', '620597210');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `documento`
--
ALTER TABLE `documento`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKixmmvpbdf7u5va0e5l2l292ya` (`estudiante_id`);

--
-- Indices de la tabla `formacion_empresa`
--
ALTER TABLE `formacion_empresa`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKn61ctm5rctaxc7wk9rfmwrcb5` (`estudiante_id`),
  ADD KEY `FKqfs3m08wbrqtjc33hh932ore8` (`tutor_id`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `documento`
--
ALTER TABLE `documento`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `formacion_empresa`
--
ALTER TABLE `formacion_empresa`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `documento`
--
ALTER TABLE `documento`
  ADD CONSTRAINT `FKixmmvpbdf7u5va0e5l2l292ya` FOREIGN KEY (`estudiante_id`) REFERENCES `user` (`id`);

--
-- Filtros para la tabla `formacion_empresa`
--
ALTER TABLE `formacion_empresa`
  ADD CONSTRAINT `FKn61ctm5rctaxc7wk9rfmwrcb5` FOREIGN KEY (`estudiante_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKqfs3m08wbrqtjc33hh932ore8` FOREIGN KEY (`tutor_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
