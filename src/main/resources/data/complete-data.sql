-- ===============================================
-- PINGOU - DADOS COMPLETOS COM TRANSAÇÃO
-- Script com transação explícita para garantir consistência
-- ===============================================

BEGIN;

-- 1. PLANOS DE ASSINATURA
INSERT INTO planos (nome, descricao, preco, max_produtos_por_mes, frequencia_entrega, ativo) VALUES
('Básico', 'Plano ideal para iniciantes na degustação de cachaças. Receba 1 garrafa de cachaça artesanal selecionada por mês.', 39.90, 1, 'MENSAL', true),
('Universitário', 'Plano especial com desconto para estudantes. Receba 1 garrafa de cachaça artesanal por mês com preço acessível.', 29.90, 1, 'MENSAL', true),
('Pinguçoê', 'Para os verdadeiros apreciadores! Receba 3 garrafas de cachaças premium selecionadas.', 129.90, 3, 'MENSAL', true),
('Alambique', 'O plano mais completo e premium do Pingou. Receba 3 garrafas de cachaças ultra-premium.', 199.90, 3, 'MENSAL', true);

-- 2. PRODUTOS (CACHAÇAS)
INSERT INTO produtos (nome, descricao, preco, ativo) VALUES
('Cachaça Mineira Tradicional', 'Cachaça artesanal de Minas Gerais', 45.00, true),
('Cachaça Nordeste Branca', 'Cachaça do sertão nordestino', 35.00, true),
('Cachaça Paulista Artesanal', 'Da região de Piracicaba', 42.00, true),
('Cachaça Goiana Ouro', 'Levemente envelhecida em tonéis de carvalho', 68.00, true),
('Cachaça Fluminense Premium', 'Produzida na região serrana do Rio de Janeiro', 89.00, true),
('Cachaça Premium Aged 3 Anos', 'Edição limitada envelhecida por 3 anos', 189.00, true);

INSERT INTO cachacas (id, regiao, teor_alcoolico, volume, tipo_cachaca, tipo_envelhecimento, tempo_envelhecimento_meses, ano_producao)
SELECT p.id, 'Minas Gerais', 40.0, 700, 'BRANCA', 'SEM_ENVELHECIMENTO', 0, 2024 FROM produtos p WHERE p.nome = 'Cachaça Mineira Tradicional';
INSERT INTO cachacas (id, regiao, teor_alcoolico, volume, tipo_cachaca, tipo_envelhecimento, tempo_envelhecimento_meses, ano_producao)
SELECT p.id, 'Pernambuco', 39.0, 700, 'BRANCA', 'SEM_ENVELHECIMENTO', 0, 2024 FROM produtos p WHERE p.nome = 'Cachaça Nordeste Branca';
INSERT INTO cachacas (id, regiao, teor_alcoolico, volume, tipo_cachaca, tipo_envelhecimento, tempo_envelhecimento_meses, ano_producao)
SELECT p.id, 'São Paulo', 38.5, 700, 'BRANCA', 'SEM_ENVELHECIMENTO', 0, 2024 FROM produtos p WHERE p.nome = 'Cachaça Paulista Artesanal';
INSERT INTO cachacas (id, regiao, teor_alcoolico, volume, tipo_cachaca, tipo_envelhecimento, tempo_envelhecimento_meses, ano_producao)
SELECT p.id, 'Goiás', 40.0, 700, 'OURO', 'ENVELHECIDA', 6, 2024 FROM produtos p WHERE p.nome = 'Cachaça Goiana Ouro';
INSERT INTO cachacas (id, regiao, teor_alcoolico, volume, tipo_cachaca, tipo_envelhecimento, tempo_envelhecimento_meses, ano_producao)
SELECT p.id, 'Rio de Janeiro', 41.0, 700, 'ENVELHECIDA', 'ENVELHECIDA', 6, 2023 FROM produtos p WHERE p.nome = 'Cachaça Fluminense Premium';
INSERT INTO cachacas (id, regiao, teor_alcoolico, volume, tipo_cachaca, tipo_envelhecimento, tempo_envelhecimento_meses, ano_producao)
SELECT p.id, 'Minas Gerais', 42.0, 750, 'PREMIUM', 'PREMIUM', 36, 2021 FROM produtos p WHERE p.nome = 'Cachaça Premium Aged 3 Anos';

-- 4. PACOTES (usar SELECT para resolver o plano_id pelo nome)
INSERT INTO pacotes (nome, descricao, data_entrega, mes, ano, plano_id, ativo)
SELECT 'Pacote Básico - Outubro 2024', 'Descobrindo Minas Gerais', '2024-10-15', 10, 2024, id, true FROM planos WHERE nome = 'Básico';

INSERT INTO pacotes (nome, descricao, data_entrega, mes, ano, plano_id, ativo)
SELECT 'Pacote Universitário - Outubro 2024', 'Primeira degustação', '2024-10-15', 10, 2024, id, true FROM planos WHERE nome = 'Universitário';

INSERT INTO pacotes (nome, descricao, data_entrega, mes, ano, plano_id, ativo)
SELECT 'Pacote Pinguçoê - Outubro 2024', 'Trio Especial', '2024-10-15', 10, 2024, id, true FROM planos WHERE nome = 'Pinguçoê';

INSERT INTO pacotes (nome, descricao, data_entrega, mes, ano, plano_id, ativo)
SELECT 'Pacote Alambique - Outubro 2024', 'Experiência Premium', '2024-10-15', 10, 2024, id, true FROM planos WHERE nome = 'Alambique';

