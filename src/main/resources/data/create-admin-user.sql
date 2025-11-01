-- Script para criar um usuário admin de teste
-- Senha: admin123

-- Remove usuário se já existir
DELETE FROM users WHERE email = 'admin@test.com';

-- Cria usuário admin
-- Senha criptografada com BCrypt: admin123
INSERT INTO users (email, nome, sobrenome, password, role) 
VALUES (
    'admin@test.com',
    'Admin',
    'User',
    '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lh5xY3xRHxiZ8rCAi',
    'ADMIN'
) ON CONFLICT (email) DO NOTHING;

-- Verifica se foi criado
SELECT id, email, nome, sobrenome, role FROM users WHERE email = 'admin@test.com';


