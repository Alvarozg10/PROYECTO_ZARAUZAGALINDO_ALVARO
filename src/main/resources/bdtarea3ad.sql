-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 12-02-2026 a las 12:06:07
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
  `id_documento` bigint(20) NOT NULL,
  `estado` enum('EN_CURSO','FINALIZADO','PENDIENTE') DEFAULT NULL,
  `fecha_subida` date DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  `ruta` varchar(255) DEFAULT NULL,
  `tipo` enum('CONTRATO','INFORME','MEMORIA','OTRO') DEFAULT NULL,
  `formacion_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresa`
--

CREATE TABLE `empresa` (
  `id` bigint(20) NOT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `empresa`
--

INSERT INTO `empresa` (`id`, `direccion`, `nombre`, `telefono`) VALUES
(1, 'Calle Innovación 12', 'Mecalux', '987654321');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `formacion_empresa`
--

CREATE TABLE `formacion_empresa` (
  `id_formacion` bigint(20) NOT NULL,
  `fecha_inicio` date DEFAULT NULL,
  `fecha_fin` date DEFAULT NULL,
  `estado` varchar(255) DEFAULT NULL,
  `estudiante_id` bigint(20) DEFAULT NULL,
  `profesor_id` bigint(20) DEFAULT NULL,
  `tutor_empresa_id` bigint(20) DEFAULT NULL,
  `empresa_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `formacion_empresa`
--

INSERT INTO `formacion_empresa` (`id_formacion`, `fecha_inicio`, `fecha_fin`, `estado`, `estudiante_id`, `profesor_id`, `tutor_empresa_id`, `empresa_id`) VALUES
(2, '2026-01-25', '2026-05-15', 'ACTIVA', 3, 5, 4, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `dtype` varchar(31) NOT NULL,
  `apellidos` varchar(255) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `genero` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `rol` varchar(255) DEFAULT NULL,
  `ciclo` varchar(255) DEFAULT NULL,
  `curso` int(11) DEFAULT NULL,
  `expediente` varchar(255) DEFAULT NULL,
  `especialidad` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `empresa_id` bigint(20) DEFAULT NULL,
  `tutor_empresa_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `user`
--

INSERT INTO `user` (`id`, `email`, `password`, `dtype`, `apellidos`, `fecha_nacimiento`, `genero`, `nombre`, `rol`, `ciclo`, `curso`, `expediente`, `especialidad`, `telefono`, `empresa_id`, `tutor_empresa_id`) VALUES
(3, 'estudiante@demo.com', '1234', 'Estudiante', 'Zarauza Galindo', NULL, NULL, 'Álvaro', 'Estudiante', NULL, 1, NULL, NULL, NULL, 1, 4),
(4, 'tutor@demo.com', '1234', 'TutorEmpresa', NULL, NULL, NULL, 'María', 'Tutor de empresa', NULL, NULL, NULL, NULL, '600123456', NULL, NULL),
(5, 'profesor@demo.com', '1234', 'Profesor', NULL, NULL, NULL, 'Luis', 'Profesor', NULL, NULL, NULL, 'Informática', NULL, NULL, NULL),
(6, 'admin@demo.com', 'admin', 'Administrador', NULL, NULL, NULL, 'Administrador', 'Administrador', NULL, NULL, NULL, NULL, NULL, NULL, NULL);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `documento`
--
ALTER TABLE `documento`
  ADD PRIMARY KEY (`id_documento`),
  ADD KEY `FKjbdnufhx516lmr7nfac3auque` (`formacion_id`);

--
-- Indices de la tabla `empresa`
--
ALTER TABLE `empresa`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `formacion_empresa`
--
ALTER TABLE `formacion_empresa`
  ADD PRIMARY KEY (`id_formacion`),
  ADD KEY `FKkg87u9i7xyof1ytqo88kxq3n1` (`empresa_id`),
  ADD KEY `FKn61ctm5rctaxc7wk9rfmwrcb5` (`estudiante_id`),
  ADD KEY `FKa5xxccmd7b6abyggy3vf5e4tg` (`profesor_id`),
  ADD KEY `FKao3p37k2yhkdh345v5tdg808m` (`tutor_empresa_id`);

--
-- Indices de la tabla `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`),
  ADD KEY `FKm21n0rvmqditlvvor5ew330gr` (`empresa_id`),
  ADD KEY `FKs2tulds3s360io0aoevij65iv` (`tutor_empresa_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `documento`
--
ALTER TABLE `documento`
  MODIFY `id_documento` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `empresa`
--
ALTER TABLE `empresa`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `formacion_empresa`
--
ALTER TABLE `formacion_empresa`
  MODIFY `id_formacion` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `documento`
--
ALTER TABLE `documento`
  ADD CONSTRAINT `FKjbdnufhx516lmr7nfac3auque` FOREIGN KEY (`formacion_id`) REFERENCES `formacion_empresa` (`id_formacion`);

--
-- Filtros para la tabla `formacion_empresa`
--
ALTER TABLE `formacion_empresa`
  ADD CONSTRAINT `FKa5xxccmd7b6abyggy3vf5e4tg` FOREIGN KEY (`profesor_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKao3p37k2yhkdh345v5tdg808m` FOREIGN KEY (`tutor_empresa_id`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FKkg87u9i7xyof1ytqo88kxq3n1` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`id`),
  ADD CONSTRAINT `FKn61ctm5rctaxc7wk9rfmwrcb5` FOREIGN KEY (`estudiante_id`) REFERENCES `user` (`id`);

--
-- Filtros para la tabla `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FKm21n0rvmqditlvvor5ew330gr` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`id`),
  ADD CONSTRAINT `FKs2tulds3s360io0aoevij65iv` FOREIGN KEY (`tutor_empresa_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
