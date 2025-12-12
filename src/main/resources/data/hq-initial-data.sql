-- Script de inicialização do Sistema de Assinatura de HQs

-- ========================================
-- Dados de exemplo de Quadrinhos (HQs)
-- ========================================

-- Marvel - Clássicas
INSERT INTO products (product_type, name, description, price, editora, tipo_hq, pontos_ganho, edicao_colecionador, active, estoque, serie, autor, ilustrador, categoria, sku)
VALUES 
('QUADRINHO', 'Amazing Spider-Man #1', 'A origem do Homem-Aranha por Stan Lee e Steve Ditko', 129.90, 'MARVEL', 'CLASSICA', 225, true, true, 5, 'Amazing Spider-Man', 'Stan Lee', 'Steve Ditko', 'SUPER_HEROI', 'ASM-001'),
('QUADRINHO', 'X-Men #1', 'A primeira aparição dos X-Men', 149.90, 'MARVEL', 'CLASSICA', 225, true, true, 3, 'X-Men', 'Stan Lee', 'Jack Kirby', 'SUPER_HEROI', 'XMN-001'),
('QUADRINHO', 'Fantastic Four #1', 'A origem do Quarteto Fantástico', 159.90, 'MARVEL', 'CLASSICA', 225, true, true, 2, 'Fantastic Four', 'Stan Lee', 'Jack Kirby', 'SUPER_HEROI', 'FF-001'),
('QUADRINHO', 'Daredevil: O Homem Sem Medo', 'Saga clássica do Demolidor', 39.90, 'MARVEL', 'CLASSICA', 150, false, true, 15, 'Daredevil', 'Frank Miller', 'Frank Miller', 'SUPER_HEROI', 'DD-MSM'),
('QUADRINHO', 'Vingadores: A Saga de Ultron', 'Clássica batalha contra Ultron', 44.90, 'MARVEL', 'CLASSICA', 150, false, true, 12, 'Vingadores', 'Roy Thomas', 'John Buscema', 'SUPER_HEROI', 'VNG-ULT');

-- Marvel - Modernas
INSERT INTO products (product_type, name, description, price, editora, tipo_hq, pontos_ganho, edicao_colecionador, active, estoque, serie, autor, ilustrador, categoria, sku)
VALUES 
('QUADRINHO', 'Miles Morales: Spider-Man #1', 'O novo Homem-Aranha em ação', 29.90, 'MARVEL', 'MODERNA', 100, false, true, 25, 'Miles Morales: Spider-Man', 'Saladin Ahmed', 'Javier Garrón', 'SUPER_HEROI', 'MM-SM-001'),
('QUADRINHO', 'Immortal Hulk Vol. 1', 'Hulk como nunca visto antes', 34.90, 'MARVEL', 'MODERNA', 100, false, true, 20, 'Immortal Hulk', 'Al Ewing', 'Joe Bennett', 'SUPER_HEROI', 'IH-V1'),
('QUADRINHO', 'Thor: God of Thunder', 'Thor enfrentando Gorr', 32.90, 'MARVEL', 'MODERNA', 100, false, true, 18, 'Thor', 'Jason Aaron', 'Esad Ribić', 'SUPER_HEROI', 'THR-GOT'),
('QUADRINHO', 'Ms. Marvel Vol. 1', 'A origem de Kamala Khan', 29.90, 'MARVEL', 'MODERNA', 100, false, true, 22, 'Ms. Marvel', 'G. Willow Wilson', 'Adrian Alphona', 'SUPER_HEROI', 'MSM-V1');

-- DC - Clássicas
INSERT INTO products (product_type, name, description, price, editora, tipo_hq, pontos_ganho, edicao_colecionador, active, estoque, serie, autor, ilustrador, categoria, sku)
VALUES 
('QUADRINHO', 'Batman: O Cavaleiro das Trevas', 'Obra-prima de Frank Miller', 89.90, 'DC', 'CLASSICA', 225, true, true, 8, 'Batman', 'Frank Miller', 'Frank Miller', 'SUPER_HEROI', 'BTM-DKR'),
('QUADRINHO', 'Watchmen', 'A graphic novel definitiva', 79.90, 'DC', 'CLASSICA', 225, true, true, 10, 'Watchmen', 'Alan Moore', 'Dave Gibbons', 'SUPER_HEROI', 'WTM-001'),
('QUADRINHO', 'Crise nas Infinitas Terras', 'O evento que mudou a DC', 69.90, 'DC', 'CLASSICA', 150, false, true, 12, 'Crise', 'Marv Wolfman', 'George Pérez', 'SUPER_HEROI', 'CRS-INF'),
('QUADRINHO', 'Superman: Entre a Foice e o Martelo', 'Superman na Guerra Fria', 42.90, 'DC', 'CLASSICA', 150, false, true, 15, 'Superman', 'Mark Millar', 'Dave Johnson', 'SUPER_HEROI', 'SUP-SFM');

