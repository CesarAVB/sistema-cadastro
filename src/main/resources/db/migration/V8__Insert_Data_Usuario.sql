-- Inserir o usuário 'admin' (ignora se já existir)
-- Username: admin
-- Nome: Administrador
-- Email: admin@admin.com
-- Senha: admin123 (BCrypt)
INSERT IGNORE INTO `usuario` (`id`, `username`, `nome`, `email`, `password`, `account_non_expired`, `account_non_locked`, `credentials_non_expired`, `enabled`) VALUES
(1, 'admin', 'Administrador', 'admin@admin.com', '$2a$10$hI46iyrwXjxOsvR6VcE6h.vVQMcNzDrQjvGFJgkQdO6y4nCQ7l/gG', b'1', b'1', b'1', b'1');
-- ATENÇÃO: O hash BCrypt já está aqui!
-- ATENÇÃO: O ID 1 foi especificado para o usuário admin. Se você já tiver um usuário com ID 1,
-- ou se o auto-incremento já tiver passado do 1, você precisará ajustar este ID ou
-- remover o `id` e deixar o auto-incremento gerá-lo (mas isso pode complicar a V9).
-- Para fins de teste e consistência com V9, manteremos ID 1 aqui.
