-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.17 - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             8.3.0.4833
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping database structure for jdeeco_simulation
CREATE DATABASE IF NOT EXISTS `jdeeco_simulation` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `jdeeco_simulation`;


-- Dumping structure for table jdeeco_simulation.simulation_configuration
CREATE TABLE IF NOT EXISTS `simulation_configuration` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.


-- Dumping structure for table jdeeco_simulation.simulation_run
CREATE TABLE IF NOT EXISTS `simulation_run` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ConfigurationID` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `FK_SimulationRun_SimulationConfiguration` (`ConfigurationID`),
  CONSTRAINT `FK_SimulationRun_SimulationConfiguration` FOREIGN KEY (`ConfigurationID`) REFERENCES `simulation_configuration` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