-- DC - Modernas
INSERT INTO products (product_type, name, description, price, editora, tipo_hq, pontos_ganho, edicao_colecionador, active, estoque, serie, autor, ilustrador, categoria, sku)
VALUES 
('QUADRINHO', 'Batman: Corte das Corujas', 'Scott Snyder revoluciona Batman', 34.90, 'DC', 'MODERNA', 100, false, true, 20, 'Batman', 'Scott Snyder', 'Greg Capullo', 'SUPER_HEROI', 'BTM-CRT'),
('QUADRINHO', 'Flash: Renascimento', 'O retorno de Barry Allen', 32.90, 'DC', 'MODERNA', 100, false, true, 18, 'Flash', 'Geoff Johns', 'Ethan Van Sciver', 'SUPER_HEROI', 'FLS-RNS'),
('QUADRINHO', 'Aquaman Vol. 1: O Abismo', 'Aquaman repaginado', 29.90, 'DC', 'MODERNA', 100, false, true, 22, 'Aquaman', 'Geoff Johns', 'Ivan Reis', 'SUPER_HEROI', 'AQM-V1');

-- Independentes
INSERT INTO products (product_type, name, description, price, editora, tipo_hq, pontos_ganho, edicao_colecionador, active, estoque, serie, autor, ilustrador, categoria, sku)
VALUES 
('QUADRINHO', 'Saga Vol. 1', 'Space opera épica', 34.90, 'IMAGE', 'MODERNA', 100, false, true, 20, 'Saga', 'Brian K. Vaughan', 'Fiona Staples', 'FICCAO_CIENTIFICA', 'SAG-V1'),
('QUADRINHO', 'The Walking Dead Vol. 1', 'O início do apocalipse zumbi', 32.90, 'IMAGE', 'MODERNA', 100, false, true, 25, 'The Walking Dead', 'Robert Kirkman', 'Tony Moore', 'TERROR', 'TWD-V1'),
('QUADRINHO', 'Invincible Vol. 1', 'Novo herói, nova perspectiva', 29.90, 'IMAGE', 'MODERNA', 100, false, true, 18, 'Invincible', 'Robert Kirkman', 'Cory Walker', 'SUPER_HEROI', 'INV-V1'),
('QUADRINHO', 'Sandman Vol. 1: Prelúdios e Noturnos', 'A obra-prima de Neil Gaiman', 44.90, 'VERTIGO', 'CLASSICA', 150, false, true, 15, 'Sandman', 'Neil Gaiman', 'Sam Kieth', 'FANTASIA', 'SND-V1');

-- Mangás
INSERT INTO products (product_type, name, description, price, editora, tipo_hq, pontos_ganho, edicao_colecionador, active, estoque, serie, autor, ilustrador, categoria, sku)
VALUES 
('QUADRINHO', 'One Piece Vol. 1', 'A jornada de Luffy começa', 24.90, 'PANINI', 'MODERNA', 100, false, true, 30, 'One Piece', 'Eiichiro Oda', 'Eiichiro Oda', 'MANGA', 'OP-V1'),
('QUADRINHO', 'Attack on Titan Vol. 1', 'A humanidade sob cerco', 26.90, 'PANINI', 'MODERNA', 100, false, true, 28, 'Attack on Titan', 'Hajime Isayama', 'Hajime Isayama', 'MANGA', 'AOT-V1'),
('QUADRINHO', 'Death Note Vol. 1', 'O caderno da morte', 22.90, 'PANINI', 'MODERNA', 100, false, true, 25, 'Death Note', 'Tsugumi Ohba', 'Takeshi Obata', 'MANGA', 'DN-V1');

-- ========================================
-- Categorias dos Quadrinhos
-- ========================================

-- Adiciona categorias múltiplas para alguns quadrinhos
INSERT INTO quadrinho_categorias (quadrinho_id, categoria) VALUES
-- Watchmen tem múltiplas categorias
((SELECT id FROM products WHERE sku = 'WTM-001'), 'SUPER_HEROI'),
((SELECT id FROM products WHERE sku = 'WTM-001'), 'CRIME'),
((SELECT id FROM products WHERE sku = 'WTM-001'), 'FICCAO_CIENTIFICA'),

-- Sandman
((SELECT id FROM products WHERE sku = 'SND-V1'), 'FANTASIA'),
((SELECT id FROM products WHERE sku = 'SND-V1'), 'TERROR'),

