CREATE TABLE `simulation_execution_statistic` (
	`ID` INT NOT NULL AUTO_INCREMENT,
	`ExecutionID` INT NOT NULL,
	`Name` VARCHAR(255) NOT NULL,
	PRIMARY KEY (`ID`),
	UNIQUE(`ExecutionID`, `Name`),
	INDEX `FK_SimulationExecutionStatistic_SimulationExecution` (`ExecutionID`),
	CONSTRAINT `FK_SimulationExecutionStatistic_SimulationExecution` FOREIGN KEY (`ExecutionID`) REFERENCES `simulation_execution` (`ID`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `simulation_run_statistic` (
	`ID` BIGINT NOT NULL AUTO_INCREMENT,
	`RunID` INT NOT NULL,
	`ExecutionStatisticID` INT NOT NULL,
	`DataType` TINYINT NOT NULL,
	`VectorData` BLOB,
	PRIMARY KEY (`ID`),
	INDEX `FK_SimulationRunStatistic_SimulationRun` (`RunID`),
	INDEX `FK_SimulationRunStatistic_SimulationExecutionStatistic` (`ExecutionStatisticID`),
	CONSTRAINT `FK_SimulationRunStatistic_SimulationRun` FOREIGN KEY (`RunID`) REFERENCES `simulation_run` (`ID`),
	CONSTRAINT `FK_SimulationRunStatistic_SimulationExecutionStatistic` FOREIGN KEY (`ExecutionStatisticID`) REFERENCES `simulation_execution_statistic` (`ID`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;

CREATE TABLE `simulation_run_statistic_aggdata` (
	`RunStatisticID` BIGINT NOT NULL,
	`SaveType` TINYINT NOT NULL,
	`Data` VARBINARY(8) NOT NULL,
	PRIMARY KEY (`RunStatisticID`, `SaveType`),
	INDEX `FK_SimulationRunStatisticAggdata_SimulationRunStatistic` (`RunStatisticID`),
	CONSTRAINT `FK_SimulationRunStatisticAggdata_SimulationRunStatistic` FOREIGN KEY (`RunStatisticID`) REFERENCES `simulation_run_statistic` (`ID`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB
;