-- ===============================================
-- DADOS SIMPLES PARA TESTE - PINGOU
-- ===============================================

-- 1. Planos de Assinatura (com todos os campos obrigatórios)
INSERT INTO planos (nome, descricao, preco, max_produtos_por_mes, frequencia_entrega, ativo) VALUES
('Básico', 'Plano básico com 1 cachaça por mês', 39.90, 1, 'MENSAL', true),
('Universitário', 'Desconto especial para estudantes', 29.90, 1, 'MENSAL', true),
('Pinguçoê', 'Para apreciadores experientes', 129.90, 3, 'MENSAL', true),
('Alambique', 'O mais premium do Pingou', 199.90, 3, 'MENSAL', true);

-- 2. Produtos (Cachaças)
INSERT INTO produtos (nome, descricao, preco, ativo) VALUES
('Cachaça Mineira Tradicional', 'Cachaça artesanal de Minas Gerais', 45.00, true),
('Cachaça Nordeste Premium', 'Cachaça especial do Nordeste', 65.00, true),
('Cachaça Paulista Gold', 'Cachaça envelhecida de São Paulo', 85.00, true);

-- Relacionamento JOINED: a tabela cachacas usa o mesmo ID de produtos
INSERT INTO cachacas (id, regiao, teor_alcoolico, volume, tipo_cachaca, tipo_envelhecimento, tempo_envelhecimento_meses, ano_producao)
SELECT p.id, 'Minas Gerais', 40.0, 700, 'BRANCA', 'SEM_ENVELHECIMENTO', 0, 2024 FROM produtos p WHERE p.nome = 'Cachaça Mineira Tradicional';
INSERT INTO cachacas (id, regiao, teor_alcoolico, volume, tipo_cachaca, tipo_envelhecimento, tempo_envelhecimento_meses, ano_producao)
SELECT p.id, 'Pernambuco', 41.0, 700, 'OURO', 'ENVELHECIDA', 6, 2024 FROM produtos p WHERE p.nome = 'Cachaça Nordeste Premium';
INSERT INTO cachacas (id, regiao, teor_alcoolico, volume, tipo_cachaca, tipo_envelhecimento, tempo_envelhecimento_meses, ano_producao)
SELECT p.id, 'São Paulo', 42.0, 700, 'PREMIUM', 'ENVELHECIDA', 12, 2023 FROM produtos p WHERE p.nome = 'Cachaça Paulista Gold';

INSERT INTO pacotes (nome, descricao, data_entrega, mes, ano, plano_id, ativo)
SELECT 'Pacote Básico - Outubro 2024', 'Cachaça mineira tradicional', '2024-10-15', 10, 2024, id, true FROM planos WHERE nome = 'Básico';

INSERT INTO pacotes (nome, descricao, data_entrega, mes, ano, plano_id, ativo)
SELECT 'Pacote Universitário - Outubro 2024', 'Cachaça nordeste premium', '2024-10-15', 10, 2024, id, true FROM planos WHERE nome = 'Universitário';

INSERT INTO item_pacote (pacote_id, produto_id, quantidade, observacoes)
SELECT p.id, pr.id, 1, 'Cachaça mineira tradicional'
FROM pacotes p, produtos pr
WHERE p.nome = 'Pacote Básico - Outubro 2024' AND pr.nome = 'Cachaça Mineira Tradicional';

INSERT INTO item_pacote (pacote_id, produto_id, quantidade, observacoes)
SELECT p.id, pr.id, 1, 'Cachaça nordeste premium'
FROM pacotes p, produtos pr
WHERE p.nome = 'Pacote Universitário - Outubro 2024' AND pr.nome = 'Cachaça Nordeste Premium';

-- 6. Usuários de Teste
INSERT INTO users (nome, sobrenome, email, password, role) VALUES
('Admin', 'Sistema', 'admin@pingou.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyc..Wng4kSa1P.o7nXSJe', 'ADMIN'),
('João', 'Silva', 'joao.silva@email.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyc..Wng4kSa1P.o7nXSJe', 'USER'),
('Maria', 'Santos', 'maria.santos@email.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyc..Wng4kSa1P.o7nXSJe', 'USER');

INSERT INTO assinaturas (user_id, plano_id, data_inicio, data_expiracao, status)
SELECT u.id, p.id, '2024-10-01', '2025-10-01', 'ATIVA'
FROM users u, planos p
WHERE u.email = 'joao.silva@email.com' AND p.nome = 'Básico';

INSERT INTO assinaturas (user_id, plano_id, data_inicio, data_expiracao, status)
SELECT u.id, p.id, '2024-10-01', '2025-10-01', 'ATIVA'
FROM users u, planos p
WHERE u.email = 'maria.santos@email.com' AND p.nome = 'Universitário';