ALTER TABLE `simulation_execution`
ADD COLUMN `RunProfile` VARCHAR(255) NOT NULL,
ADD COLUMN `StatisticsProfile` VARCHAR(255) NOT NULL,
ADD COLUMN `AssertsProfile` VARCHAR(255) NOT NULL;