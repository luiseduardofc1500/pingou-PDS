# 🎯 Pontos Variáveis Implementados - Guia Prático

## 📍 Localização e Testes no Postman

---

## 1️⃣ Especialização do Domínio do Produto

### 📦 Implementação

**Localização:** `src/main/java/com/subscription/framework/hq/domain/Quadrinho.java`

**Atributos Implementados:**
- ✅ `editora` - Marvel, DC, Image, Dark Horse, IDW, Panini, Vertigo, Outros
- ✅ `tipoHQ` - Clássica (150 pontos base) ou Moderna (100 pontos base)
- ✅ `pontosGanho` - Calculado automaticamente baseado no tipo + multiplicador colecionador
- ✅ `edicaoColecionador` - Edições especiais (multiplicador 1.5x nos pontos)
- ✅ Categorias múltiplas por HQ
- ✅ Sistema de pontos automático

### 🧪 Testes no Postman

#### Cadastrar HQ Clássica de Colecionador
```http
POST http://localhost:8080/api/hq/quadrinhos
Content-Type: application/json
Authorization: Bearer <admin-token>

{
  "name": "Amazing Spider-Man #1",
  "description": "A origem do Homem-Aranha por Stan Lee",
  "price": 129.90,
  "editora": "MARVEL",
  "tipoHQ": "CLASSICA",
  "edicaoColecionador": true,
  "categorias": ["SUPER_HEROI"],
  "serie": "Amazing Spider-Man",
  "estoque": 10,
  "autor": "Stan Lee",
  "ilustrador": "Steve Ditko"
}
```
**Resultado:** Pontos = 150 (clássica) × 1.5 (colecionador) = 225 pontos

#### Filtrar HQs Clássicas
```http
GET http://localhost:8080/api/hq/quadrinhos/tipo/CLASSICA
```

#### Filtrar HQs Modernas
```http
GET http://localhost:8080/api/hq/quadrinhos/tipo/MODERNA
```

#### Filtrar por Editora
```http
GET http://localhost:8080/api/hq/quadrinhos/editora/MARVEL
```

#### Buscar Edições de Colecionador
```http
GET http://localhost:8080/api/hq/quadrinhos/colecionador
```

**✅ Comprovação:** O sistema filtra corretamente por clássicas/modernas e diferencia pontos por tipo.

---

## 2️⃣ Mecanismo de Curadoria e Montagem de Pacotes

### 📦 Implementação

**Localização:** `src/main/java/com/subscription/framework/hq/service/CuradoriaHQService.java`

**Recursos Implementados:**

#### Algoritmo de Curadoria Inteligente
- **Pontuação por editoras favoritas:** Peso 30
- **Pontuação por categorias favoritas:** Peso 40
- **Pontuação por edição colecionador:** Peso 20
- **Pontuação por séries acompanhadas:** Peso 10

#### Verificação de Histórico
- Método: `usuarioJaRecebeuHQ()`
- Previne duplicatas automaticamente
- Query: `findQuadrinhoIdsRecebidosPorUsuario()`

### 🧪 Testes no Postman

#### Passo 1: Completar Onboarding com Preferências
```http
POST http://localhost:8080/api/hq/preferencias/onboarding
Content-Type: application/json
Authorization: Bearer <user-token>

{
  "categoriasFavoritas": ["SUPER_HEROI", "MANGA"],
  "editorasFavoritas": ["MARVEL", "DC"],
  "preferenciaClassicas": 60,
  "interesseEdicoesColecionador": true,
  "seriesAcompanhadas": ["Amazing Spider-Man", "Batman"]
}
```

#### Passo 2: Gerar Pacote Curado
```http
POST http://localhost:8080/api/hq/pacotes/gerar?planoId=1&deliveryDate=2025-02-15
Authorization: Bearer <user-token>
```

**O que acontece nos bastidores:**
1. Sistema busca suas preferências
2. Calcula pontuação de cada HQ disponível
3. Verifica histórico (evita duplicatas)
4. Prioriza séries acompanhadas
5. Distribui 60% clássicas, 40% modernas
6. Seleciona as HQs com maior pontuação

#### Passo 3: Ver Pacote Gerado
```http
GET http://localhost:8080/api/hq/pacotes
Authorization: Bearer <user-token>
```

