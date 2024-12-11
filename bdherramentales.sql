-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 12-12-2024 a las 00:26:23
-- Versión del servidor: 10.4.27-MariaDB
-- Versión de PHP: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `bdherramentales`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `encargado`
--

CREATE TABLE `encargado` (
  `id_emptE` varchar(16) NOT NULL,
  `num_emp` varchar(8) NOT NULL,
  `nombre` text NOT NULL,
  `linea` text NOT NULL,
  `turno` int(1) NOT NULL,
  `contrasena` text NOT NULL,
  `estado` int(1) NOT NULL COMMENT '1:bisponible, 0:bloqueado',
  `tipo_cuenta` int(1) NOT NULL COMMENT '0:Admin, 1:Encargado con manipulacion de htal, 2: Encargado, entrada salida'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `encargado`
--

INSERT INTO `encargado` (`id_emptE`, `num_emp`, `nombre`, `linea`, `turno`, `contrasena`, `estado`, `tipo_cuenta`) VALUES
('0000000000000000', 'a1001010', 'CUENT AX', '14', 4, '77ba59f87d7eaa6846c0eb3d5dfe8d5410bbd76f5581f4e191a52318994cd2ba', 1, 2),
('1000000000000000', 'a1000000', 'OP DE PRUEBA', '8', 1, '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, 1),
('1ff87e9bdd58c681', 'A1005706', 'HORTENCIA HERNANDEZ ', '2', 1, '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, 1),
('1ffbfef7b946c2cb', 'A1075737', 'CARLOS TORRES', '13', 1, '77ba59f87d7eaa6846c0eb3d5dfe8d5410bbd76f5581f4e191a52318994cd2ba', 1, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `heramental`
--

CREATE TABLE `heramental` (
  `id_herramental` varchar(16) NOT NULL,
  `nombre` text NOT NULL,
  `observaciones` text NOT NULL,
  `vieneta` varchar(10) NOT NULL,
  `estado` tinyint(1) NOT NULL COMMENT 'Cuando estado esta en 1 el herramental esta disponible',
  `linea` int(3) NOT NULL,
  `reporte` int(1) NOT NULL COMMENT '0-No tiene reporte, y se puede prestar\r\n1-Tiene reporte y no se puede prestar\r\n2-Se desactiva de manera permanente\r\n'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `heramental`
--

INSERT INTO `heramental` (`id_herramental`, `nombre`, `observaciones`, `vieneta`, `estado`, `linea`, `reporte`) VALUES
('AFT-1', 'Automatic Funtional Test 1', 'El Automatic Functional Test (AFT) es una solución innovadora diseñada para mejorar la eficiencia y precisión en el ciclo de pruebas de software.', 'NA', 1, 10, 1)


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lineas`
--

CREATE TABLE `lineas` (
  `id_linea` int(11) NOT NULL,
  `nombre` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `lineas`
--

INSERT INTO `lineas` (`id_linea`, `nombre`) VALUES
(1, 'BECARIO'),


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `operario`
--

CREATE TABLE `operario` (
  `id_empt` varchar(16) NOT NULL,
  `num_emp` varchar(8) DEFAULT NULL,
  `nombre` text DEFAULT NULL,
  `linea` int(3) DEFAULT NULL,
  `turno` int(2) DEFAULT NULL,
  `observaciones` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `operario`
--

INSERT INTO `operario` (`id_empt`, `num_emp`, `nombre`, `linea`, `turno`, `observaciones`) VALUES
('0000000000000000', 'a1001010', 'CUENT AX', 14, 4, 'Cambio'),
('1000000000000000', 'a1000000', 'OP DE PRUEBA', 8, 1, ''),
('1010101010101010', 'a1010101', 'NUEVA CUENTA', 1, 4, 'cuenta de manejo de htal'),

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `registro`
--

CREATE TABLE `registro` (
  `id_registro` int(11) NOT NULL,
  `id_htal` varchar(16) NOT NULL,
  `fecha_prestamo` datetime NOT NULL,
  `fecha_entrega` datetime NOT NULL,
  `num_encargado` varchar(8) NOT NULL,
  `num_encargado_r` varchar(8) NOT NULL,
  `id_operario` varchar(8) NOT NULL,
  `id_empt` varchar(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `registro`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reportes`
--

CREATE TABLE `reportes` (
  `id_reporte` int(11) NOT NULL COMMENT 'Esta asociado al id del registro que se esta reportando ',
  `estado` int(1) NOT NULL COMMENT 'En caso de 1 esta abierto el reporte',
  `id_encargado` varchar(8) NOT NULL,
  `id_htal` varchar(16) NOT NULL,
  `id_op` varchar(8) NOT NULL,
  `tipo_reporte` int(2) NOT NULL COMMENT '0-Sin reporte\r\n1-Otro\r\n2-Extravio o perdida\r\n3-Falla o daño\r\n4-Mantenimiento o reparación',
  `problematica` text NOT NULL,
  `solucion` text DEFAULT NULL,
  `fecha_abierto` datetime NOT NULL,
  `fecha_cierre` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reportes`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tem_registro`
--

CREATE TABLE `tem_registro` (
  `id_registro` int(11) NOT NULL,
  `id_htal` varchar(16) NOT NULL,
  `fecha_prestamo` datetime NOT NULL,
  `num_encargado` varchar(8) NOT NULL,
  `num_emp` varchar(8) NOT NULL,
  `id_empt` varchar(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tem_registro`
--

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `encargado`
--
ALTER TABLE `encargado`
  ADD PRIMARY KEY (`id_emptE`),
  ADD UNIQUE KEY `Numero de empleado` (`num_emp`);

--
-- Indices de la tabla `heramental`
--
ALTER TABLE `heramental`
  ADD PRIMARY KEY (`id_herramental`);

--
-- Indices de la tabla `lineas`
--
ALTER TABLE `lineas`
  ADD PRIMARY KEY (`id_linea`);

--
-- Indices de la tabla `operario`
--
ALTER TABLE `operario`
  ADD PRIMARY KEY (`id_empt`);

--
-- Indices de la tabla `registro`
--
ALTER TABLE `registro`
  ADD PRIMARY KEY (`id_registro`);

--
-- Indices de la tabla `reportes`
--
ALTER TABLE `reportes`
  ADD PRIMARY KEY (`id_reporte`);

--
-- Indices de la tabla `tem_registro`
--
ALTER TABLE `tem_registro`
  ADD PRIMARY KEY (`id_registro`),
  ADD UNIQUE KEY `id_htal` (`id_htal`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `lineas`
--
ALTER TABLE `lineas`
  MODIFY `id_linea` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT de la tabla `tem_registro`
--
ALTER TABLE `tem_registro`
  MODIFY `id_registro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=136;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
