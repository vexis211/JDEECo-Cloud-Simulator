ALTER TABLE `simulation_data`
ADD `ProjectID` int(11) NOT NULL AFTER `CreatorID`,
ADD CONSTRAINT `FK_SimulationData_Project` FOREIGN KEY (`ProjectID`) REFERENCES `project` (`ID`);