**Resposta mostra:**
- `quantidadeClassicas`: número de HQs clássicas
- `quantidadeModernas`: número de HQs modernas
- `totalPontos`: soma dos pontos de todas as HQs
- `items`: lista das HQs selecionadas

#### Passo 4: Verificar Histórico (Sem Duplicatas)
```http
GET http://localhost:8080/api/hq/pontos/historico
Authorization: Bearer <user-token>
```

#### Passo 5: Gerar Segundo Pacote
```http
POST http://localhost:8080/api/hq/pacotes/gerar?planoId=1&deliveryDate=2025-03-15
Authorization: Bearer <user-token>
```

**✅ Comprovação:** O segundo pacote NÃO terá nenhuma HQ do primeiro (sistema evita duplicatas automaticamente).

---

## 3️⃣ Regras de Cadastro de Novos Usuários

### 📦 Implementação

**Localização:** 
- Controller: `src/main/java/com/subscription/framework/hq/controller/PreferenciasController.java`
- Service: `src/main/java/com/subscription/framework/hq/service/PreferenciasService.java`
- Entity: `src/main/java/com/subscription/framework/hq/domain/UsuarioPreferencias.java`

**Formulário de Onboarding:**
- Categorias favoritas (obrigatório)
- Editoras favoritas (obrigatório)
- Preferência clássicas 0-100% (obrigatório)
- Interesse em edições de colecionador
- Séries acompanhadas

### 🧪 Testes no Postman

#### Quiz Completo de Onboarding
```http
POST http://localhost:8080/api/hq/preferencias/onboarding
Content-Type: application/json
Authorization: Bearer <user-token>

{
  "categoriasFavoritas": ["SUPER_HEROI", "MANGA", "TERROR"],
  "editorasFavoritas": ["MARVEL", "DC", "PANINI"],
  "preferenciaClassicas": 70,
  "interesseEdicoesColecionador": true,
  "seriesAcompanhadas": ["Amazing Spider-Man", "Batman", "One Piece"]
}
```

**Resultado:**
- ✅ Preferências salvas
- ✅ +100 pontos bonus creditados
- ✅ `onboardingCompleto: true`

#### Verificar Status do Onboarding
```http
GET http://localhost:8080/api/hq/preferencias/onboarding/status
Authorization: Bearer <user-token>
```

**Resposta:** `true` (se completou) ou `false` (se não completou)

#### Ver Preferências Salvas
```http
GET http://localhost:8080/api/hq/preferencias
Authorization: Bearer <user-token>
```

#### Atualizar Preferência de Clássicas
```http
PUT http://localhost:8080/api/hq/preferencias/classicas?percentual=80
Authorization: Bearer <user-token>
```

#### Adicionar Série Acompanhada
```http
POST http://localhost:8080/api/hq/preferencias/series?serie=X-Men
Authorization: Bearer <user-token>
```

**✅ Comprovação:** Sistema utiliza essas preferências na curadoria automática.

---

## 4️⃣ Estrutura e Composição do Plano

### 📦 Implementação

**Localização:**
- Entity: `src/main/java/com/subscription/framework/hq/domain/PlanoHQ.java`
- Service: `src/main/java/com/subscription/framework/hq/service/PlanoHQService.java`
- Repository: `src/main/java/com/subscription/framework/hq/repository/PlanoHQRepository.java`

**Filosofia de Curadoria por Plano:**

| Plano | Clássicas | Modernas | Multiplicador | Preço |
|-------|-----------|----------|---------------|-------|
| HQ Básico | 50% | 50% | 1.0x | R$ 39,90 |
| HQ Clássico | 80% | 20% | 1.5x | R$ 59,90 |
| HQ Moderno | 20% | 80% | 1.2x | R$ 49,90 |
| HQ Colecionador Premium | 60% | 40% | 2.0x | R$ 149,90 |

### 🧪 Testes no Postman

#### Inicializar Planos Padrão
```http
POST http://localhost:8080/api/hq/planos/inicializar-padrao
Authorization: Bearer <admin-token>
```

**Resultado:** Cria os 4 planos com configurações diferentes

#### Listar Todos os Planos
```http
GET http://localhost:8080/api/hq/planos
```