-- The Walking Dead
((SELECT id FROM products WHERE sku = 'TWD-V1'), 'TERROR'),
((SELECT id FROM products WHERE sku = 'TWD-V1'), 'AVENTURA');

-- ========================================
-- Exemplo de Preferências de Usuário
-- ========================================

-- Usuário 1: Fã de Marvel e Super Heróis
INSERT INTO usuario_preferencias (user_id, preferencia_classicas, interesse_edicoes_colecionador, onboarding_completo, created_at)
VALUES (1, 60, true, true, NOW());

INSERT INTO usuario_categorias_favoritas (preferencia_id, categoria) VALUES
((SELECT id FROM usuario_preferencias WHERE user_id = 1), 'SUPER_HEROI'),
((SELECT id FROM usuario_preferencias WHERE user_id = 1), 'FICCAO_CIENTIFICA');

INSERT INTO usuario_editoras_favoritas (preferencia_id, editora) VALUES
((SELECT id FROM usuario_preferencias WHERE user_id = 1), 'MARVEL'),
((SELECT id FROM usuario_preferencias WHERE user_id = 1), 'DC');

INSERT INTO usuario_series_acompanhadas (preferencia_id, serie) VALUES
((SELECT id FROM usuario_preferencias WHERE user_id = 1), 'Amazing Spider-Man'),
((SELECT id FROM usuario_preferencias WHERE user_id = 1), 'Batman');

-- Usuário 2: Fã de Mangás
INSERT INTO usuario_preferencias (user_id, preferencia_classicas, interesse_edicoes_colecionador, onboarding_completo, created_at)
VALUES (2, 20, false, true, NOW());

INSERT INTO usuario_categorias_favoritas (preferencia_id, categoria) VALUES
((SELECT id FROM usuario_preferencias WHERE user_id = 2), 'MANGA'),
((SELECT id FROM usuario_preferencias WHERE user_id = 2), 'AVENTURA');

INSERT INTO usuario_editoras_favoritas (preferencia_id, editora) VALUES
((SELECT id FROM usuario_preferencias WHERE user_id = 2), 'PANINI');

-- ========================================
-- Inicializar Pontos dos Usuários
-- ========================================

INSERT INTO usuario_pontos (user_id, pontos_totais, pontos_disponiveis, pontos_utilizados, nivel, created_at)
VALUES 
(1, 100, 100, 0, 1, NOW()),
(2, 100, 100, 0, 1, NOW());

-- ========================================
-- Criar Planos de HQ
-- ========================================

-- Plano Básico
INSERT INTO plans (plan_type, name, description, price, max_items_per_delivery, delivery_frequency, tier, active, 
                   percentual_classicas, percentual_modernas, multiplicador_pontos, filosofia_curadoria, plano_colecionador, inclui_edicoes_colecionador)
VALUES ('HQ', 'HQ Básico', 'Plano equilibrado com mix de clássicas e modernas', 39.90, 3, 'MONTHLY', 'BASIC', true,
        50, 50, 1.0, 'Mix equilibrado para quem quer conhecer diversos estilos', false, false);

-- Plano Clássico
INSERT INTO plans (plan_type, name, description, price, max_items_per_delivery, delivery_frequency, tier, active,
                   percentual_classicas, percentual_modernas, multiplicador_pontos, filosofia_curadoria, plano_colecionador, inclui_edicoes_colecionador)
VALUES ('HQ', 'HQ Clássico', 'Foco em quadrinhos clássicos icônicos', 59.90, 5, 'MONTHLY', 'PREMIUM', true,
        80, 20, 1.5, 'Para apreciadores das obras que marcaram época', false, false);

-- Plano Moderno
INSERT INTO plans (plan_type, name, description, price, max_items_per_delivery, delivery_frequency, tier, active,
                   percentual_classicas, percentual_modernas, multiplicador_pontos, filosofia_curadoria, plano_colecionador, inclui_edicoes_colecionador)
VALUES ('HQ', 'HQ Moderno', 'As melhores HQs contemporâneas', 49.90, 4, 'MONTHLY', 'STANDARD', true,
        20, 80, 1.2, 'Descubra as histórias mais recentes e inovadoras', false, false);

-- Plano Colecionador Premium
INSERT INTO plans (plan_type, name, description, price, max_items_per_delivery, delivery_frequency, tier, active,
                   percentual_classicas, percentual_modernas, multiplicador_pontos, filosofia_curadoria, plano_colecionador, inclui_edicoes_colecionador)
VALUES ('HQ', 'HQ Colecionador Premium', 'Edições especiais e de colecionador', 149.90, 6, 'MONTHLY', 'PREMIUM', true,
        60, 40, 2.0, 'Curadoria premium com edições raras e especiais', true, true);

COMMIT;
