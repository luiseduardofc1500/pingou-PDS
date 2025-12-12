# Sistema de Assinatura de HQs

Sistema completo de assinatura de quadrinhos (HQs) desenvolvido sobre o framework de assinaturas. O sistema implementa curadoria inteligente, gamificação com pontos e personalização baseada em preferências do usuário.

## 🎯 Funcionalidades Principais

### 1. Especialização do Domínio do Produto

#### Entidade Quadrinho (HQ)
- **Atributos principais:**
  - `editora`: Marvel, DC, Image, Dark Horse, IDW, Panini, Vertigo, etc.
  - `tipoHQ`: Clássica (mais de 10 anos) ou Moderna (recente)
  - `pontosGanho`: Sistema de pontos baseado no tipo e raridade
  - `edicaoColecionador`: Indica edições especiais para colecionadores
  - `categorias`: Super Herói, Manga, Independente, Terror, etc.
  - `serie`: Rastreamento de séries para continuidade
  - `estoque`: Gerenciamento de inventário

#### Sistema de Pontos
- HQs Clássicas: 150 pontos base
- HQs Modernas: 100 pontos base
- Edições de Colecionador: Multiplicador de 1.5x
- Planos premium aplicam multiplicadores adicionais

### 2. Mecanismo de Curadoria e Montagem de Pacotes

#### Curadoria Inteligente por Preferências
O algoritmo de curadoria (`CuradoriaHQService`) considera:

1. **Histórico do Usuário**
   - Verifica todas as HQs já recebidas
   - Previne duplicatas automaticamente
   - Rastreia padrões de leitura e avaliação

2. **Preferências Personalizadas**
   - Editoras favoritas (peso: 30 pontos)
   - Categorias favoritas (peso: 40 pontos)
   - Interesse em edições de colecionador (peso: 20 pontos)
   - Séries acompanhadas (peso: 10 pontos)

3. **Distribuição Clássicas vs Modernas**
   - Percentual configurável por usuário (0-100%)
   - Aplicado automaticamente na montagem do pacote

4. **Continuidade de Séries**
   - Prioriza próximos volumes de séries acompanhadas
   - Substitui HQs de menor relevância quando disponível

### 3. Regras de Cadastro e Onboarding

#### Quiz de Preferências
Endpoint: `POST /api/hq/preferencias/onboarding`

Coleta as seguintes informações:
```json
{
  "categoriasFavoritas": ["SUPER_HEROI", "MANGA"],
  "editorasFavoritas": ["MARVEL", "DC"],
  "preferenciaClassicas": 50,
  "interesseEdicoesColecionador": true,
  "seriesAcompanhadas": ["Amazing Spider-Man", "Batman"]
}
```

**Recompensa:** 100 pontos bonus por completar o onboarding

### 4. Estrutura e Composição do Plano

#### Planos Disponíveis

**1. HQ Básico** (R$ 39,90/mês)
- 3 HQs por mês
- 50% clássicas, 50% modernas
- Multiplicador de pontos: 1.0x
- Filosofia: Mix equilibrado para iniciantes

**2. HQ Clássico** (R$ 59,90/mês)
- 5 HQs por mês
- 80% clássicas, 20% modernas
- Multiplicador de pontos: 1.5x
- Filosofia: Para apreciadores de obras icônicas

**3. HQ Moderno** (R$ 49,90/mês)
- 4 HQs por mês
- 20% clássicas, 80% modernas
- Multiplicador de pontos: 1.2x
- Filosofia: Histórias recentes e inovadoras

**4. HQ Colecionador Premium** (R$ 149,90/mês)
- 6 HQs por mês
- 60% clássicas, 40% modernas
- Inclui edições especiais
- Multiplicador de pontos: 2.0x
- Filosofia: Curadoria premium com edições raras

## 🎮 Sistema de Gamificação

### Ganho de Pontos

1. **Recebimento de HQs**
   - Pontos variam por tipo e edição
   - Multiplicados pelo fator do plano

2. **Marcar HQ como Lida**
   - Bonus: +50 pontos
   - Endpoint: `PATCH /api/hq/pontos/historico/{id}/marcar-lido`

3. **Avaliar HQ**
   - Bonus: +25 pontos
   - Endpoint: `POST /api/hq/pontos/historico/avaliar`

4. **Completar Onboarding**
   - Bonus: +100 pontos

### Níveis
- Cada 1000 pontos = 1 nível
- Ranking disponível em: `GET /api/hq/pontos/ranking`

### Estatísticas de Leitura
Endpoint: `GET /api/hq/pontos/estatisticas`

Retorna:
- Total de HQs recebidas
- Total de HQs lidas
- Percentual de leitura

## 📚 API Endpoints

### Quadrinhos
```
POST   /api/hq/quadrinhos              - Criar quadrinho (ADMIN)
GET    /api/hq/quadrinhos              - Listar todos
GET    /api/hq/quadrinhos/disponiveis  - Listar disponíveis
GET    /api/hq/quadrinhos/{id}         - Buscar por ID
GET    /api/hq/quadrinhos/editora/{editora} - Filtrar por editora
GET    /api/hq/quadrinhos/tipo/{tipo}  - Filtrar por tipo
GET    /api/hq/quadrinhos/serie/{serie} - Filtrar por série
PUT    /api/hq/quadrinhos/{id}         - Atualizar (ADMIN)
DELETE /api/hq/quadrinhos/{id}         - Deletar (ADMIN)
```