**Resposta mostra:**
```json
[
  {
    "id": 1,
    "name": "HQ Básico",
    "percentualClassicas": 50,
    "percentualModernas": 50,
    "multiplicadorPontos": 1.0,
    "filosofiaCuradoria": "Mix equilibrado para quem quer conhecer diversos estilos",
    "maxItemsPerDelivery": 3
  },
  {
    "id": 2,
    "name": "HQ Clássico",
    "percentualClassicas": 80,
    "percentualModernas": 20,
    "multiplicadorPontos": 1.5,
    "filosofiaCuradoria": "Para apreciadores das obras que marcaram época",
    "maxItemsPerDelivery": 5
  }
  // ... outros planos
]
```

#### Buscar Planos para Colecionadores
```http
GET http://localhost:8080/api/hq/planos/colecionador
```

**Resultado:** Retorna apenas planos com `planoColecionador: true` e `incluiEdicoesColecionador: true`

#### Teste Prático: Gerar Pacote com Plano Clássico
```http
POST http://localhost:8080/api/hq/pacotes/gerar?planoId=2&deliveryDate=2025-02-01
Authorization: Bearer <user-token>
```

**Resultado Esperado:**
- 5 HQs no pacote (conforme `maxItemsPerDelivery`)
- 4 HQs clássicas (80%)
- 1 HQ moderna (20%)
- Pontos multiplicados por 1.5x

#### Teste Prático: Gerar Pacote com Plano Moderno
```http
POST http://localhost:8080/api/hq/pacotes/gerar?planoId=3&deliveryDate=2025-02-01
Authorization: Bearer <user-token>
```

**Resultado Esperado:**
- 4 HQs no pacote
- 1 HQ clássica (20%)
- 3 HQs modernas (80%)
- Pontos multiplicados por 1.2x

**✅ Comprovação:** Cada plano gera pacotes com composição diferente e valores de pontos distintos.

---

## 🎮 Sistema de Pontos e Gamificação

### 📦 Implementação

**Localização:** `src/main/java/com/subscription/framework/hq/service/PontosService.java`

### 🧪 Testes no Postman

#### Ver Meus Pontos
```http
GET http://localhost:8080/api/hq/pontos
Authorization: Bearer <user-token>
```

**Resposta:**
```json
{
  "pontosTotais": 1150,
  "pontosDisponiveis": 1150,
  "pontosUtilizados": 0,
  "nivel": 2,
  "pontosParaProximoNivel": 850
}
```

#### Marcar HQ como Lida (+50 pontos)
```http
PATCH http://localhost:8080/api/hq/pontos/historico/1/marcar-lido
Authorization: Bearer <user-token>
```

#### Avaliar HQ (+25 pontos)
```http
POST http://localhost:8080/api/hq/pontos/historico/avaliar
Content-Type: application/json
Authorization: Bearer <user-token>

{
  "historicoId": 1,
  "nota": 5,
  "comentario": "Incrível! Uma obra-prima!"
}
```

#### Ver Ranking de Pontos
```http
GET http://localhost:8080/api/hq/pontos/ranking
```

#### Ver Estatísticas de Leitura
```http
GET http://localhost:8080/api/hq/pontos/estatisticas
Authorization: Bearer <user-token>
```

---

## 📋 Collection Postman - Fluxo Completo de Teste

### Passo 1: Setup Inicial (Admin)
```http
# 1.1 - Criar planos
POST http://localhost:8080/api/hq/planos/inicializar-padrao

# 1.2 - Cadastrar HQs Clássicas
POST http://localhost:8080/api/hq/quadrinhos
{
  "name": "Amazing Spider-Man #1",
  "editora": "MARVEL",
  "tipoHQ": "CLASSICA",
  "edicaoColecionador": true,
  "categorias": ["SUPER_HEROI"],
  "estoque": 10,
  "price": 129.90
}

# 1.3 - Cadastrar HQs Modernas
POST http://localhost:8080/api/hq/quadrinhos
{
  "name": "Miles Morales #1",
  "editora": "MARVEL",
  "tipoHQ": "MODERNA",
  "edicaoColecionador": false,
  "categorias": ["SUPER_HEROI"],
  "estoque": 20,
  "price": 29.90
}
```

