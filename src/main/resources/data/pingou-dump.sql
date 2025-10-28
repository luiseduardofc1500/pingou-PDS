-- PINGOU - Dump completo (schema + dados)
-- Compatível com PostgreSQL 15+
-- Observação: alinhado às entidades JPA atuais (JOINED para produtos/cachaças; sem coluna plano_tipo).

BEGIN;

-- Limpeza (ordem respeitando FKs)
DROP TABLE IF EXISTS item_pacote CASCADE;
DROP TABLE IF EXISTS pacotes CASCADE;
DROP TABLE IF EXISTS "cachaças" CASCADE;
DROP TABLE IF EXISTS produtos CASCADE;
DROP TABLE IF EXISTS assinaturas CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS planos CASCADE;

-- Tabela: planos
CREATE TABLE planos (
  id BIGSERIAL PRIMARY KEY,
  nome VARCHAR(255) NOT NULL UNIQUE,
  descricao VARCHAR(2000) NOT NULL,
  preco NUMERIC(10,2) NOT NULL,
  max_produtos_por_mes INTEGER NOT NULL,
  frequencia_entrega VARCHAR(50) NOT NULL,
  ativo BOOLEAN NOT NULL
);

-- Tabela: produtos (classe base)
CREATE TABLE produtos (
  id BIGSERIAL PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  descricao VARCHAR(1000),
  preco NUMERIC(10,2) NOT NULL,
  url_imagem VARCHAR(1000),
  ativo BOOLEAN NOT NULL
);

-- Tabela: cachaças (subclasse JOINED de produtos, mesma PK referenciando produtos.id)
CREATE TABLE "cachaças" (
  id BIGINT PRIMARY KEY REFERENCES produtos(id) ON DELETE CASCADE,
  regiao VARCHAR(255) NOT NULL,
  teor_alcoolico NUMERIC(5,2) NOT NULL,
  volume INTEGER NOT NULL,
  tipo_cachaca VARCHAR(50) NOT NULL,
  tipo_envelhecimento VARCHAR(50),
  tempo_envelhecimento_meses INTEGER,
  ano_producao INTEGER
);

-- Tabela: users
CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  email VARCHAR(255) NOT NULL,
  nome VARCHAR(255) NOT NULL,
  sobrenome VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  role VARCHAR(50) NOT NULL
);
CREATE UNIQUE INDEX IF NOT EXISTS ux_users_email ON users(email);

-- Tabela: pacotes
CREATE TABLE pacotes (
  id BIGSERIAL PRIMARY KEY,
  nome VARCHAR(255) NOT NULL,
  descricao VARCHAR(1000),
  data_entrega DATE NOT NULL,
  mes INTEGER NOT NULL,
  ano INTEGER NOT NULL,
  plano_id BIGINT NOT NULL REFERENCES planos(id),
  ativo BOOLEAN NOT NULL
);
CREATE INDEX IF NOT EXISTS ix_pacotes_plano ON pacotes(plano_id);
CREATE INDEX IF NOT EXISTS ix_pacotes_mes_ano ON pacotes(mes, ano);

-- Tabela: item_pacote (itens do pacote)
CREATE TABLE item_pacote (
  id BIGSERIAL PRIMARY KEY,
  pacote_id BIGINT NOT NULL REFERENCES pacotes(id) ON DELETE CASCADE,
  produto_id BIGINT NOT NULL REFERENCES produtos(id),
  quantidade INTEGER NOT NULL,
  observacoes VARCHAR(500)
);
CREATE INDEX IF NOT EXISTS ix_item_pacote_pacote ON item_pacote(pacote_id);
CREATE INDEX IF NOT EXISTS ix_item_pacote_produto ON item_pacote(produto_id);

-- Tabela: assinaturas (user 1-1; plano N-1)
CREATE TABLE assinaturas (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
  plano_id BIGINT NOT NULL REFERENCES planos(id),
  status VARCHAR(50) NOT NULL,
  data_inicio DATE NOT NULL,
  data_expiracao DATE
);
CREATE INDEX IF NOT EXISTS ix_assinaturas_plano ON assinaturas(plano_id);

-- Dados: planos (ids fixos para referenciamento)
INSERT INTO planos (id, nome, descricao, preco, max_produtos_por_mes, frequencia_entrega, ativo) VALUES
(1, 'Básico', 'Plano ideal para iniciantes na degustação de cachaças. Receba 1 garrafa artesanal por mês.', 39.90, 1, 'MENSAL', TRUE),
(2, 'Universitário', 'Plano especial com desconto para estudantes. 1 garrafa por mês com preço acessível.', 29.90, 1, 'MENSAL', TRUE),
(3, 'Pinguçôe', 'Para os verdadeiros apreciadores! Receba 3 garrafas premium selecionadas.', 129.90, 3, 'MENSAL', TRUE),
(4, 'Alambique', 'O plano mais completo e premium do Pingou. 3 garrafas ultra-premium.', 199.90, 3, 'MENSAL', TRUE);

