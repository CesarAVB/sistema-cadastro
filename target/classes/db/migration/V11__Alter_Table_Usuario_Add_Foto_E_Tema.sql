ALTER TABLE `usuario` 
ADD COLUMN `foto_perfil` LONGTEXT NULL COMMENT 'Foto de perfil em Base64',
ADD COLUMN `tema` VARCHAR(20) NOT NULL DEFAULT 'light' COMMENT 'Tema da interface do usuário';
