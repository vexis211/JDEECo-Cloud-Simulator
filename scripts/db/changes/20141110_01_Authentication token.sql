USE `jdeeco_simulation`;

/* Token for authentication. */

CREATE TABLE `authentication_token` (
	`ID` INT(11) NOT NULL AUTO_INCREMENT,
	`Value` VARCHAR(255) NOT NULL,
	`Owner` INT(11) NOT NULL,
	`Created` DATETIME NOT NULL,
	`Expiry` DATETIME NOT NULL,
	PRIMARY KEY (`id`),
	CONSTRAINT `FK_AuthenticationToken_User` FOREIGN KEY (`Owner`) REFERENCES `user` (`ID`) ON UPDATE NO ACTION ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
