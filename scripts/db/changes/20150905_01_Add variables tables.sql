CREATE TABLE `simulation_execution_variable` (
	`ID` INT NOT NULL AUTO_INCREMENT,
	`ExecutionID` INT NOT NULL,
	`Name` VARCHAR(255) NOT NULL,
	`DataType` TINYINT NOT NULL,
	PRIMARY KEY (`ID`),
	UNIQUE(`ExecutionID`, `Name`),
	INDEX `FK_SimulationExecutionVariable_SimulationExecution` (`ExecutionID`),
	CONSTRAINT `FK_SimulationExecutionVariable_SimulationExecution` FOREIGN KEY (`ExecutionID`) REFERENCES `simulation_execution` (`ID`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `simulation_run_variable` (
	`RunID` INT NOT NULL,
	`ExecutionVariableID` INT NOT NULL,
	`Data` VARBINARY(255) NOT NULL,
	PRIMARY KEY (`RunID`, `ExecutionVariableID`),
	INDEX `FK_SimulationRunVariable_SimulationRun` (`RunID`),
	INDEX `FK_SimulationRunVariable_SimulationExecutionVariable` (`ExecutionVariableID`),
	CONSTRAINT `FK_SimulationRunVariable_SimulationRun` FOREIGN KEY (`RunID`) REFERENCES `simulation_run` (`ID`),
	CONSTRAINT `FK_SimulationRunVariable_SimulationExecutionVariable` FOREIGN KEY (`ExecutionVariableID`) REFERENCES `simulation_execution_variable` (`ID`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;