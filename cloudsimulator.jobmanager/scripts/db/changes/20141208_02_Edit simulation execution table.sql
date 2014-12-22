ALTER TABLE `simulation_execution`
ADD COLUMN `Description` VARCHAR(1000) NOT NULL DEFAULT '',
ADD COLUMN `Status` INT(4) NOT NULL,
ADD COLUMN `Created` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN `Started` TIMESTAMP,
ADD COLUMN `Ended` TIMESTAMP;