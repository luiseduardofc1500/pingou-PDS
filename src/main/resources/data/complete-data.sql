-- ===============================================
-- PINGOU - DADOS COMPLETOS COM TRANSAÇÃO
-- Script com transação explícita para garantir consistência
-- ===============================================

BEGIN;

-- 1. PLANOS DE ASSINATURA
INSERT INTO planos (nome, descricao, preco, plano_tipo, max_produtos_por_mes, frequencia_entrega, ativo) VALUES
('Básico', 'Plano ideal para iniciantes na degustação de cachaças. Receba 1 garrafa de cachaça artesanal selecionada por mês.', 39.90, 'BASICO', 1, 'MENSAL', true),
('Universitário', 'Plano especial com desconto para estudantes. Receba 1 garrafa de cachaça artesanal por mês com preço acessível.', 29.90, 'UNIVERSITARIO', 1, 'MENSAL', true),
('Pinguçoê', 'Para os verdadeiros apreciadores! Receba 3 garrafas de cachaças premium selecionadas.', 129.90, 'PINGUCOE', 3, 'MENSAL', true),
('Alambique', 'O plano mais completo e premium do Pingou. Receba 3 garrafas de cachaças ultra-premium.', 199.90, 'ALAMBIQUE', 3, 'MENSAL', true);

-- 2. PRODUTOS (CACHAÇAS)
INSERT INTO produtos (nome, descricao, preco, ativo) VALUES
('Cachaça Mineira Tradicional', 'Cachaça artesanal de Minas Gerais', 45.00, true),
('Cachaça Nordeste Branca', 'Cachaça do sertão nordestino', 35.00, true),
('Cachaça Paulista Artesanal', 'Da região de Piracicaba', 42.00, true),
('Cachaça Goiana Ouro', 'Levemente envelhecida em tonéis de carvalho', 68.00, true),
('Cachaça Fluminense Premium', 'Produzida na região serrana do Rio de Janeiro', 89.00, true),
('Cachaça Premium Aged 3 Anos', 'Edição limitada envelhecida por 3 anos', 189.00, true);

-- 3. DETALHES DAS CACHAÇAS
INSERT INTO cachaças (id, regiao, teor_alcoolico, volume, tipo_cachaca, tipo_envelhecimento, tempo_envelhecimento_meses, ano_producao) VALUES
(1, 'Minas Gerais', 40.0, 700, 'BRANCA', 'SEM_ENVELHECIMENTO', 0, 2024),
(2, 'Pernambuco', 39.0, 700, 'BRANCA', 'SEM_ENVELHECIMENTO', 0, 2024),
(3, 'São Paulo', 38.5, 700, 'BRANCA', 'SEM_ENVELHECIMENTO', 0, 2024),
(4, 'Goiás', 40.0, 700, 'OURO', 'ENVELHECIDA', 6, 2024),
(5, 'Rio de Janeiro', 41.0, 700, 'ENVELHECIDA', 'ENVELHECIDA', 6, 2023),
(6, 'Minas Gerais', 42.0, 750, 'PREMIUM', 'PREMIUM', 36, 2021);

-- 4. PACOTES
INSERT INTO pacotes (nome, descricao, data_entrega, mes, ano, plano_id, ativo) VALUES
('Pacote Básico - Outubro 2024', 'Descobrindo Minas Gerais', '2024-10-15', 10, 2024, 1, true),
('Pacote Universitário - Outubro 2024', 'Primeira degustação', '2024-10-15', 10, 2024, 2, true),
('Pacote Pinguçoê - Outubro 2024', 'Trio Especial', '2024-10-15', 10, 2024, 3, true),
('Pacote Alambique - Outubro 2024', 'Experiência Premium', '2024-10-15', 10, 2024, 4, true);

-- 5. ITENS DOS PACOTES
INSERT INTO item_pacote (pacote_id, produto_id, quantidade, observacoes) VALUES
(1, 1, 1, 'Cachaça mineira tradicional'),
(2, 2, 1, 'Cachaça nordeste branca'),
(3, 1, 1, 'Mineira tradicional'),
(3, 2, 1, 'Nordestina'),
(3, 3, 1, 'Paulista artesanal'),
(4, 4, 1, 'Goiana ouro'),
(4, 5, 1, 'Fluminense premium'),
(4, 6, 1, 'Premium aged 3 anos');

-- 6. USUÁRIOS DE TESTE
INSERT INTO users (nome, sobrenome, email, password, role) VALUES
('Admin', 'Sistema', 'admin@pingou.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyc..Wng4kSa1P.o7nXSJe', 'ADMIN'),
('João', 'Silva', 'joao.silva@email.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyc..Wng4kSa1P.o7nXSJe', 'USER'),
('Maria', 'Santos', 'maria.santos@email.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyc..Wng4kSa1P.o7nXSJe', 'USER'),
('Carlos', 'Oliveira', 'carlos.oliveira@email.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyc..Wng4kSa1P.o7nXSJe', 'USER'),
('Ana', 'Costa', 'ana.costa@email.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyc..Wng4kSa1P.o7nXSJe', 'USER');

-- 7. ASSINATURAS
INSERT INTO assinaturas (user_id, plano_id, data_inicio, data_expiracao, status) VALUES
(2, 1, '2024-10-01', '2024-12-31', 'ATIVA'),
(3, 2, '2024-10-01', '2025-03-31', 'ATIVA'),
(4, 3, '2024-10-01', '2025-09-30', 'ATIVA'),
(5, 4, '2024-09-15', '2025-09-15', 'ATIVA');

COMMIT;

-- Verificar dados inseridos
SELECT 'PLANOS' as tabela, COUNT(*) as total FROM planos
UNION ALL SELECT 'PRODUTOS', COUNT(*) FROM produtos
UNION ALL SELECT 'CACHAÇAS', COUNT(*) FROM cachaças
UNION ALL SELECT 'PACOTES', COUNT(*) FROM pacotes
UNION ALL SELECT 'ITEM_PACOTE', COUNT(*) FROM item_pacote
UNION ALL SELECT 'USERS', COUNT(*) FROM users
UNION ALL SELECT 'ASSINATURAS', COUNT(*) FROM assinaturas
ORDER BY tabela;