### Preferências
```
POST   /api/hq/preferencias/onboarding    - Completar onboarding
GET    /api/hq/preferencias               - Buscar minhas preferências
GET    /api/hq/preferencias/onboarding/status - Verificar status
PUT    /api/hq/preferencias/categorias    - Atualizar categorias
PUT    /api/hq/preferencias/editoras      - Atualizar editoras
PUT    /api/hq/preferencias/classicas     - Atualizar preferência clássicas
POST   /api/hq/preferencias/series        - Adicionar série
DELETE /api/hq/preferencias/series        - Remover série
```

### Pontos e Gamificação
```
GET    /api/hq/pontos                     - Consultar meus pontos
GET    /api/hq/pontos/historico           - Histórico de HQs
GET    /api/hq/pontos/historico/nao-lidas - HQs não lidas
PATCH  /api/hq/pontos/historico/{id}/marcar-lido - Marcar como lida
POST   /api/hq/pontos/historico/avaliar   - Avaliar HQ
GET    /api/hq/pontos/estatisticas        - Estatísticas de leitura
GET    /api/hq/pontos/ranking             - Ranking de usuários
```

### Planos
```
GET    /api/hq/planos                     - Listar planos ativos
GET    /api/hq/planos/{id}                - Buscar plano por ID
GET    /api/hq/planos/colecionador        - Planos para colecionadores
POST   /api/hq/planos/inicializar-padrao  - Criar planos padrão (ADMIN)
```

### Pacotes
```
POST   /api/hq/pacotes/gerar              - Gerar pacote curado
GET    /api/hq/pacotes                    - Listar meus pacotes
GET    /api/hq/pacotes/ativos             - Pacotes ativos
GET    /api/hq/pacotes/{id}               - Buscar pacote por ID
DELETE /api/hq/pacotes/{id}               - Cancelar pacote
```

## 🗄️ Modelo de Dados

### Principais Entidades

1. **Quadrinho** (extends Product)
   - Informações da HQ
   - Categorias (many-to-many)
   - Gestão de estoque

2. **UsuarioPreferencias**
   - Categorias favoritas
   - Editoras favoritas
   - Preferência clássicas/modernas
   - Séries acompanhadas

3. **UsuarioHistoricoHQ**
   - Histórico de recebimento
   - Status de leitura
   - Avaliações

4. **UsuarioPontos**
   - Pontos totais
   - Pontos disponíveis
   - Nível do usuário

5. **PlanoHQ** (extends Plan)
   - Percentuais de distribuição
   - Multiplicador de pontos
   - Filosofia de curadoria

6. **PacoteHQ** (extends Package)
   - Curadoria personalizada
   - Contadores de clássicas/modernas
   - Total de pontos do pacote

## 🚀 Como Usar

### 1. Inicializar o Sistema
```bash
# Criar planos padrão
POST /api/hq/planos/inicializar-padrao
```

### 2. Cadastrar HQs (Admin)
```bash
POST /api/hq/quadrinhos
{
  "name": "Amazing Spider-Man #1",
  "description": "A origem do Homem-Aranha",
  "price": 29.90,
  "editora": "MARVEL",
  "tipoHQ": "CLASSICA",
  "edicaoColecionador": true,
  "categorias": ["SUPER_HEROI"],
  "serie": "Amazing Spider-Man",
  "estoque": 10
}
```

### 3. Usuário Completa Onboarding
```bash
POST /api/hq/preferencias/onboarding
{
  "categoriasFavoritas": ["SUPER_HEROI", "MANGA"],
  "editorasFavoritas": ["MARVEL", "DC"],
  "preferenciaClassicas": 50,
  "interesseEdicoesColecionador": true
}
```

### 4. Gerar Pacote Curado
```bash
POST /api/hq/pacotes/gerar?planoId=1&deliveryDate=2025-01-15
```

### 5. Interagir com HQs
```bash
# Marcar como lida
PATCH /api/hq/pontos/historico/1/marcar-lido

# Avaliar
POST /api/hq/pontos/historico/avaliar
{
  "historicoId": 1,
  "nota": 5,
  "comentario": "Incrível!"
}
```

## 🎨 Enums Disponíveis

### Editora
`MARVEL`, `DC`, `IMAGE`, `DARK_HORSE`, `IDW`, `PANINI`, `VERTIGO`, `OUTROS`

### TipoHQ
`CLASSICA`, `MODERNA`

### CategoriaHQ
`SUPER_HEROI`, `MANGA`, `INDEPENDENTE`, `TERROR`, `FICCAO_CIENTIFICA`, `FANTASIA`, `CRIME`, `HUMOR`, `AVENTURA`, `BIOGRAFIA`

## 🔧 Tecnologias Utilizadas

- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- PostgreSQL / H2
- Lombok
- Swagger/OpenAPI
- Spring Security

## 📝 Notas de Implementação

1. **Curadoria Automática**: O sistema evita duplicatas verificando o histórico antes de selecionar HQs
2. **Sistema de Pontos**: Integrado com o recebimento de pacotes
3. **Preferências Dinâmicas**: Usuários podem atualizar preferências a qualquer momento
4. **Gestão de Estoque**: Estoque é automaticamente reduzido ao gerar pacotes
5. **Cancelamento**: Pacotes podem ser cancelados devolvendo o estoque

## 🔐 Segurança

- Endpoints administrativos protegidos com `@PreAuthorize("hasRole('ADMIN')")`
- Usuários só podem acessar seus próprios dados
- Validação de entrada com Bean Validation
- TODO: Implementar extração correta do userId do JWT

## 📈 Próximos Passos

1. Integrar com sistema de pagamento
2. Scheduler para geração automática mensal de pacotes
3. Sistema de recomendações baseado em ML
4. Notificações por email/push
5. Sistema de troca de HQs entre usuários
6. Programa de fidelidade com uso de pontos