-- 5. ITENS DOS PACOTES
INSERT INTO item_pacote (pacote_id, produto_id, quantidade, observacoes)
SELECT p.id, pr.id, 1, 'Cachaça mineira tradicional'
FROM pacotes p, produtos pr
WHERE p.nome = 'Pacote Básico - Outubro 2024' AND pr.nome = 'Cachaça Mineira Tradicional';

INSERT INTO item_pacote (pacote_id, produto_id, quantidade, observacoes)
SELECT p.id, pr.id, 1, 'Cachaça nordeste branca'
FROM pacotes p, produtos pr
WHERE p.nome = 'Pacote Universitário - Outubro 2024' AND pr.nome = 'Cachaça Nordeste Branca';

INSERT INTO item_pacote (pacote_id, produto_id, quantidade, observacoes)
SELECT p.id, pr.id, 1, 'Mineira tradicional'
FROM pacotes p, produtos pr
WHERE p.nome = 'Pacote Pinguçoê - Outubro 2024' AND pr.nome = 'Cachaça Mineira Tradicional';

INSERT INTO item_pacote (pacote_id, produto_id, quantidade, observacoes)
SELECT p.id, pr.id, 1, 'Nordestina'
FROM pacotes p, produtos pr
WHERE p.nome = 'Pacote Pinguçoê - Outubro 2024' AND pr.nome = 'Cachaça Nordeste Branca';

INSERT INTO item_pacote (pacote_id, produto_id, quantidade, observacoes)
SELECT p.id, pr.id, 1, 'Paulista artesanal'
FROM pacotes p, produtos pr
WHERE p.nome = 'Pacote Pinguçoê - Outubro 2024' AND pr.nome = 'Cachaça Paulista Artesanal';

INSERT INTO item_pacote (pacote_id, produto_id, quantidade, observacoes)
SELECT p.id, pr.id, 1, 'Goiana ouro'
FROM pacotes p, produtos pr
WHERE p.nome = 'Pacote Alambique - Outubro 2024' AND pr.nome = 'Cachaça Goiana Ouro';

INSERT INTO item_pacote (pacote_id, produto_id, quantidade, observacoes)
SELECT p.id, pr.id, 1, 'Fluminense premium'
FROM pacotes p, produtos pr
WHERE p.nome = 'Pacote Alambique - Outubro 2024' AND pr.nome = 'Cachaça Fluminense Premium';

INSERT INTO item_pacote (pacote_id, produto_id, quantidade, observacoes)
SELECT p.id, pr.id, 1, 'Premium aged 3 anos'
FROM pacotes p, produtos pr
WHERE p.nome = 'Pacote Alambique - Outubro 2024' AND pr.nome = 'Cachaça Premium Aged 3 Anos';

-- 6. USUÁRIOS DE TESTE
INSERT INTO users (nome, sobrenome, email, password, role) VALUES
('Admin', 'Sistema', 'admin@pingou.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyc..Wng4kSa1P.o7nXSJe', 'ADMIN'),
('João', 'Silva', 'joao.silva@email.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyc..Wng4kSa1P.o7nXSJe', 'USER'),
('Maria', 'Santos', 'maria.santos@email.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyc..Wng4kSa1P.o7nXSJe', 'USER'),
('Carlos', 'Oliveira', 'carlos.oliveira@email.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyc..Wng4kSa1P.o7nXSJe', 'USER'),
('Ana', 'Costa', 'ana.costa@email.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyc..Wng4kSa1P.o7nXSJe', 'USER');

-- Resolver user_id e plano_id por email/nome para evitar dependência de IDs
INSERT INTO assinaturas (user_id, plano_id, data_inicio, data_expiracao, status)
SELECT u.id, p.id, '2024-10-01', '2024-12-31', 'ATIVA'
FROM users u, planos p
WHERE u.email = 'joao.silva@email.com' AND p.nome = 'Básico';

INSERT INTO assinaturas (user_id, plano_id, data_inicio, data_expiracao, status)
SELECT u.id, p.id, '2024-10-01', '2025-03-31', 'ATIVA'
FROM users u, planos p
WHERE u.email = 'maria.santos@email.com' AND p.nome = 'Universitário';

INSERT INTO assinaturas (user_id, plano_id, data_inicio, data_expiracao, status)
SELECT u.id, p.id, '2024-10-01', '2025-09-30', 'ATIVA'
FROM users u, planos p
WHERE u.email = 'carlos.oliveira@email.com' AND p.nome = 'Pinguçoê';

INSERT INTO assinaturas (user_id, plano_id, data_inicio, data_expiracao, status)
SELECT u.id, p.id, '2024-09-15', '2025-09-15', 'ATIVA'
FROM users u, planos p
WHERE u.email = 'ana.costa@email.com' AND p.nome = 'Alambique';

COMMIT;

-- Verificar dados inseridos
SELECT 'PLANOS' as tabela, COUNT(*) as total FROM planos
UNION ALL SELECT 'PRODUTOS', COUNT(*) FROM produtos
UNION ALL SELECT 'CACHACAS', COUNT(*) FROM cachacas
UNION ALL SELECT 'PACOTES', COUNT(*) FROM pacotes
UNION ALL SELECT 'ITEM_PACOTE', COUNT(*) FROM item_pacote
UNION ALL SELECT 'USERS', COUNT(*) FROM users
UNION ALL SELECT 'ASSINATURAS', COUNT(*) FROM assinaturas
ORDER BY tabela;