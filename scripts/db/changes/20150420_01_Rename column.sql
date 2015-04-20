ALTER TABLE `simulation_data`
CHANGE COLUMN `PathToPom` `PomDirectory` VARCHAR(256) NOT NULL AFTER `RepositoryURL`;