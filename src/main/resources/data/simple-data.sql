-- ===============================================
-- DADOS SIMPLES PARA TESTE - PINGOU
-- ===============================================

-- 1. Planos de Assinatura (com todos os campos obrigatórios)
INSERT INTO planos (nome, descricao, preco, plano_tipo, max_produtos_por_mes, frequencia_entrega, ativo) VALUES
('Básico', 'Plano básico com 1 cachaça por mês', 39.90, 'BASICO', 1, 'MENSAL', true),
('Universitário', 'Desconto especial para estudantes', 29.90, 'UNIVERSITARIO', 1, 'MENSAL', true),
('Pinguçoê', 'Para apreciadores experientes', 129.90, 'PINGUCOE', 3, 'MENSAL', true),
('Alambique', 'O mais premium do Pingou', 199.90, 'ALAMBIQUE', 3, 'MENSAL', true);

-- 2. Produtos (Cachaças)
INSERT INTO produtos (nome, descricao, preco, ativo) VALUES
('Cachaça Mineira Tradicional', 'Cachaça artesanal de Minas Gerais', 45.00, true),
('Cachaça Nordeste Premium', 'Cachaça especial do Nordeste', 65.00, true),
('Cachaça Paulista Gold', 'Cachaça envelhecida de São Paulo', 85.00, true);

-- 3. Detalhes das Cachaças
INSERT INTO cachaças (id, regiao, teor_alcoolico, volume, tipo_cachaca, tipo_envelhecimento, tempo_envelhecimento_meses, ano_producao) VALUES
(1, 'Minas Gerais', 40.0, 700, 'BRANCA', 'SEM_ENVELHECIMENTO', 0, 2024),
(2, 'Pernambuco', 41.0, 700, 'OURO', 'ENVELHECIDA', 6, 2024),
(3, 'São Paulo', 42.0, 700, 'PREMIUM', 'ENVELHECIDA', 12, 2023);

-- 4. Pacotes (com campo ativo obrigatório)
INSERT INTO pacotes (nome, descricao, data_entrega, mes, ano, plano_id, ativo) VALUES
('Pacote Básico - Outubro 2024', 'Cachaça mineira tradicional', '2024-10-15', 10, 2024, 1, true),
('Pacote Universitário - Outubro 2024', 'Cachaça nordeste premium', '2024-10-15', 10, 2024, 2, true);

-- 5. Itens dos Pacotes
INSERT INTO item_pacote (pacote_id, produto_id, quantidade, observacoes) VALUES
(1, 1, 1, 'Cachaça mineira tradicional'),
(2, 2, 1, 'Cachaça nordeste premium');

-- 6. Usuários de Teste
INSERT INTO users (nome, sobrenome, email, password, role) VALUES
('Admin', 'Sistema', 'admin@pingou.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyc..Wng4kSa1P.o7nXSJe', 'ADMIN'),
('João', 'Silva', 'joao.silva@email.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyc..Wng4kSa1P.o7nXSJe', 'USER'),
('Maria', 'Santos', 'maria.santos@email.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyc..Wng4kSa1P.o7nXSJe', 'USER');

-- 7. Assinaturas
INSERT INTO assinaturas (user_id, plano_id, data_inicio, data_expiracao, status) VALUES
(2, 1, '2024-10-01', '2025-10-01', 'ATIVA'),
(3, 2, '2024-10-01', '2025-10-01', 'ATIVA');