-- Dados: produtos (cachaças base)
INSERT INTO produtos (id, nome, descricao, preco, url_imagem, ativo) VALUES
(1, 'Cachaça Mineira Tradicional', 'Cachaça artesanal de Minas Gerais', 45.00, NULL, TRUE),
(2, 'Cachaça Nordeste Branca', 'Cachaça do sertão nordestino', 35.00, NULL, TRUE),
(3, 'Cachaça Paulista Artesanal', 'Da região de Piracicaba', 42.00, NULL, TRUE),
(4, 'Cachaça Goiana Ouro', 'Levemente envelhecida em carvalho', 68.00, NULL, TRUE),
(5, 'Cachaça Fluminense Premium', 'Região serrana do Rio de Janeiro', 89.00, NULL, TRUE),
(6, 'Cachaça Premium Aged 3 Anos', 'Edição limitada envelhecida por 3 anos', 189.00, NULL, TRUE);

-- Dados: cachaças (subclasse)
INSERT INTO "cachaças" (id, regiao, teor_alcoolico, volume, tipo_cachaca, tipo_envelhecimento, tempo_envelhecimento_meses, ano_producao) VALUES
(1, 'Minas Gerais', 40.00, 700, 'BRANCA', 'SEM_ENVELHECIMENTO', 0, 2024),
(2, 'Pernambuco', 39.00, 700, 'BRANCA', 'SEM_ENVELHECIMENTO', 0, 2024),
(3, 'São Paulo', 38.50, 700, 'BRANCA', 'SEM_ENVELHECIMENTO', 0, 2024),
(4, 'Goiás', 40.00, 700, 'OURO', 'ENVELHECIDA', 6, 2024),
(5, 'Rio de Janeiro', 41.00, 700, 'ENVELHECIDA', 'ENVELHECIDA', 6, 2023),
(6, 'Minas Gerais', 42.00, 750, 'PREMIUM', 'PREMIUM', 36, 2021);

-- Dados: users (senha já com hash bcrypt)
INSERT INTO users (id, nome, sobrenome, email, password, role) VALUES
(1, 'Admin', 'Sistema', 'admin@pingou.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyc..Wng4kSa1P.o7nXSJe', 'ADMIN'),
(2, 'João', 'Silva', 'joao.silva@email.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyc..Wng4kSa1P.o7nXSJe', 'USER'),
(3, 'Maria', 'Santos', 'maria.santos@email.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyc..Wng4kSa1P.o7nXSJe', 'USER'),
(4, 'Carlos', 'Oliveira', 'carlos.oliveira@email.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyc..Wng4kSa1P.o7nXSJe', 'USER'),
(5, 'Ana', 'Costa', 'ana.costa@email.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqyc..Wng4kSa1P.o7nXSJe', 'USER');

-- Dados: pacotes
INSERT INTO pacotes (id, nome, descricao, data_entrega, mes, ano, plano_id, ativo) VALUES
(1, 'Pacote Básico - Outubro 2024', 'Descobrindo Minas Gerais', '2024-10-15', 10, 2024, 1, TRUE),
(2, 'Pacote Universitário - Outubro 2024', 'Primeira degustação', '2024-10-15', 10, 2024, 2, TRUE),
(3, 'Pacote Pinguçôe - Outubro 2024', 'Trio Especial', '2024-10-15', 10, 2024, 3, TRUE),
(4, 'Pacote Alambique - Outubro 2024', 'Experiência Premium', '2024-10-15', 10, 2024, 4, TRUE);

-- Dados: item_pacote
INSERT INTO item_pacote (id, pacote_id, produto_id, quantidade, observacoes) VALUES
(1, 1, 1, 1, 'Cachaça mineira tradicional'),
(2, 2, 2, 1, 'Cachaça nordeste branca'),
(3, 3, 1, 1, 'Mineira tradicional'),
(4, 3, 2, 1, 'Nordestina'),
(5, 3, 3, 1, 'Paulista artesanal'),
(6, 4, 4, 1, 'Goiana ouro'),
(7, 4, 5, 1, 'Fluminense premium'),
(8, 4, 6, 1, 'Premium aged 3 anos');

-- Dados: assinaturas
INSERT INTO assinaturas (id, user_id, plano_id, status, data_inicio, data_expiracao) VALUES
(1, 2, 1, 'ATIVA', '2024-10-01', '2024-12-31'),
(2, 3, 2, 'ATIVA', '2024-10-01', '2025-03-31'),
(3, 4, 3, 'ATIVA', '2024-10-01', '2025-09-30'),
(4, 5, 4, 'ATIVA', '2024-09-15', '2025-09-15');

-- Ajuste dos sequences (para continuar inserindo a partir do próximo id)
SELECT setval(pg_get_serial_sequence('planos','id'), COALESCE(MAX(id),0)) FROM planos;
SELECT setval(pg_get_serial_sequence('produtos','id'), COALESCE(MAX(id),0)) FROM produtos;
SELECT setval(pg_get_serial_sequence('users','id'), COALESCE(MAX(id),0)) FROM users;
SELECT setval(pg_get_serial_sequence('pacotes','id'), COALESCE(MAX(id),0)) FROM pacotes;
SELECT setval(pg_get_serial_sequence('item_pacote','id'), COALESCE(MAX(id),0)) FROM item_pacote;
SELECT setval(pg_get_serial_sequence('assinaturas','id'), COALESCE(MAX(id),0)) FROM assinaturas;

COMMIT;

-- Fim do dump

