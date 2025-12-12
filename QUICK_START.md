# 🚀 Quick Start - Sistema de Assinatura de HQs

Guia rápido para começar a usar o sistema.

## 📋 Pré-requisitos

- Java 17+
- Maven 3.6+
- PostgreSQL (opcional, usa H2 por padrão)

## 🔧 Instalação e Execução

### 1. Clone o Repositório
```bash
git clone <repository-url>
cd pingou-PDS
```

### 2. Compile o Projeto
```bash
./mvnw clean install
```

### 3. Execute a Aplicação
```bash
./mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 📚 Acesse a Documentação da API

Após iniciar a aplicação, acesse:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs

## 🎯 Fluxo Básico de Uso

### Passo 1: Inicializar Planos Padrão

```bash
curl -X POST http://localhost:8080/api/hq/planos/inicializar-padrao \
  -H "Authorization: Bearer <admin-token>"
```

### Passo 2: Cadastrar Quadrinhos (Admin)

```bash
curl -X POST http://localhost:8080/api/hq/quadrinhos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <admin-token>" \
  -d '{
    "name": "Amazing Spider-Man #1",
    "description": "A origem do Homem-Aranha",
    "price": 129.90,
    "editora": "MARVEL",
    "tipoHQ": "CLASSICA",
    "edicaoColecionador": true,
    "categorias": ["SUPER_HEROI"],
    "serie": "Amazing Spider-Man",
    "estoque": 10,
    "autor": "Stan Lee",
    "ilustrador": "Steve Ditko"
  }'
```

### Passo 3: Completar Onboarding (Usuário)

```bash
curl -X POST http://localhost:8080/api/hq/preferencias/onboarding \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <user-token>" \
  -d '{
    "categoriasFavoritas": ["SUPER_HEROI", "MANGA"],
    "editorasFavoritas": ["MARVEL", "DC"],
    "preferenciaClassicas": 60,
    "interesseEdicoesColecionador": true,
    "seriesAcompanhadas": ["Amazing Spider-Man", "Batman"]
  }'
```

**Resposta:**
- ✅ Onboarding completo
- 🎁 +100 pontos bonus

### Passo 4: Consultar Planos Disponíveis

```bash
curl -X GET http://localhost:8080/api/hq/planos \
  -H "Authorization: Bearer <user-token>"
```

### Passo 5: Gerar Pacote Curado

```bash
curl -X POST "http://localhost:8080/api/hq/pacotes/gerar?planoId=1&deliveryDate=2025-02-01" \
  -H "Authorization: Bearer <user-token>"
```

**O que acontece:**
1. ✅ Sistema verifica suas preferências
2. ✅ Algoritmo seleciona HQs baseado em:
   - Editoras favoritas
   - Categorias favoritas
   - Distribuição clássicas/modernas
   - Séries acompanhadas
   - Histórico (evita duplicatas)
3. ✅ Pacote é criado
4. ✅ Estoque é atualizado
5. ✅ Pontos são creditados

### Passo 6: Ver Pacote Gerado

```bash
curl -X GET http://localhost:8080/api/hq/pacotes \
  -H "Authorization: Bearer <user-token>"
```

### Passo 7: Consultar Pontos

```bash
curl -X GET http://localhost:8080/api/hq/pontos \
  -H "Authorization: Bearer <user-token>"
```

### Passo 8: Ver Histórico de HQs

```bash
curl -X GET http://localhost:8080/api/hq/pontos/historico \
  -H "Authorization: Bearer <user-token>"
```

### Passo 9: Marcar HQ como Lida

```bash
curl -X PATCH http://localhost:8080/api/hq/pontos/historico/1/marcar-lido \
  -H "Authorization: Bearer <user-token>"
```

**Recompensa:** +50 pontos

### Passo 10: Avaliar HQ

```bash
curl -X POST http://localhost:8080/api/hq/pontos/historico/avaliar \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <user-token>" \
  -d '{
    "historicoId": 1,
    "nota": 5,
    "comentario": "Incrível! Uma das melhores HQs que já li!"
  }'
