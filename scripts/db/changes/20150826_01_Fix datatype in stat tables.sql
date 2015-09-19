ALTER TABLE `simulation_run_statistic`
	DROP COLUMN `DataType`;
	
ALTER TABLE `simulation_execution_statistic`
	ADD COLUMN `DataType` TINYINT NOT NULL;