ALTER TABLE `simulation_configuration`
ADD COLUMN `DefaultRunCount` INT(11) NOT NULL;

ALTER TABLE `simulation_execution`
ADD COLUMN `RunCount` INT(11) NOT NULL;