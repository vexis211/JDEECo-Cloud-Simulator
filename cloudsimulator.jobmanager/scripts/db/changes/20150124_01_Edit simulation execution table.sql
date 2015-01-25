ALTER TABLE `simulation_execution`
ADD COLUMN `EndSpecificationType` INT(4) NOT NULL,
ADD COLUMN `EndDate` TIMESTAMP NULL;