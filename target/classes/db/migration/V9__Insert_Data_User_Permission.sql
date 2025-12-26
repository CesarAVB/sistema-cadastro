-- Associar permissão ADMIN ao usuário admin (ignora se já existir)
INSERT IGNORE INTO `user_permission` (`id_user`, `id_permission`)
SELECT u.id, p.id
FROM `usuario` u, `permission` p
WHERE u.username = 'admin' AND p.description = 'ADMIN';

-- Associar permissão MANAGER ao usuário admin (ignora se já existir)
INSERT IGNORE INTO `user_permission` (`id_user`, `id_permission`)
SELECT u.id, p.id
FROM `usuario` u, `permission` p
WHERE u.username = 'admin' AND p.description = 'MANAGER';
