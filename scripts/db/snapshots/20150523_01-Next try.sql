-- --------------------------------------------------------
-- Hostitel:                     127.0.0.1
-- Verze serveru:                5.6.17 - MySQL Community Server (GPL)
-- OS serveru:                   Win64
-- HeidiSQL Verze:               9.2.0.4947
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Exportování struktury databáze pro
DROP DATABASE IF EXISTS `jdeeco_simulation`;
CREATE DATABASE IF NOT EXISTS `jdeeco_simulation` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `jdeeco_simulation`;


-- Exportování struktury pro tabulka jdeeco_simulation.authentication_token
CREATE TABLE IF NOT EXISTS `authentication_token` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Value` varchar(255) NOT NULL,
  `Owner` int(11) NOT NULL,
  `Created` datetime NOT NULL,
  `Expiry` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_AuthenticationToken_User` (`Owner`),
  CONSTRAINT `FK_AuthenticationToken_User` FOREIGN KEY (`Owner`) REFERENCES `user` (`ID`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Export dat nebyl vybrán.


-- Exportování struktury pro tabulka jdeeco_simulation.project
CREATE TABLE IF NOT EXISTS `project` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CreatorID` int(11) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Description` varchar(1000) NOT NULL DEFAULT '',
  `Created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `FK_Project_User` (`CreatorID`),
  CONSTRAINT `FK_Project_User` FOREIGN KEY (`CreatorID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Export dat nebyl vybrán.


-- Exportování struktury pro tabulka jdeeco_simulation.simulation_configuration
CREATE TABLE IF NOT EXISTS `simulation_configuration` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `DataID` int(11) DEFAULT '0',
  `CreatorID` int(11) NOT NULL,
  `ProjectID` int(11) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Description` varchar(1000) NOT NULL DEFAULT '',
  `Created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `DefaultRunCount` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_SimulationConfiguration_SimulationData` (`DataID`),
  KEY `FK_SimulationConfiguration_User` (`CreatorID`),
  KEY `FK_SimulationConfiguration_Project` (`ProjectID`),
  CONSTRAINT `FK_SimulationConfiguration_Project` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ID`),
  CONSTRAINT `FK_SimulationConfiguration_SimulationData` FOREIGN KEY (`DataID`) REFERENCES `simulation_data` (`ID`),
  CONSTRAINT `FK_SimulationConfiguration_User` FOREIGN KEY (`CreatorID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Export dat nebyl vybrán.


-- Exportování struktury pro tabulka jdeeco_simulation.simulation_data
CREATE TABLE IF NOT EXISTS `simulation_data` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CreatorID` int(11) NOT NULL,
  `ProjectID` int(11) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Description` varchar(1000) NOT NULL DEFAULT '',
  `Created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `VCSType` int(4) NOT NULL,
  `RepositoryURL` varchar(2000) NOT NULL,
  `PomDirectory` varchar(256) NOT NULL,
  `MavenGoals` varchar(1000) NOT NULL,
  `StartupFile` varchar(256) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_SimulationData_User` (`CreatorID`),
  KEY `FK_SimulationData_Project` (`ProjectID`),
  CONSTRAINT `FK_SimulationData_Project` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ID`),
  CONSTRAINT `FK_SimulationData_User` FOREIGN KEY (`CreatorID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Export dat nebyl vybrán.


-- Exportování struktury pro tabulka jdeeco_simulation.simulation_execution
CREATE TABLE IF NOT EXISTS `simulation_execution` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ConfigurationID` int(11) NOT NULL DEFAULT '0',
  `CreatorID` int(11) NOT NULL,
  `Description` varchar(1000) NOT NULL DEFAULT '',
  `Status` int(4) NOT NULL,
  `Created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `Started` timestamp NULL DEFAULT NULL,
  `Ended` timestamp NULL DEFAULT NULL,
  `EndSpecificationType` int(4) NOT NULL,
  `EndDate` timestamp NULL DEFAULT NULL,
  `RunCount` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_SimulationExecution_SimulationConfiguration` (`ConfigurationID`),
  KEY `FK_SimulationExecution_User` (`CreatorID`),
  CONSTRAINT `FK_SimulationExecution_SimulationConfiguration` FOREIGN KEY (`ConfigurationID`) REFERENCES `simulation_configuration` (`ID`),
  CONSTRAINT `FK_SimulationExecution_User` FOREIGN KEY (`CreatorID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Export dat nebyl vybrán.


-- Exportování struktury pro tabulka jdeeco_simulation.simulation_run
CREATE TABLE IF NOT EXISTS `simulation_run` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ExecutionID` int(11) NOT NULL DEFAULT '0',
  `Status` int(4) NOT NULL,
  `Started` timestamp NULL DEFAULT NULL,
  `Ended` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FK_SimulationRun_SimulationExecution` (`ExecutionID`),
  CONSTRAINT `FK_SimulationRun_SimulationExecution` FOREIGN KEY (`ExecutionID`) REFERENCES `simulation_execution` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Export dat nebyl vybrán.


-- Exportování struktury pro tabulka jdeeco_simulation.user
CREATE TABLE IF NOT EXISTS `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Email` varchar(255) NOT NULL,
  `Password` varchar(60) NOT NULL,
  `Role` int(11) NOT NULL,
  `RegistrationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `LastActivityDate` timestamp NULL DEFAULT NULL,
  `ActivationState` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UQ_User_Email` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Export dat nebyl vybrán.


-- Exportování struktury pro tabulka jdeeco_simulation.user_info
CREATE TABLE IF NOT EXISTS `user_info` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `UserID` int(11) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Value` text,
  PRIMARY KEY (`ID`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `FK_UserInfo_User` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Export dat nebyl vybrán.


-- Exportování struktury pro tabulka jdeeco_simulation.visible_project
CREATE TABLE IF NOT EXISTS `visible_project` (
  `UserID` int(11) NOT NULL,
  `ProjectID` int(11) NOT NULL,
  PRIMARY KEY (`UserID`,`ProjectID`),
  KEY `FK_VisibleProject_Project` (`ProjectID`),
  CONSTRAINT `FK_VisibleProject_Project` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ID`),
  CONSTRAINT `FK_VisibleProject_User` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Export dat nebyl vybrán.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
