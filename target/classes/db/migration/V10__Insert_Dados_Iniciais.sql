-- Migration: V4__Insert_Dados_Iniciais.sql
-- Objetivo: Popular as tabelas cliente, endereco e contato com 17 registros de teste.

-- 0. LIMPEZA (Opcional - Use com cuidado!)
-- A ordem é importante devido às chaves estrangeiras: primeiro as "filhas", depois a "pai".
DELETE FROM `contato`;
DELETE FROM `endereco`;
DELETE FROM `cliente`;

-- Reiniciar os contadores de ID (Auto increment) para começarem do 1
ALTER TABLE `cliente` AUTO_INCREMENT = 1;
ALTER TABLE `endereco` AUTO_INCREMENT = 1;
ALTER TABLE `contato` AUTO_INCREMENT = 1;

-- 1. INSERÇÃO NA TABELA CLIENTE
INSERT INTO `cliente` (nome, data_nascimento, tipo_pessoa, cpf_cnpj, rg, genero, login, senha, data_cadastro) VALUES
('Ana Silva', '1990-05-15', 'FISICA', '12345678901', 'MG12345', 'FEMININO', 'ana.silva', '12345', NOW()),
('Bruno Gomes', '1985-10-20', 'FISICA', '23456789012', 'SP23456', 'MASCULINO', 'bruno.g', '54321', NOW()),
('Empresa Alfa Ltda', '2010-01-01', 'JURIDICA', '12345678000199', 'ISENTO', NULL, 'alfa.corp', 'alfa123', NOW()),
('Carla Souza', '1992-03-12', 'FISICA', '34567890123', 'RJ34567', 'FEMININO', 'carla.s', 'senha99', NOW()),
('Daniel Oliveira', '1988-07-30', 'FISICA', '45678901234', 'PR45678', 'MASCULINO', 'daniel.o', 'dan123', NOW()),
('Eduarda Lima', '1995-12-05', 'FISICA', '56789012345', 'SC56789', 'FEMININO', 'edu.lima', 'edu789', NOW()),
('Fabio Santos', '1982-02-25', 'FISICA', '67890123456', 'BA67890', 'MASCULINO', 'fabio.s', 'fabio@1', NOW()),
('Giovanna Rocha', '2000-09-18', 'FISICA', '78901234567', 'GO78901', 'FEMININO', 'gi.rocha', 'gi2000', NOW()),
('Helio Junior', '1975-11-11', 'FISICA', '89012345678', 'PE89012', 'MASCULINO', 'helio.j', 'helio99', NOW()),
('Iara Martins', '1998-04-22', 'FISICA', '90123456789', 'RS90123', 'FEMININO', 'iara.m', 'iara!@', NOW()),
('João Pereira', '1991-08-14', 'FISICA', '01234567890', 'CE01234', 'MASCULINO', 'joao.p', 'joao14', NOW()),
('Kelly Farias', '1993-06-30', 'FISICA', '11234567891', 'AM11234', 'FEMININO', 'kelly.f', 'kel3006', NOW()),
('Leonardo Costa', '1987-01-05', 'FISICA', '22345678902', 'MT22345', 'MASCULINO', 'leo.costa', 'leo55', NOW()),
('Mercado Beta', '2015-05-20', 'JURIDICA', '22333444000188', 'ISENTO', NULL, 'beta.adm', 'beta88', NOW()),
('Nara Bueno', '1996-10-10', 'FISICA', '33456789013', 'MS33456', 'FEMININO', 'nara.b', 'nara10', NOW()),
('Otávio Luiz', '1980-03-03', 'FISICA', '44567890124', 'AL44567', 'MASCULINO', 'otavio.l', 'otv80', NOW()),
('Paula Mendes', '1994-12-25', 'FISICA', '55678901235', 'PB55678', 'FEMININO', 'paula.m', 'pau25', NOW());

-- 2. INSERÇÃO NA TABELA ENDERECO (Relacionado aos IDs de 1 a 17)
INSERT INTO `endereco` (cep, logradouro, numero, complemento, bairro, cidade, uf, id_cliente) VALUES
('01001000', 'Praça da Sé', '10', 'Apto 1', 'Sé', 'São Paulo', 'SP', 1),
('20010000', 'Rua Primeiro de Março', '100', 'Bloco B', 'Centro', 'Rio de Janeiro', 'RJ', 2),
('30110000', 'Avenida do Contorno', '500', 'Galpão 4', 'Funcionários', 'Belo Horizonte', 'MG', 3),
('40010000', 'Rua Chile', '12', 'Casa', 'Centro', 'Salvador', 'BA', 4),
('60010000', 'Rua Guilherme Rocha', '45', 'Sala 2', 'Centro', 'Fortaleza', 'CE', 5),
('70040000', 'Eixo Monumental', 'S/N', 'Anexo', 'Asa Norte', 'Brasília', 'DF', 6),
('80010010', 'Rua XV de Novembro', '88', 'Frente', 'Centro', 'Curitiba', 'PR', 7),
('90010000', 'Rua dos Andradas', '200', 'Apto 10', 'Centro', 'Porto Alegre', 'RS', 8),
('66010000', 'Rua 15 de Novembro', '30', 'Fundo', 'Campina', 'Belém', 'PA', 9),
('50010000', 'Avenida Rio Branco', '150', NULL, 'Recife', 'Recife', 'PE', 10),
('69005000', 'Rua Marechal Deodoro', '99', 'S2', 'Centro', 'Manaus', 'AM', 11),
('74003010', 'Avenida Goiás', '1010', 'Loja', 'Setor Central', 'Goiânia', 'GO', 12),
('57020000', 'Rua do Comércio', '77', 'Apt 202', 'Centro', 'Maceió', 'AL', 13),
('79002000', 'Avenida Afonso Pena', '2500', 'Térreo', 'Centro', 'Campo Grande', 'MS', 14),
('78005000', 'Rua Getúlio Vargas', '400', 'Cs 5', 'Centro', 'Cuiabá', 'MT', 15),
('58010000', 'Rua Visconde de Pelotas', '55', NULL, 'Centro', 'João Pessoa', 'PB', 16),
('49010000', 'Rua João Pessoa', '33', 'Bloco C', 'Centro', 'Aracaju', 'SE', 17);

-- 3. INSERÇÃO NA TABELA CONTATO (Relacionado aos IDs de 1 a 17)
INSERT INTO `contato` (tipo, valor, id_cliente) VALUES
('EMAIL', 'ana@email.com', 1), ('TELEFONE', '11988887777', 1),
('TELEFONE', '21977776666', 2), ('EMAIL', 'bruno@email.com', 2),
('EMAIL', 'contato@alfa.com', 3), ('TELEFONE', '3133334444', 3),
('EMAIL', 'carla@email.com', 4), ('TELEFONE', '71999990000', 5),
('EMAIL', 'edu@lima.com', 6), ('TELEFONE', '61988881111', 7),
('EMAIL', 'gi@rocha.com', 8), ('TELEFONE', '51988882222', 9),
('TELEFONE', '81988883333', 10), ('EMAIL', 'joao@p.com', 11),
('TELEFONE', '92988884444', 12), ('EMAIL', 'kelly@f.com', 13),
('EMAIL', 'sac@beta.com', 14), ('TELEFONE', '67988885555', 15),
('EMAIL', 'otavio@l.com', 16), ('TELEFONE', '79988886666', 17);