```

**Recompensa:** +25 pontos

## 🎮 Comandos Úteis

### Listar HQs Disponíveis
```bash
curl -X GET http://localhost:8080/api/hq/quadrinhos/disponiveis
```

### Filtrar por Editora
```bash
curl -X GET http://localhost:8080/api/hq/quadrinhos/editora/MARVEL
```

### Filtrar por Tipo (Clássicas)
```bash
curl -X GET http://localhost:8080/api/hq/quadrinhos/tipo/CLASSICA
```

### Filtrar por Série
```bash
curl -X GET http://localhost:8080/api/hq/quadrinhos/serie/Amazing%20Spider-Man
```

### Ver Edições de Colecionador
```bash
curl -X GET http://localhost:8080/api/hq/quadrinhos/colecionador
```

### Ver Ranking de Pontos
```bash
curl -X GET http://localhost:8080/api/hq/pontos/ranking
```

### Ver Estatísticas de Leitura
```bash
curl -X GET http://localhost:8080/api/hq/pontos/estatisticas \
  -H "Authorization: Bearer <user-token>"
```

## 🔑 Enums Disponíveis

### Editoras
- `MARVEL`
- `DC`
- `IMAGE`
- `DARK_HORSE`
- `IDW`
- `PANINI`
- `VERTIGO`
- `OUTROS`

### Tipos de HQ
- `CLASSICA` - Publicadas há mais de 10 anos
- `MODERNA` - Lançamentos recentes

### Categorias
- `SUPER_HEROI`
- `MANGA`
- `INDEPENDENTE`
- `TERROR`
- `FICCAO_CIENTIFICA`
- `FANTASIA`
- `CRIME`
- `HUMOR`
- `AVENTURA`
- `BIOGRAFIA`

## 📊 Console H2 (Desenvolvimento)

Acesse o console do banco de dados H2:
- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:hqdb`
- Username: `sa`
- Password: (deixar em branco)

## 🐛 Troubleshooting

### Erro: "Usuário não possui preferências"
**Solução:** Complete o onboarding primeiro
```bash
POST /api/hq/preferencias/onboarding
```

### Erro: "Quadrinho sem estoque"
**Solução:** Admin adiciona estoque
```bash
PATCH /api/hq/quadrinhos/{id}/estoque/adicionar?quantidade=10
```

### Erro: "Não foi possível curar HQs"
**Possíveis causas:**
1. Nenhuma HQ disponível no estoque
2. Usuário já recebeu todas as HQs disponíveis
3. Filtros muito restritivos

**Solução:** Cadastrar mais HQs ou ajustar preferências

## 📱 Exemplo de Uso Completo

```bash
# 1. Login (obter token)
TOKEN=$(curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"password123"}' \
  | jq -r '.token')

# 2. Completar onboarding
curl -X POST http://localhost:8080/api/hq/preferencias/onboarding \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "categoriasFavoritas": ["SUPER_HEROI"],
    "editorasFavoritas": ["MARVEL"],
    "preferenciaClassicas": 50,
    "interesseEdicoesColecionador": false
  }'

# 3. Ver planos
curl -X GET http://localhost:8080/api/hq/planos \
  -H "Authorization: Bearer $TOKEN"

# 4. Gerar pacote
curl -X POST "http://localhost:8080/api/hq/pacotes/gerar?planoId=1&deliveryDate=2025-02-01" \
  -H "Authorization: Bearer $TOKEN"

# 5. Ver meus pontos
curl -X GET http://localhost:8080/api/hq/pontos \
  -H "Authorization: Bearer $TOKEN"
```

## 🎓 Próximos Passos

1. Explore a documentação completa em `SISTEMA_HQ_README.md`
2. Acesse o Swagger UI para testar todos os endpoints
3. Customize os planos conforme sua necessidade
4. Implemente integrações com sistemas de pagamento
5. Configure notificações por email

## 💡 Dicas

- 🎯 Complete o onboarding para melhor experiência
- 📚 Adicione séries à lista de acompanhadas para receber continuações
- ⭐ Avalie as HQs para ajustar as recomendações
- 🏆 Acumule pontos para benefícios futuros
- 🔄 Atualize suas preferências conforme seus gostos mudam

## 📞 Suporte

Para dúvidas ou problemas:
- 📧 Email: suporte@hqsystem.com
- 📖 Documentação: `SISTEMA_HQ_README.md`
- 🐛 Issues: GitHub Issues
