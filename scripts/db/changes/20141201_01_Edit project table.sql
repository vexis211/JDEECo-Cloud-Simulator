
ALTER TABLE `project`
ADD `Name` varchar(255) NOT NULL,
ADD `Visible` tinyint(1) NOT NULL DEFAULT '1';
