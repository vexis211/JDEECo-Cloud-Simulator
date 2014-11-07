USE `jdeeco_simulation`;


ALTER TABLE `user`
	ADD `Role` int(11) NOT NULL AFTER `Password`,
	ADD `LastActivityDate` timestamp NULL DEFAULT NULL,
	ADD `ActivationState` int(11) NOT NULL DEFAULT '0';


CREATE TABLE IF NOT EXISTS `user_info` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `UserID` int(11) NOT NULL,
  `Name` varchar(255) NOT NULL,
  `Value` text,
  PRIMARY KEY (`ID`),
  KEY `UserID` (`UserID`),
  CONSTRAINT `FK_UserInfo_User` FOREIGN KEY (`UserID`) REFERENCES `user` (`ID`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;