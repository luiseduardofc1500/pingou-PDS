# Subscription Framework - Documentação de Arquitetura

## Visão Geral

O **Subscription Framework** é um framework genérico para sistemas de assinatura, projetado para ser reutilizado em qualquer domínio de negócio. Ele elimina dependências de domínio específico (como "cachaça", "bebida") e fornece uma base sólida e extensível.

---

## Estrutura de Pacotes

```
com.subscription.framework/
├── core/                          # Núcleo do framework (domínio + contratos)
│   ├── domain/                    # Entidades JPA genéricas
│   │   ├── Product.java           # Produto abstrato (base para extensão)
│   │   ├── Plan.java              # Plano de assinatura
│   │   ├── Feature.java           # Funcionalidades/benefícios do plano
│   │   ├── Subscription.java      # Assinatura do cliente
│   │   ├── Package.java           # Pacote mensal/periódico
│   │   ├── PackageItem.java       # Item do pacote (produto + quantidade)
│   │   ├── DeliveryRecord.java    # Registro de entrega
│   │   └── enums/                 # Enumerações genéricas
│   │       ├── SubscriptionStatus.java
│   │       ├── DeliveryFrequency.java
│   │       ├── PlanTier.java
│   │       └── DeliveryStatus.java
│   │
│   ├── contract/                  # Interfaces/contratos (Strategy Pattern)
│   │   ├── ProductService.java    # Interface genérica para produtos
│   │   ├── PlanService.java       # Contrato para serviço de planos
│   │   ├── SubscriptionService.java
│   │   ├── PackageService.java
│   │   ├── BillingService.java    # Ponto de extensão: cobrança
│   │   ├── DeliveryService.java   # Ponto de extensão: entrega
│   │   └── NotificationService.java
│   │
│   ├── repository/                # Repositórios JPA genéricos
│   │   ├── ProductRepository.java # Genérico com <T extends Product>
│   │   ├── PlanRepository.java
│   │   ├── SubscriptionRepository.java
│   │   ├── PackageRepository.java
│   │   ├── PackageItemRepository.java
│   │   ├── DeliveryRecordRepository.java
│   │   └── FeatureRepository.java
│   │
│   └── service/                   # Serviços abstratos (Template Method)
│       ├── AbstractProductService.java
│       ├── AbstractPlanService.java
│       ├── AbstractSubscriptionService.java
│       └── AbstractPackageService.java
│
├── api/                           # Camada de API REST
│   ├── controller/                # Controllers abstratos
│   │   ├── AbstractPlanController.java
│   │   ├── AbstractSubscriptionController.java
│   │   └── AbstractPackageController.java
│   │
│   ├── dto/                       # Data Transfer Objects
│   │   ├── PlanRequestDTO.java
│   │   ├── PlanResponseDTO.java
│   │   ├── SubscriptionRequestDTO.java
│   │   ├── SubscriptionResponseDTO.java
│   │   ├── PackageRequestDTO.java
│   │   ├── PackageResponseDTO.java
│   │   ├── PackageItemRequestDTO.java
│   │   └── PackageItemResponseDTO.java
│   │
│   └── exception/                 # Exceções e handlers
│       ├── SubscriptionFrameworkException.java
│       ├── PlanNotFoundException.java
│       ├── SubscriptionNotFoundException.java
│       ├── PackageNotFoundException.java
│       ├── ProductNotFoundException.java
│       ├── FeatureNotFoundException.java
│       ├── DuplicatePlanNameException.java
│       ├── DuplicateSubscriptionException.java
│       ├── CustomerNotFoundException.java
│       └── GlobalExceptionHandler.java
│
├── infra/                         # Infraestrutura (implementações plugáveis)
│   ├── billing/
│   │   └── StubBillingService.java
│   ├── delivery/
│   │   └── StubDeliveryService.java
│   ├── notification/
│   │   └── StubNotificationService.java
│   └── config/                    # Configurações do framework
│
└── examples/                      # Exemplos de implementação
    ├── cachaca/                   # Exemplo: Assinatura de Cachaça
    │   ├── Cachaca.java           # Produto específico
    │   ├── CachacaPlan.java       # Plano específico
    │   ├── CachacaSubscription.java # Assinatura específica
    │   ├── CachacaPackage.java    # Pacote específico
    │   ├── CachacaType.java
    │   ├── AgingType.java
    │   ├── CachacaRepository.java
    │   ├── CachacaRequestDTO.java
    │   ├── CachacaResponseDTO.java
    │   ├── CachacaService.java
    │   └── CachacaController.java
    │
    ├── comics/                    # Exemplo: Assinatura de HQs
    │   ├── Comic.java             # Produto específico
    │   ├── ComicPlan.java         # Plano específico
    │   ├── ComicSubscription.java # Assinatura específica
    │   ├── ComicPackage.java      # Pacote específico
    │   ├── ComicGenre.java
    │   ├── ComicFormat.java
    │   ├── ComicRepository.java
    │   ├── ComicRequestDTO.java
    │   ├── ComicResponseDTO.java
    │   ├── ComicService.java
    │   └── ComicController.java
    │
    └── jersey/                    # Exemplo: Assinatura de Camisas
        ├── SoccerJersey.java      # Produto específico
        ├── JerseyPlan.java        # Plano específico
        ├── JerseySubscription.java # Assinatura específica
        ├── JerseyPackage.java     # Pacote específico
        ├── JerseyType.java
        ├── JerseySize.java
        ├── SoccerJerseyRepository.java
        ├── SoccerJerseyRequestDTO.java
        ├── SoccerJerseyResponseDTO.java
        ├── SoccerJerseyService.java
        └── SoccerJerseyController.java
```
    │   ├── Comic.java
    │   ├── ComicGenre.java
    │   ├── ComicFormat.java
    │   ├── ComicRepository.java
    │   ├── ComicRequestDTO.java
    │   ├── ComicResponseDTO.java
    │   ├── ComicService.java
    │   └── ComicController.java
    │
    └── jersey/                    # Exemplo: Assinatura de Camisas
        ├── SoccerJersey.java
        ├── JerseyType.java
        ├── JerseySize.java
        ├── SoccerJerseyRepository.java
        ├── SoccerJerseyRequestDTO.java
        ├── SoccerJerseyResponseDTO.java
        ├── SoccerJerseyService.java
        └── SoccerJerseyController.java
