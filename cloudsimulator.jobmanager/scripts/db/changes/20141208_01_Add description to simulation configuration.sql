ALTER TABLE `simulation_configuration`
ADD COLUMN `Description` VARCHAR(1000) NOT NULL DEFAULT '' AFTER `Name`;