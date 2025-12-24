CREATE TABLE IF NOT EXISTS `contato` (
`id` bigint NOT NULL AUTO_INCREMENT,
`tipo` enum('EMAIL','TELEFONE') DEFAULT 'TELEFONE',
`valor` varchar(50) NOT NULL,
`id_cliente` bigint DEFAULT NULL,
PRIMARY KEY (`id`),
CONSTRAINT `fk_cliente_contato` FOREIGN KEY (`id_cliente`) REFERENCES `cliente`(`id`)
);