CREATE TABLE `user_simulation_data` (
	`UserID` INT(11) NOT NULL,
	`DataID` INT(11) NOT NULL,
	INDEX `FK_UserSimulationData_User` (`UserID`),
	INDEX `FK_UserSimulationData_SimulationData` (`DataID`),
	CONSTRAINT `FK_UserSimulationData_SimulationData` FOREIGN KEY (`DataID`) REFERENCES `simulation_data` (`ID`),
	CONSTRAINT `FK_UserSimulationData_User` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
