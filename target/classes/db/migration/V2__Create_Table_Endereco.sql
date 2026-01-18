CREATE TABLE IF NOT EXISTS `endereco` (
`id` bigint NOT NULL AUTO_INCREMENT,
`cep` varchar(20) DEFAULT NULL,
`logradouro` varchar(100) DEFAULT NULL,
`numero` varchar(20) DEFAULT NULL,
`complemento` varchar(50) DEFAULT NULL,
`bairro` varchar(50) DEFAULT NULL,
`cidade` varchar(50) DEFAULT NULL,
`uf` varchar(50) DEFAULT NULL,
`id_cliente` bigint DEFAULT NULL,
PRIMARY KEY (`id`),
CONSTRAINT `fk_cliente_endereco` FOREIGN KEY (`id_cliente`) REFERENCES `cliente`(`id`)
);