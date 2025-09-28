# Testes Unitários - Sistema Pingou

Este diretório contém todos os testes unitários do sistema Pingou, organizados por service e cobrindo cenários de sucesso e erro.

## 📋 Estrutura dos Testes

```
src/test/java/com/pds/pingou/
├── admin/service/
│   └── AdminServiceTest.java
├── assinatura/
│   └── AssinaturaServiceTest.java
├── config/
│   └── TestConfig.java
├── pacote/
│   └── PacoteServiceTest.java
├── planos/
│   └── PlanoServiceTest.java
├── produto/cachaca/
│   └── CachacaServiceTest.java
└── security/auth/
    └── AuthenticationServiceTest.java
```

## 🚀 Como Executar os Testes

### Executar Todos os Testes
```bash
./mvnw test
```

### Executar Testes de um Service Específico
```bash
./mvnw test -Dtest=PlanoServiceTest
./mvnw test -Dtest=AuthenticationServiceTest
./mvnw test -Dtest=AssinaturaServiceTest
./mvnw test -Dtest=CachacaServiceTest
./mvnw test -Dtest=PacoteServiceTest
./mvnw test -Dtest=AdminServiceTest
```

### Executar um Teste Específico
```bash
./mvnw test -Dtest=PlanoServiceTest#deveCriarPlanoComSucesso
```

### Gerar Relatório de Coverage
```bash
./mvnw test jacoco:report
```

## 📊 Estatísticas dos Testes

| Service | Testes | Cenários Sucesso | Cenários Erro |
|---------|--------|------------------|---------------|
| AuthenticationService | 5 | 2 | 3 |
| PlanoService | 8 | 4 | 4 |
| AssinaturaService | 12 | 6 | 6 |
| CachacaService | 12 | 8 | 4 |
| PacoteService | 18 | 11 | 7 |
| AdminService | 8 | 5 | 3 |
| **TOTAL** | **63** | **36** | **27** |

## 🛠️ Tecnologias Utilizadas

- **JUnit 5**: Framework de testes
- **Mockito**: Framework para mocks
- **Spring Boot Test**: Integração com Spring
- **AssertJ**: Assertions fluentes (incluído no spring-boot-starter-test)

## 🔍 Coverage Esperado

Os testes cobrem:
- **Cenários de sucesso**: Fluxos principais
- **Cenários de erro**: Validações e exceções
- **Edge cases**: Listas vazias, valores nulos
- **Validações de negócio**: Regras específicas do domínio
- **Interações**: Verificação de chamadas aos repositórios

## 📚 Exemplos de Execução

### Sucesso ✅
```bash
[INFO] Tests run: 63, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### Com Falha ❌
```bash
[ERROR] Tests run: 63, Failures: 1, Errors: 0, Skipped: 0
[ERROR] deveCriarPlanoComSucesso Time elapsed: 0.05 s <<< FAILURE!
```

---

*Os testes garantem a qualidade e confiabilidade do sistema Pingou! 🧪✨*