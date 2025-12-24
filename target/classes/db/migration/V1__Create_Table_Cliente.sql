CREATE TABLE IF NOT EXISTS `cliente` (
`id` bigint NOT NULL AUTO_INCREMENT,
`nome` varchar(100) NOT NULL,
`data_nascimento` datetime(6) NOT NULL,
`tipo_pessoa` enum('FISICA','JURIDICA') NOT NULL,
`cpf_cnpj` varchar(20) UNIQUE NOT NULL,
`rg` varchar(20) NOT NULL,
`genero` enum('FEMININO','MASCULINO') DEFAULT NULL,
`login` varchar(50) DEFAULT NULL,
`senha` varchar(50) DEFAULT NULL,
PRIMARY KEY (`id`)
);