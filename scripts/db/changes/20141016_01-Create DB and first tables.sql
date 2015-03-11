CREATE DATABASE IF NOT EXISTS `jdeeco_simulation` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `jdeeco_simulation`;


CREATE TABLE IF NOT EXISTS `simulation_configuration` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `simulation_run` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ConfigurationID` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `FK_SimulationRun_SimulationConfiguration` (`ConfigurationID`),
  CONSTRAINT `FK_SimulationRun_SimulationConfiguration` FOREIGN KEY (`ConfigurationID`) REFERENCES `simulation_configuration` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