### Passo 2: Onboarding do Usuário
```http
# 2.1 - Completar quiz
POST http://localhost:8080/api/hq/preferencias/onboarding
{
  "categoriasFavoritas": ["SUPER_HEROI"],
  "editorasFavoritas": ["MARVEL"],
  "preferenciaClassicas": 60,
  "interesseEdicoesColecionador": true
}

# 2.2 - Verificar pontos (deve ter +100)
GET http://localhost:8080/api/hq/pontos
```

### Passo 3: Testar Curadoria
```http
# 3.1 - Ver planos disponíveis
GET http://localhost:8080/api/hq/planos

# 3.2 - Gerar pacote com plano clássico (80% clássicas)
POST http://localhost:8080/api/hq/pacotes/gerar?planoId=2&deliveryDate=2025-02-01

# 3.3 - Ver pacote gerado
GET http://localhost:8080/api/hq/pacotes

# 3.4 - Ver histórico (deve mostrar as HQs)
GET http://localhost:8080/api/hq/pontos/historico
```

### Passo 4: Testar Gamificação
```http
# 4.1 - Marcar HQ como lida
PATCH http://localhost:8080/api/hq/pontos/historico/1/marcar-lido

# 4.2 - Avaliar HQ
POST http://localhost:8080/api/hq/pontos/historico/avaliar
{
  "historicoId": 1,
  "nota": 5,
  "comentario": "Excelente!"
}

# 4.3 - Conferir pontos (deve ter aumentado)
GET http://localhost:8080/api/hq/pontos
```

### Passo 5: Testar Prevenção de Duplicatas
```http
# 5.1 - Gerar segundo pacote
POST http://localhost:8080/api/hq/pacotes/gerar?planoId=2&deliveryDate=2025-03-01

# 5.2 - Ver novo pacote (não deve ter HQs repetidas)
GET http://localhost:8080/api/hq/pacotes

# 5.3 - Confirmar histórico completo
GET http://localhost:8080/api/hq/pontos/historico
```

---

## 🎯 Checklist de Validação

### ✅ Ponto 1: Especialização do Produto
- [ ] HQs clássicas dão 150 pontos base
- [ ] HQs modernas dão 100 pontos base
- [ ] Edições colecionador têm multiplicador 1.5x
- [ ] Filtros por editora funcionam
- [ ] Filtros por tipo funcionam

### ✅ Ponto 2: Curadoria e Montagem
- [ ] Sistema verifica histórico antes de selecionar HQs
- [ ] Não há duplicatas entre pacotes
- [ ] Curadoria considera preferências do usuário
- [ ] Séries acompanhadas são priorizadas
- [ ] Distribuição clássicas/modernas respeita o plano

### ✅ Ponto 3: Onboarding
- [ ] Quiz coleta categorias favoritas
- [ ] Quiz coleta editoras favoritas
- [ ] Quiz coleta preferência clássicas/modernas
- [ ] Usuário ganha 100 pontos ao completar
- [ ] Preferências são usadas na curadoria

### ✅ Ponto 4: Planos
- [ ] Plano Básico: 50/50
- [ ] Plano Clássico: 80/20
- [ ] Plano Moderno: 20/80
- [ ] Plano Premium: 60/40 + edições colecionador
- [ ] Multiplicadores de pontos funcionam
- [ ] Cada plano gera pacotes diferentes

---

## 📚 Referências Rápidas

### Enums Disponíveis

**Editoras:**
`MARVEL`, `DC`, `IMAGE`, `DARK_HORSE`, `IDW`, `PANINI`, `VERTIGO`, `OUTROS`

**Tipos:**
`CLASSICA`, `MODERNA`

**Categorias:**
`SUPER_HEROI`, `MANGA`, `INDEPENDENTE`, `TERROR`, `FICCAO_CIENTIFICA`, `FANTASIA`, `CRIME`, `HUMOR`, `AVENTURA`, `BIOGRAFIA`

### Base URLs
- API: `http://localhost:8080`
- Swagger: `http://localhost:8080/swagger-ui.html`
- H2 Console: `http://localhost:8080/h2-console`

---

## 🐛 Troubleshooting

**Erro: "Usuário não possui preferências"**
→ Complete o onboarding primeiro

**Erro: "Nenhuma HQ disponível"**
→ Cadastre HQs ou já recebeu todas disponíveis

**Pacotes vazios ou incompletos**
→ Verifique estoque das HQs

**Pontos não aumentam**
→ Verifique se marcou como lida/avaliou corretamente
