ALTER TABLE `simulation_execution`
	CHANGE COLUMN `RunCount` `RunMultiplicator` INT(11) NOT NULL AFTER `EndDate`;

ALTER TABLE `simulation_configuration`
	CHANGE COLUMN `DefaultRunCount` `DefaultRunMultiplicator` INT(11) NOT NULL AFTER `Created`;
	