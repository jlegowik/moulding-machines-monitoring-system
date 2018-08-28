CREATE TABLE `Production` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `machine_name` varchar(255) NOT NULL DEFAULT '',
  `variable_name` varchar(255) NOT NULL DEFAULT '',
  `datetime_from` datetime NOT NULL,
  `datetime_to` datetime NOT NULL,
  `value` decimal(10,0) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
);

CREATE TABLE `Runtime` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `machine_name` varchar(255) NOT NULL DEFAULT '',
  `datetime` datetime NOT NULL,
  `isrunning` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
);