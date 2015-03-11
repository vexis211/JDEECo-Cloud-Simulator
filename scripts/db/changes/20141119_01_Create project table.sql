USE `jdeeco_simulation`;

CREATE TABLE `project` (
	`ID` INT(11) NOT NULL AUTO_INCREMENT,
	`UserID` INT(11) NOT NULL,
	PRIMARY KEY (`ID`),
	CONSTRAINT `FK_Project_User` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `simulation_configuration`
ADD `ProjectID` int(11) NOT NULL,
ADD CONSTRAINT `FK_SimulationConfiguration_Project` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ID`);
