CREATE TABLE `organization` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) DEFAULT NULL,
  `lastName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `teams` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `organization_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK69209B63FBF4CF4` (`organization_id`),
  CONSTRAINT `FK69209B63FBF4CF4` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`id`)
);

CREATE TABLE IF NOT EXISTS `member_teams` (
  `Member_id` int(11) NOT NULL,
  `teams_id` int(11) NOT NULL,
  KEY `FK72C6FF1822C4CBB` (`teams_id`),
  KEY `FK72C6FF170335835` (`Member_id`),
  CONSTRAINT `FK72C6FF170335835` FOREIGN KEY (`Member_id`) REFERENCES `member` (`id`),
  CONSTRAINT `FK72C6FF1822C4CBB` FOREIGN KEY (`teams_id`) REFERENCES `teams` (`id`)
);