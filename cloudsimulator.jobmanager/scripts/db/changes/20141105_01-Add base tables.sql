USE `jdeeco_simulation`;

CREATE TABLE IF NOT EXISTS `simulation_data` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `simulation_configuration`
ADD `DataID` int(11) NULL DEFAULT 0,
ADD CONSTRAINT `FK_SimulationConfiguration_SimulationData` FOREIGN KEY (`DataID`) REFERENCES `simulation_data` (`ID`);

CREATE TABLE IF NOT EXISTS `user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Email` varchar(255) NOT NULL,
  `Password` varchar(60) NOT NULL,
  `RegistrationDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UQ_User_Email` (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `user_simulation_configuration` (
  `UserID` int(11) NOT NULL,
  `ConfigurationID` int(11) NOT NULL,
  CONSTRAINT `FK_UserSimulationConfiguration_User` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`),
  CONSTRAINT `FK_UserSimulationConfiguration_SimulationConfiguration` FOREIGN KEY (`ConfigurationID`) REFERENCES `simulation_configuration` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- rename
ALTER TABLE `simulation_run` DROP FOREIGN KEY `FK_SimulationRun_SimulationConfiguration`;
DROP TABLE `simulation_run`;

CREATE TABLE IF NOT EXISTS `simulation_execution` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ConfigurationID` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  CONSTRAINT `FK_SimulationExecution_SimulationConfiguration` FOREIGN KEY (`ConfigurationID`) REFERENCES `simulation_configuration` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `simulation_run` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ExecutionID` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  CONSTRAINT `FK_SimulationRun_SimulationExecution` FOREIGN KEY (`ExecutionID`) REFERENCES `simulation_execution` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
