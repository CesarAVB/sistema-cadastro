CREATE TABLE IF NOT EXISTS `usuario` (
`id` bigint(20) NOT NULL AUTO_INCREMENT,
`email` varchar(70) UNIQUE NOT NULL,
`nome` varchar(70) DEFAULT NULL,
`username` varchar(50) UNIQUE NOT NULL,
`password` varchar(100) NOT NULL,
`account_non_expired` bit(1) DEFAULT NULL,
`account_non_locked` bit(1) DEFAULT NULL,
`credentials_non_expired` bit(1) DEFAULT NULL,
`enabled` bit(1) DEFAULT NULL,
PRIMARY KEY (`id`),
UNIQUE KEY `uk_username` (`username`)
);