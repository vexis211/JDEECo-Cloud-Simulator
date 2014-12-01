
ALTER TABLE `simulation_configuration`
ADD `Name` varchar(255) NOT NULL;

CREATE TABLE `visible_project` (
	`UserID` INT(11) NOT NULL,
	`ProjectID` INT(11) NOT NULL,
  	PRIMARY KEY (`UserID`, `ProjectID`),
	CONSTRAINT `FK_VisibleProject_User` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`),
	CONSTRAINT `FK_VisibleProject_Project` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `visible_simulation_configuration` (
	`UserID` INT(11) NOT NULL,
	`ConfigurationID` INT(11) NOT NULL,
  	PRIMARY KEY (`UserID`, `ConfigurationID`),
	CONSTRAINT `FK_VisibleSimulationConfiguration_User` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`),
	CONSTRAINT `FK_VisibleSimulationConfiguration_SimulationConfiguration` FOREIGN KEY (`ConfigurationID`) REFERENCES `simulation_configuration` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `project` DROP `Visible`;

ALTER TABLE `project`
CHANGE COLUMN `UserID` `CreatorID` INT(11) NOT NULL;

ALTER TABLE `simulation_configuration`
CHANGE COLUMN `UserID` `CreatorID` INT(11) NOT NULL;

ALTER TABLE `simulation_data`
CHANGE COLUMN `UserID` `CreatorID` INT(11) NOT NULL;

ALTER TABLE `simulation_execution`
CHANGE COLUMN `UserID` `CreatorID` INT(11) NOT NULL;