```

---

## Decisões Arquiteturais

### 1. **Herança com `@Inheritance(strategy = JOINED)` em Product, Plan, Subscription e Package**

**Por quê?**
- Permite que cada entidade específica tenha sua própria tabela com campos específicos do domínio
- Mantém a integridade referencial com chave estrangeira para a tabela base
- Queries podem ser feitas na entidade base ou na específica
- **Aplicado em 4 entidades**: Product, Plan, Subscription e Package

**Alternativas consideradas:**
- `SINGLE_TABLE`: Descartada por criar muitas colunas nulas
- `TABLE_PER_CLASS`: Descartada por dificultar queries polimórficas

**Exemplo de herança de Product:**
```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product { ... }

@Entity
public class Comic extends Product { ... }
```

**Exemplo de herança de Plan:**
```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Plan { ... }

@Entity
public class CachacaPlan extends Plan {
    private Boolean includesTastingSession;
    private Boolean includesSommelierAccess;
    private Boolean includesLimitedEditions;
    // ... campos específicos de cachaça
}
```

**Exemplo de herança de Subscription:**
```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Subscription { ... }

@Entity
public class ComicSubscription extends Subscription {
    private ComicGenre preferredGenre;
    private String favoritePublisher;
    private String favoriteCharacters;
    // ... preferências específicas do leitor
}
```

**Exemplo de herança de Package:**
```java
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Package { ... }

@Entity
public class JerseyPackage extends Package {
    private String featuredTeam;
    private String featuredLeague;
    private Boolean hasRetroJersey;
    // ... características específicas do pacote
}
```

### 2. **Generics em `ProductService<T extends Product>`**

**Por quê?**
- Type-safety: o serviço trabalha com o tipo correto
- Evita casts desnecessários
- Permite métodos específicos retornarem o tipo correto

```java
public interface ProductService<T extends Product> {
    T save(T product);
    Optional<T> findById(Long id);
    // ...
}
```

### 3. **Template Method Pattern nos Serviços Abstratos**

**Por quê?**
- Define o esqueleto do algoritmo na classe abstrata
- Permite que subclasses personalizem passos específicos via hooks
- Evita duplicação de código entre implementações

```java
public abstract class AbstractProductService<T extends Product> {
    
    public T save(T product) {
        validateProduct(product);    // Hook
        beforeSave(product);         // Hook
        T saved = repository.save(product);
        afterSave(saved);            // Hook
        return saved;
    }
    
    protected void validateProduct(T product) { }  // Override opcional
    protected void beforeSave(T product) { }       // Override opcional
    protected void afterSave(T saved) { }          // Override opcional
}
```

### 4. **Strategy Pattern nos Contratos**

**Por quê?**
- Desacopla a interface da implementação
- Permite trocar implementações (ex: StubBillingService → StripeBillingService)
- Facilita testes com mocks

```java
public interface BillingService {
    boolean processPayment(Long customerId, BigDecimal amount, String description);
    boolean refund(String transactionId, BigDecimal amount);
}

// Implementação stub (desenvolvimento)
@Service
@Profile("dev")
public class StubBillingService implements BillingService { ... }

