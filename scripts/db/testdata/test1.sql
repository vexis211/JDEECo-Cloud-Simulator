-- --------------------------------------------------------
-- Hostitel:                     127.0.0.1
-- Verze serveru:                5.6.17 - MySQL Community Server (GPL)
-- OS serveru:                   Win64
-- HeidiSQL Verze:               9.2.0.4947
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
-- Exportování dat pro tabulku jdeeco_simulation.authentication_token: ~0 rows (přibližně)
DELETE FROM `authentication_token`;
/*!40000 ALTER TABLE `authentication_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `authentication_token` ENABLE KEYS */;

-- Exportování dat pro tabulku jdeeco_simulation.project: ~0 rows (přibližně)
DELETE FROM `project`;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` (`ID`, `CreatorID`, `Name`, `Description`, `Created`) VALUES
	(1, 1, 'Project 1', 'first project', '2015-04-11 09:31:34');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;

-- Exportování dat pro tabulku jdeeco_simulation.simulation_configuration: ~0 rows (přibližně)
DELETE FROM `simulation_configuration`;
/*!40000 ALTER TABLE `simulation_configuration` DISABLE KEYS */;
INSERT INTO `simulation_configuration` (`ID`, `DataID`, `CreatorID`, `ProjectID`, `Name`, `Description`, `Created`, `DefaultRunCount`) VALUES
	(1, 1, 1, 1, 'JDEECo-Cloud-Simulator-Test-1', 'first test', '2015-04-11 10:58:10', 1);
/*!40000 ALTER TABLE `simulation_configuration` ENABLE KEYS */;

-- Exportování dat pro tabulku jdeeco_simulation.simulation_data: ~1 rows (přibližně)
DELETE FROM `simulation_data`;
/*!40000 ALTER TABLE `simulation_data` DISABLE KEYS */;
INSERT INTO `simulation_data` (`ID`, `CreatorID`, `ProjectID`, `Name`, `Description`, `Created`, `VCSType`, `RepositoryURL`, `PomDirectory`, `MavenGoals`, `StartupFile`) VALUES
	(1, 1, 1, 'JDEECo-Cloud-Simulator-Test-1', 'first test', '2015-04-11 10:57:22', 0, 'https://github.com/vexis211/JDEECo-Cloud-Simulator-Test.git', 'cloudsimulator.test1', 'package', 'cloudsimulator.test1-0.0.1-SNAPSHOT.jar');
/*!40000 ALTER TABLE `simulation_data` ENABLE KEYS */;

-- Exportování dat pro tabulku jdeeco_simulation.simulation_execution: ~0 rows (přibližně)
DELETE FROM `simulation_execution`;
/*!40000 ALTER TABLE `simulation_execution` DISABLE KEYS */;
/*!40000 ALTER TABLE `simulation_execution` ENABLE KEYS */;

-- Exportování dat pro tabulku jdeeco_simulation.simulation_run: ~2 rows (přibližně)
DELETE FROM `simulation_run`;
/*!40000 ALTER TABLE `simulation_run` DISABLE KEYS */;
/*!40000 ALTER TABLE `simulation_run` ENABLE KEYS */;

-- Exportování dat pro tabulku jdeeco_simulation.user: ~0 rows (přibližně)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`ID`, `Email`, `Password`, `Role`, `RegistrationDate`, `LastActivityDate`, `ActivationState`) VALUES
	(1, 'hskalicky@gmail.com', '$2a$10$ovoD9AD9azGpZRFQoIG.VOtbfWg3PEYAb30y0PgzYzOrPTVS4MEKW', 1, '2015-04-11 09:28:32', '2015-05-03 22:13:56', 6);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

-- Exportování dat pro tabulku jdeeco_simulation.user_info: ~0 rows (přibližně)
DELETE FROM `user_info`;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;

-- Exportování dat pro tabulku jdeeco_simulation.visible_project: ~0 rows (přibližně)
DELETE FROM `visible_project`;
/*!40000 ALTER TABLE `visible_project` DISABLE KEYS */;
INSERT INTO `visible_project` (`UserID`, `ProjectID`) VALUES
	(1, 1);
/*!40000 ALTER TABLE `visible_project` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
