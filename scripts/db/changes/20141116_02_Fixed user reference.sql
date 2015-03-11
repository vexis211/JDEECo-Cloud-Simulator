USE `jdeeco_simulation`;


DROP TABLE IF EXISTS `user_simulation_configuration`;
DROP TABLE IF EXISTS `user_simulation_data`;


ALTER TABLE `simulation_configuration`
	ADD `UserID` int(11) NOT NULL,
	ADD CONSTRAINT `FK_SimulationConfiguration_User` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`);
	
ALTER TABLE `simulation_data`
	ADD `UserID` int(11) NOT NULL,
	ADD CONSTRAINT `FK_SimulationData_User` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`);
	
ALTER TABLE `simulation_execution`
	ADD `UserID` int(11) NOT NULL,
	ADD CONSTRAINT `FK_SimulationExecution_User` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`);