// Implementação real (produção)
@Service
@Profile("prod")
public class StripeBillingService implements BillingService { ... }
```

### 5. **Metadata Map para Extensibilidade**

**Por quê?**
- Permite adicionar atributos dinâmicos sem alterar o schema
- Útil para integrações e dados temporários
- Flexibilidade para casos não previstos

```java
@ElementCollection
@CollectionTable(name = "product_metadata")
@MapKeyColumn(name = "meta_key")
@Column(name = "meta_value")
private Map<String, String> metadata = new HashMap<>();
```

### 6. **Records para DTOs**

**Por quê?**
- Imutabilidade por padrão
- Redução de boilerplate
- Semântica clara de "container de dados"

```java
public record PlanRequestDTO(
    String name,
    String description,
    BigDecimal price,
    DeliveryFrequency frequency,
    PlanTier tier
) {
    public Plan toEntity() { ... }
}
```

### 7. **Exceções Hierárquicas**

**Por quê?**
- Permite tratamento granular ou genérico
- Facilita logging e monitoramento
- Respostas HTTP consistentes via `@RestControllerAdvice`

```
SubscriptionFrameworkException (base)
├── PlanNotFoundException
├── SubscriptionNotFoundException
├── PackageNotFoundException
├── ProductNotFoundException
├── FeatureNotFoundException
├── CustomerNotFoundException
├── DuplicatePlanNameException
└── DuplicateSubscriptionException
```

### 8. **Separação Core vs API vs Infra**

**Por quê?**
- **Core**: Regras de negócio independentes de framework
- **API**: Exposição REST (pode ser trocada por GraphQL, gRPC)
- **Infra**: Implementações técnicas (banco, mensageria, APIs externas)

Isso segue os princípios de:
- Clean Architecture
- Ports and Adapters (Hexagonal)
- Inversão de Dependência

---

## Pontos de Extensão (Hotspots)

| Hotspot | Como Estender | Exemplo |
|---------|--------------|---------|
| Novo produto | Estender `Product`, criar Repository, Service, Controller | `Comic extends Product` |
| Validação customizada | Override de `validateProduct()` no Service | Validar teor alcoólico |
| Hooks pré/pós operação | Override de `beforeSave()`, `afterSave()`, etc. | Notificar sistema externo |
| Billing | Implementar `BillingService` | Integração Stripe |
| Entrega | Implementar `DeliveryService` | Integração Correios |
| Notificações | Implementar `NotificationService` | SMS, Push, Email |
| Queries customizadas | Adicionar métodos no Repository específico | `findByTeamAndSeason()` |

---

## Mapeamento de Classes (Original → Framework)

| Classe Original (Pingou) | Classe Framework | Observações |
|-------------------------|------------------|-------------|
| `Produto` | `Product` | Abstrata, genérica |
| `Cachaca` | `examples.cachaca.Cachaca` | Exemplo de extensão |
| `Plano` | `Plan` | Genérico |
| `Assinatura` | `Subscription` | Genérico |
| `Pacote` | `Package` | Genérico |
| `ItemPacote` | `PackageItem` | Genérico |
| `HistoricoEnvio` | `DeliveryRecord` | Genérico |
| `StatusAssinatura` | `SubscriptionStatus` | Enum expandido |
| `PlanoService` | `AbstractPlanService` | Template Method |
| `AssinaturaService` | `AbstractSubscriptionService` | Template Method |
| `PacoteService` | `AbstractPackageService` | Template Method |
| `CachacaService` | `AbstractProductService<T>` | Genérico tipado |

---

## Como Criar uma Nova Instância do Framework

### Passo 1: Criar a entidade do produto

```java
@Entity
public class MyProduct extends Product {
    // Campos específicos do seu domínio
    private String customField;
    private MyEnum type;
}
```

### Passo 2: Criar enums específicos (se necessário)

```java
public enum MyProductType {
    TYPE_A, TYPE_B, TYPE_C
}
```

### Passo 3: Criar o repositório

```java
@Repository
public interface MyProductRepository extends ProductRepository<MyProduct> {
    List<MyProduct> findByCustomField(String value);
}
```

### Passo 4: Criar DTOs

```java
public record MyProductRequestDTO(...) {
    public MyProduct toEntity() { ... }
}

public record MyProductResponseDTO(...) {
    public static MyProductResponseDTO fromEntity(MyProduct p) { ... }
}
```

### Passo 5: Criar o serviço

```java
@Service
public class MyProductService extends AbstractProductService<MyProduct> {
    
    public MyProductService(MyProductRepository repository) {
        super(repository);
    }
    
    @Override
    protected void validateProduct(MyProduct product) {
        // Validações específicas
    }
}
```

### Passo 6: Criar o controller

```java
@RestController
@RequestMapping("/api/v1/my-products")
public class MyProductController {
    // Endpoints REST
}
```

---

## Benefícios do Framework

1. **Reuso máximo**: 80%+ do código é compartilhado
2. **Consistência**: APIs seguem o mesmo padrão
3. **Manutenibilidade**: Correções no core beneficiam todos
4. **Testabilidade**: Interfaces permitem mocks fáceis
5. **Escalabilidade**: Adicionar novo produto = ~7 arquivos
6. **Desacoplamento**: Trocar Billing/Delivery sem afetar domínio
