-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         12.2.2-MariaDB - MariaDB Server
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.14.0.7165
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para rentzone_db
CREATE DATABASE IF NOT EXISTS `rentzone_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci */;
USE `rentzone_db`;

-- Volcando estructura para tabla rentzone_db.alquileres
CREATE TABLE IF NOT EXISTS `alquileres` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_cliente` int(11) DEFAULT NULL,
  `id_producto` int(11) NOT NULL,
  `fecha_inicio` date DEFAULT NULL,
  `fecha_devolucion` date DEFAULT NULL,
  `costo` double NOT NULL DEFAULT 0,
  `estado` enum('ACTIVO','DEVUELTO','RETRASADO') DEFAULT 'ACTIVO',
  PRIMARY KEY (`id`),
  KEY `id` (`id`),
  KEY `FK_producto` (`id_producto`),
  KEY `FK_cliente` (`id_cliente`),
  CONSTRAINT `FK_cliente` FOREIGN KEY (`id_cliente`) REFERENCES `clientes` (`id_cuenta`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_producto` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci COMMENT='Almacena todos los alquileres activos y no activos';

-- Volcando datos para la tabla rentzone_db.alquileres: ~4 rows (aproximadamente)
REPLACE INTO `alquileres` (`id`, `id_cliente`, `id_producto`, `fecha_inicio`, `fecha_devolucion`, `costo`, `estado`) VALUES
	(1, 3, 3, '2026-04-19', '2026-04-22', 2400, 'DEVUELTO'),
	(2, 3, 3, '2026-04-19', '2026-04-21', 1600, 'DEVUELTO'),
	(3, 3, 3, '2026-04-20', '2026-04-21', 800, 'DEVUELTO'),
	(4, 3, 4, '2026-04-20', '2026-04-22', 0, 'ACTIVO');

-- Volcando estructura para tabla rentzone_db.clientes
CREATE TABLE IF NOT EXISTS `clientes` (
  `id_cuenta` int(11) NOT NULL,
  `saldo` double NOT NULL DEFAULT 0,
  `membresia` varchar(50) NOT NULL DEFAULT 'NORMAL',
  `fecha_membresia` date DEFAULT NULL,
  PRIMARY KEY (`id_cuenta`),
  KEY `id_cuenta` (`id_cuenta`),
  CONSTRAINT `FK_cuenta_cliente` FOREIGN KEY (`id_cuenta`) REFERENCES `cuentas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci COMMENT='Almacena los datos especificos de los clienets y la referencia de la llave foranea de los datos de su cuenta';

-- Volcando datos para la tabla rentzone_db.clientes: ~3 rows (aproximadamente)
REPLACE INTO `clientes` (`id_cuenta`, `saldo`, `membresia`, `fecha_membresia`) VALUES
	(1, 0, 'NORMAL', '2026-04-10'),
	(3, 24200, 'GOLD', '2026-04-09'),
	(10, 20000, 'NORMAL', '2026-04-19');

-- Volcando estructura para tabla rentzone_db.cuentas
CREATE TABLE IF NOT EXISTS `cuentas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `contrasenia` varchar(255) DEFAULT NULL,
  `correo` varchar(50) DEFAULT NULL,
  `telefono` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `correo` (`correo`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci COMMENT='Almacena los datos generales de las cuentas';

-- Volcando datos para la tabla rentzone_db.cuentas: ~5 rows (aproximadamente)
REPLACE INTO `cuentas` (`id`, `username`, `nombre`, `contrasenia`, `correo`, `telefono`) VALUES
	(1, 'JDDP0708', 'Juan David Diaz Perez', 'ab826bb1fd343d08a56fdd7b345514d601dadb8173c457bf268399db124efbbf', 'fdp2403@gmail.com', '3007888908'),
	(3, 'Manu', 'Juan Manuel Salazar Salazar', '383485b7ec3ffe51fdc0caf6cd03a28032301a3a06181778f8210f7cf8c39873', 'juanmasalazarsalazar@gmail.com', '3026625699'),
	(5, 'Admin', 'Administrador Principal', '3b612c75a7b5048a435fb6ec81e52ff92d6d795a8b5a9c17070f6a63c97a53b2', 'admin@rentzone.local', '000'),
	(10, 'Saris', 'Sara Valentina Quintero Lopez', '383485b7ec3ffe51fdc0caf6cd03a28032301a3a06181778f8210f7cf8c39873', 'lokay17365@spotshops.com', '3020000000'),
	(12, 'Sam2025', 'Samuel Betacourt', 'ebb2bf399abcd4fb2ac426a5c5cad82eae2b625a55bdb824831737add26179ee', 'valenjeronimo884@gmail.com', '3053446370');

-- Volcando estructura para tabla rentzone_db.empleados
CREATE TABLE IF NOT EXISTS `empleados` (
  `id_cuenta` int(11) NOT NULL DEFAULT 0,
  `dni` varchar(10) NOT NULL DEFAULT '0',
  `direccion` varchar(30) NOT NULL DEFAULT '0',
  `cargo` varchar(50) NOT NULL DEFAULT '0',
  `rol` enum('ADMIN','EMPLEADO') NOT NULL DEFAULT 'EMPLEADO',
  PRIMARY KEY (`id_cuenta`),
  KEY `id_cuenta` (`id_cuenta`),
  CONSTRAINT `FK_cuenta_empelado` FOREIGN KEY (`id_cuenta`) REFERENCES `cuentas` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci COMMENT='Tabla para almacenar los datos especificos de la cuenta de un empleado';

-- Volcando datos para la tabla rentzone_db.empleados: ~2 rows (aproximadamente)
REPLACE INTO `empleados` (`id_cuenta`, `dni`, `direccion`, `cargo`, `rol`) VALUES
	(5, '000000000', 'System Core', 'Administrador del Sistema', 'ADMIN'),
	(12, '1234567', 'Calle 65 Sur #68a - 68', 'Esclavo', 'EMPLEADO');

-- Volcando estructura para tabla rentzone_db.juegos
CREATE TABLE IF NOT EXISTS `juegos` (
  `id_producto` int(11) NOT NULL,
  `plataforma` varchar(50) DEFAULT ' ',
  `genero` varchar(50) DEFAULT ' ',
  PRIMARY KEY (`id_producto`),
  KEY `id_producto` (`id_producto`),
  CONSTRAINT `FK__productos` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- Volcando datos para la tabla rentzone_db.juegos: ~3 rows (aproximadamente)
REPLACE INTO `juegos` (`id_producto`, `plataforma`, `genero`) VALUES
	(1, 'Switch', 'Carreras'),
	(2, 'Play Station', 'Mundo Abierto'),
	(3, 'Pc', 'NovelGrafica');

-- Volcando estructura para tabla rentzone_db.peliculas
CREATE TABLE IF NOT EXISTS `peliculas` (
  `id_producto` int(11) NOT NULL,
  `formato` varchar(50) DEFAULT ' ',
  `duracion` varchar(20) DEFAULT ' ',
  PRIMARY KEY (`id_producto`),
  KEY `id_producto` (`id_producto`),
  CONSTRAINT `FK__productos` FOREIGN KEY (`id_producto`) REFERENCES `productos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- Volcando datos para la tabla rentzone_db.peliculas: ~5 rows (aproximadamente)
REPLACE INTO `peliculas` (`id_producto`, `formato`, `duracion`) VALUES
	(4, 'Stream', '101m'),
	(5, 'CD', '105m'),
	(6, 'DVD', '108m'),
	(7, 'DVD', '120 min'),
	(12, 'Blue-Ray', '101m');

-- Volcando estructura para tabla rentzone_db.productos
CREATE TABLE IF NOT EXISTS `productos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL DEFAULT '',
  `costo` double NOT NULL DEFAULT 0,
  `stock` int(11) NOT NULL DEFAULT 0,
  `alquilados` int(11) NOT NULL DEFAULT 0,
  `tipo_producto` enum('JUEGO','PELICULA') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- Volcando datos para la tabla rentzone_db.productos: ~8 rows (aproximadamente)
REPLACE INTO `productos` (`id`, `nombre`, `costo`, `stock`, `alquilados`, `tipo_producto`) VALUES
	(1, 'Mario Kart', 5000, 9, 0, 'JUEGO'),
	(2, 'GTA VI', 10000, 4, 0, 'JUEGO'),
	(3, 'Danganrompa', 1000, 3, 0, 'JUEGO'),
	(4, 'Son Como Niños II', 4000, 10, 1, 'PELICULA'),
	(5, 'Pixeles', 500, 4, 0, 'PELICULA'),
	(6, 'Click', 5000, 10, 0, 'JUEGO'),
	(7, 'Lalaland', 4000, 2, 0, 'PELICULA'),
	(12, 'Minecraft', 3000, 5, 0, 'PELICULA');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
