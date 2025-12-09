# Subscription Framework - UML Diagrams

## Diagrama de Classes - Entidades do Core

```mermaid
classDiagram
    direction TB
    
    %% ===== ENUMS =====
    class SubscriptionStatus {
        <<enumeration>>
        ACTIVE
        INACTIVE
        CANCELLED
        EXPIRED
        SUSPENDED
        PENDING
    }
    
    class DeliveryFrequency {
        <<enumeration>>
        WEEKLY
        BIWEEKLY
        MONTHLY
        BIMONTHLY
        QUARTERLY
        SEMIANNUALLY
        ANNUALLY
    }
    
    class PlanTier {
        <<enumeration>>
        BASIC
        STANDARD
        PREMIUM
        ENTERPRISE
        CUSTOM
    }
    
    class DeliveryStatus {
        <<enumeration>>
        PENDING
        PREPARING
        SHIPPED
        IN_TRANSIT
        DELIVERED
        RETURNED
        CANCELLED
    }
    
    %% ===== CORE ENTITIES =====
    class Product {
        <<abstract>>
        -Long id
        -String name
        -String description
        -BigDecimal price
        -String imageUrl
        -boolean active
        -Map~String,String~ metadata
        -LocalDateTime createdAt
        -LocalDateTime updatedAt
    }
    
    class Plan {
        -Long id
        -String name
        -String description
        -BigDecimal price
        -int maxItemsPerDelivery
        -PlanTier tier
        -DeliveryFrequency frequency
        -boolean active
        -List~Feature~ features
        -List~Package~ packages
    }
    
    class Feature {
        -Long id
        -String name
        -String description
        -boolean enabled
    }
    
    class Subscription {
        -Long id
        -Long customerId
        -Plan plan
        -SubscriptionStatus status
        -LocalDate startDate
        -LocalDate expirationDate
        -boolean autoRenew
        -Map~String,String~ metadata
    }
    
    class Package {
        -Long id
        -String name
        -String description
        -Plan plan
        -LocalDate deliveryDate
        -int month
        -int year
        -List~PackageItem~ items
        -BigDecimal totalValue
        -boolean active
    }
    
    class PackageItem {
        -Long id
        -Package package
        -Product product
        -int quantity
        -BigDecimal unitPrice
        -String notes
    }
    
    class DeliveryRecord {
        -Long id
        -Long customerId
        -Package package
        -Product product
        -int quantity
        -DeliveryStatus status
        -LocalDate scheduledDate
        -LocalDate deliveredDate
        -String trackingCode
        -String notes
    }
    
    %% ===== RELATIONSHIPS =====
    Plan "1" --> "*" Feature : features
    Plan "1" --> "*" Package : packages
    Plan "1" --> "1" PlanTier : tier
    Plan "1" --> "1" DeliveryFrequency : frequency
    
    Subscription "*" --> "1" Plan : plan
    Subscription "1" --> "1" SubscriptionStatus : status
    
    Package "*" --> "1" Plan : plan
    Package "1" --> "*" PackageItem : items
    
    PackageItem "*" --> "1" Product : product
    PackageItem "*" --> "1" Package : package
    
    DeliveryRecord "*" --> "1" Package : package
    DeliveryRecord "*" --> "1" Product : product
    DeliveryRecord "1" --> "1" DeliveryStatus : status
```

---

## Diagrama de Classes - Herança de Product

```mermaid
classDiagram
    direction TB
    
    class Product {
        <<abstract>>
        -Long id
        -String name
        -String description
        -BigDecimal price
        -String imageUrl
        -boolean active
        -Map~String,String~ metadata
    }
    
    class Cachaca {
        -String region
        -Double alcoholContent
        -Integer volume
        -CachacaType type
        -AgingType agingType
        -Integer agingMonths
    }
    
    class Comic {
        -String publisher
        -String series
        -Integer issueNumber
        -ComicGenre genre
        -ComicFormat format
        -String writer
        -String artist
        -LocalDate releaseDate
        -Integer pageCount
        -boolean isLimitedEdition
    }
    
    class SoccerJersey {
        -String team
        -String season
        -JerseyType jerseyType
        -JerseySize size
        -String playerName
        -Integer playerNumber
        -boolean isAuthentic
        -String manufacturer
    }
    
    Product <|-- Cachaca
    Product <|-- Comic
    Product <|-- SoccerJersey
```

---

## Diagrama de Classes - Herança de Plan

```mermaid
classDiagram
    direction TB
    
    class Plan {
        -Long id
        -String name
        -String description
        -BigDecimal price
        -int maxItemsPerDelivery
        -PlanTier tier
        -DeliveryFrequency frequency
        -boolean active
    }
    
    class CachacaPlan {
        -Boolean includesTastingSession
        -Boolean includesSommelierAccess
        -Boolean includesLimitedEditions
        -Boolean includesAgedOnly
        -Boolean includesTastingNotes
        -Integer partnerStoreDiscountPercent
        -String preferredRegion
        -CachacaType preferredCachacaType
        -Boolean includesAccessories
        +isPremium() boolean
        +getBenefitsSummary() String
    }
    
    class ComicPlan {
        -Boolean includesVariantCovers
        -Boolean includesExclusives
        -Boolean includesSignedEditions
        -Boolean multiPublisher
        -String preferredPublisher
        -Boolean newReleasesOnly
        -Boolean includesGraphicNovels
        -Boolean includesManga
        -ComicGenre preferredGenre
        -Boolean includesCollectibles
        -Boolean includesArtPrints
        -Boolean includesDigitalAccess
        +isCollectorPlan() boolean
        +getBenefitsSummary() String
    }
    
    class JerseyPlan {
        -Boolean authenticOnly
        -Boolean includesRetro
        -Boolean includesSpecialEditions
        -Boolean allowsCustomization
        -Boolean includesNationalTeams
        -Boolean includesClubs
        -String preferredLeague
        -String preferredManufacturer
        -Boolean includesAccessories
        -Boolean includesCertificate
        +isCollectorPlan() boolean
        +getBenefitsSummary() String
    }
    
    Plan <|-- CachacaPlan
    Plan <|-- ComicPlan
    Plan <|-- JerseyPlan
```

---

## Diagrama de Classes - Herança de Subscription

```mermaid
classDiagram
    direction TB
    
    class Subscription {
        -Long id
        -Long customerId
        -String customerEmail
        -Plan plan
        -SubscriptionStatus status
        -LocalDate startDate
        -LocalDate expirationDate
        +activate() void
        +pause() void
        +cancel() void
        +renew() void
    }
    
    class CachacaSubscription {
        -CachacaType preferredType
        -AgingType preferredAging
        -String preferredRegion
        -Double minAlcoholContent
        -Double maxAlcoholContent
        -Integer preferredVolume
        -Boolean acceptsRepeats
        -Boolean surpriseMode
        -String tastingPreferences
        -ExpertiseLevel expertiseLevel
        +hasPreferences() boolean
    }
    
    class ComicSubscription {
        -ComicGenre preferredGenre
        -ComicFormat preferredFormat
        -String favoritePublisher
        -String favoriteCharacters
        -String favoriteWriters
        -String favoriteArtists
        -Boolean acceptsDuplicates
        -Boolean surpriseMode
        -Boolean interestedInManga
        -ReaderLevel readerLevel
        -AgeRating agePreference
        +hasPreferences() boolean
    }
    
    class JerseySubscription {
        -String favoriteTeam
        -String favoriteNationalTeam
        -String preferredLeague
        -JerseySize preferredSize
        -JerseyType preferredJerseyType
        -Boolean prefersAuthentic
        -String favoritePlayer
        -Integer preferredNumber
        -Boolean acceptsRivalTeams
        -String teamsToAvoid
        -CollectorType collectorType
        -UsagePurpose usagePurpose
        +hasPreferences() boolean
    }
    
    Subscription <|-- CachacaSubscription
    Subscription <|-- ComicSubscription
    Subscription <|-- JerseySubscription
```

---

## Diagrama de Classes - Herança de Package

```mermaid
classDiagram
    direction TB
    
    class Package {
        -Long id
        -String name
        -String description
        -LocalDate deliveryDate
        -int month
        -int year
        -Plan plan
        -List~PackageItem~ items
        -BigDecimal totalValue
        -String theme
        +addItem(PackageItem) void
        +removeItem(PackageItem) void
        +recalculateTotalValue() void
    }
    
    class CachacaPackage {
        -String harmonizationTheme
        -String harmonizationSuggestion
        -String featuredRegion
        -CachacaType featuredCachacaType
        -Boolean hasLimitedEdition
        -Boolean hasAwardedCachaca
        -Integer totalVolumeMl
        -Double averageAlcoholContent
        -String tastingVideoUrl
        -String drinkRecipe
        -String drinkName
        -LocalDateTime tastingSessionDate
        +calculateTotalVolume() void
        +calculateAverageAlcoholContent() void
        +hasEducationalContent() boolean
    }
    
    class ComicPackage {
        -String monthlyTheme
        -String themeDescription
        -String featuredCharacter
        -String featuredPublisher
        -String featuredSeries
        -Boolean hasVariantCover
        -Boolean hasSignedEdition
        -Boolean hasCollectible
        -Boolean hasArtPrint
        -Integer totalPages
        -Integer issueCount
        -String trivia
        -String digitalContentUrl
        +calculateTotalPages() void
        +calculateIssueCount() void
        +isPremiumPackage() boolean
    }
    
    class JerseyPackage {
        -String featuredSeason
        -String featuredLeague
        -String featuredTeam
        -String featuredPlayer
        -String relatedEvent
        -Boolean hasRetroJersey
        -Integer retroDecade
        -Boolean hasSpecialEdition
        -Boolean hasAuthentic
        -Boolean hasAccessory
        -Boolean hasCollectible
        -Integer jerseyCount
        -Boolean isDerbyPackage
        -Boolean isWorldCupThemed
        +calculateJerseyCount() void
        +isPremiumPackage() boolean
        +getPackageHighlights() String
    }
    
    Package <|-- CachacaPackage
    Package <|-- ComicPackage
    Package <|-- JerseyPackage
```

---

## Diagrama de Classes - Camada de Serviços

```mermaid
classDiagram
    direction TB
    
    %% ===== CONTRACTS (Interfaces) =====
    class ProductService~T~ {
        <<interface>>
        +save(T product) T
        +findById(Long id) Optional~T~
        +findAll() List~T~
        +findActive() List~T~
        +deleteById(Long id) void
        +findByCategory(String category) List~T~
        +findInPriceRange(BigDecimal min, BigDecimal max) List~T~
    }
    
    class PlanService {
        <<interface>>
        +save(Plan plan) Plan
        +findById(Long id) Optional~Plan~
        +findAll() List~Plan~
        +findActive() List~Plan~
        +findByTier(PlanTier tier) List~Plan~
        +addFeature(Long planId, Feature feature) Plan
        +removeFeature(Long planId, Long featureId) Plan
    }
    
    class SubscriptionService {
        <<interface>>
        +create(Long customerId, Long planId) Subscription
        +cancel(Long subscriptionId) Subscription
        +pause(Long subscriptionId) Subscription
        +resume(Long subscriptionId) Subscription
        +upgrade(Long subscriptionId, Long newPlanId) Subscription
        +downgrade(Long subscriptionId, Long newPlanId) Subscription
        +renew(Long subscriptionId) Subscription
        +findByCustomer(Long customerId) List~Subscription~
        +findByStatus(SubscriptionStatus status) List~Subscription~
    }
    
    class BillingService {
        <<interface>>
        +processPayment(Long customerId, BigDecimal amount, String desc) boolean
        +refund(String transactionId, BigDecimal amount) boolean
        +getPaymentHistory(Long customerId) List~Map~
        +generateInvoice(Long subscriptionId) String
    }
    
    class DeliveryService {
        <<interface>>
        +scheduleDelivery(Long customerId, Long packageId, LocalDate date) DeliveryRecord
        +cancelDelivery(Long deliveryId) boolean
        +trackDelivery(Long deliveryId) DeliveryRecord
        +updateDeliveryStatus(Long deliveryId, DeliveryStatus status) DeliveryRecord
    }
    
    class NotificationService {
        <<interface>>
        +sendSubscriptionCreated(Long customerId, Subscription sub) void
        +sendDeliveryScheduled(Long customerId, DeliveryRecord delivery) void
        +sendPaymentProcessed(Long customerId, BigDecimal amount) void
        +sendSubscriptionExpiring(Long customerId, Subscription sub) void
    }
    
    %% ===== ABSTRACT SERVICES =====
    class AbstractProductService~T~ {
        <<abstract>>
        #ProductRepository~T~ repository
        +save(T product) T
        +findById(Long id) Optional~T~
        +findAll() List~T~
        #validateProduct(T product) void
        #beforeSave(T product) void
        #afterSave(T product) void
        #beforeDelete(T product) void
    }
    
    class AbstractPlanService {
        <<abstract>>
        +save(Plan plan) Plan
        +findByTier(PlanTier tier) List~Plan~
        #validatePlan(Plan plan) void
        #beforeSave(Plan plan) void
        #afterSave(Plan plan) void
    }
    
    class AbstractSubscriptionService {
        <<abstract>>
        +create(Long customerId, Long planId) Subscription
        +cancel(Long subscriptionId) Subscription
        #validateSubscription(Subscription sub) void
        #onSubscriptionCreated(Subscription sub) void
        #onSubscriptionCancelled(Subscription sub) void
    }
    
    %% ===== CONCRETE SERVICES =====
    class CachacaService {
        +findByRegion(String region) List~Cachaca~
        +findByType(CachacaType type) List~Cachaca~
        +findAged() List~Cachaca~
    }
    
    class ComicService {
        +findByPublisher(String publisher) List~Comic~
        +findByGenre(ComicGenre genre) List~Comic~
        +findLimitedEditions() List~Comic~
    }
    
    class SoccerJerseyService {
        +findByTeam(String team) List~SoccerJersey~
        +findBySeason(String season) List~SoccerJersey~
        +findAuthenticOnly() List~SoccerJersey~
    }
    
    %% ===== INFRASTRUCTURE STUBS =====
    class StubBillingService {
        +processPayment() boolean
        +refund() boolean
    }
    
    class StubDeliveryService {
        +scheduleDelivery() DeliveryRecord
        +trackDelivery() DeliveryRecord
    }
    
    class StubNotificationService {
        +sendSubscriptionCreated() void
        +sendDeliveryScheduled() void
    }
    
    %% ===== RELATIONSHIPS =====
    ProductService~T~ <|.. AbstractProductService~T~
    PlanService <|.. AbstractPlanService
    SubscriptionService <|.. AbstractSubscriptionService
    
    AbstractProductService~T~ <|-- CachacaService
    AbstractProductService~T~ <|-- ComicService
    AbstractProductService~T~ <|-- SoccerJerseyService
    
    BillingService <|.. StubBillingService
    DeliveryService <|.. StubDeliveryService
    NotificationService <|.. StubNotificationService
```

---

## Diagrama de Pacotes

```mermaid
flowchart TB
    subgraph framework["com.subscription.framework"]
        subgraph core["core"]
            domain["domain<br/>(Entities, Enums)"]
            contract["contract<br/>(Interfaces)"]
            repository["repository<br/>(JPA Repositories)"]
            service["service<br/>(Abstract Services)"]
        end
        
        subgraph api["api"]
            controller["controller<br/>(Abstract Controllers)"]
            dto["dto<br/>(Request/Response DTOs)"]
            exception["exception<br/>(Custom Exceptions)"]
        end
        
        subgraph infra["infra"]
            billing["billing<br/>(Stub/Real)"]
            delivery["delivery<br/>(Stub/Real)"]
            notification["notification<br/>(Stub/Real)"]
            config["config"]
        end
        
        subgraph examples["examples"]
            cachaca["cachaca"]
            comics["comics"]
            jersey["jersey"]
        end
    end
    
    %% Dependencies
    service --> domain
    service --> contract
    service --> repository
    
    controller --> service
    controller --> dto
    
    dto --> domain
    
    infra --> contract
    
    examples --> core
    examples --> api
```

---

## Diagrama de Sequência - Criar Assinatura

```mermaid
sequenceDiagram
    participant C as Client
    participant SC as SubscriptionController
    participant SS as SubscriptionService
    participant PS as PlanService
    participant SR as SubscriptionRepository
    participant BS as BillingService
    participant NS as NotificationService
    
    C->>SC: POST /api/subscriptions
    SC->>SS: create(customerId, planId)
    
    SS->>PS: findById(planId)
    PS-->>SS: Plan
    
    SS->>SS: validateSubscription()
    SS->>SS: buildSubscription()
    
    SS->>BS: processPayment(customerId, plan.price)
    BS-->>SS: success: true
    
    SS->>SR: save(subscription)
    SR-->>SS: Subscription (persisted)
    
    SS->>SS: onSubscriptionCreated()
    SS->>NS: sendSubscriptionCreated(customerId, subscription)
    
    SS-->>SC: Subscription
    SC-->>C: 201 Created + SubscriptionResponseDTO
```

---

## Diagrama de Sequência - Adicionar Item ao Pacote

```mermaid
sequenceDiagram
    participant C as Client
    participant PC as PackageController
    participant PS as PackageService
    participant PRS as ProductService
    participant PKR as PackageRepository
    
    C->>PC: POST /api/packages/{id}/items
    PC->>PS: addItem(packageId, productId, quantity)
    
    PS->>PKR: findById(packageId)
    PKR-->>PS: Package
    
    PS->>PRS: findById(productId)
    PRS-->>PS: Product
    
    PS->>PS: validateItem(package, product, quantity)
    PS->>PS: createPackageItem()
    
    PS->>PS: calculateTotalValue()
    
    PS->>PKR: save(package)
    PKR-->>PS: Package (updated)
    
    PS-->>PC: Package
    PC-->>C: 200 OK + PackageResponseDTO
```

---

## Diagrama de Estados - Subscription

```mermaid
stateDiagram-v2
    [*] --> PENDING: create()
    
    PENDING --> ACTIVE: payment success
    PENDING --> CANCELLED: payment failed / cancel()
    
    ACTIVE --> SUSPENDED: pause()
    ACTIVE --> CANCELLED: cancel()
    ACTIVE --> EXPIRED: expiration date reached
    
    SUSPENDED --> ACTIVE: resume()
    SUSPENDED --> CANCELLED: cancel()
    
    EXPIRED --> ACTIVE: renew()
    EXPIRED --> INACTIVE: no renewal
    
    CANCELLED --> [*]
    INACTIVE --> [*]
```

---

## Diagrama de Estados - Delivery

```mermaid
stateDiagram-v2
    [*] --> PENDING: scheduleDelivery()
    
    PENDING --> PREPARING: start preparation
    PENDING --> CANCELLED: cancelDelivery()
    
    PREPARING --> SHIPPED: ship
    PREPARING --> CANCELLED: cancel
    
    SHIPPED --> IN_TRANSIT: carrier picked up
    
    IN_TRANSIT --> DELIVERED: delivery confirmed
    IN_TRANSIT --> RETURNED: delivery failed
    
    DELIVERED --> [*]
    RETURNED --> [*]
    CANCELLED --> [*]
```

---

## Modelo ER (Entity-Relationship)

```mermaid
erDiagram
    PRODUCT {
        bigint id PK
        varchar name
        text description
        decimal price
        varchar image_url
        boolean active
        timestamp created_at
        timestamp updated_at
        varchar dtype
    }
    
    PRODUCT_METADATA {
        bigint product_id FK
        varchar meta_key
        varchar meta_value
    }
    
    CACHACA {
        bigint id PK,FK
        varchar region
        double alcohol_content
        int volume
        varchar cachaca_type
        varchar aging_type
        int aging_months
    }
    
    COMIC {
        bigint id PK,FK
        varchar publisher
        varchar series
        int issue_number
        varchar genre
        varchar format
        varchar writer
        varchar artist
        date release_date
        int page_count
        boolean is_limited_edition
    }
    
    SOCCER_JERSEY {
        bigint id PK,FK
        varchar team
        varchar season
        varchar jersey_type
        varchar size
        varchar player_name
        int player_number
        boolean is_authentic
        varchar manufacturer
    }
    
    PLAN {
        bigint id PK
        varchar name
        text description
        decimal price
        int max_items_per_delivery
        varchar tier
        varchar frequency
        boolean active
    }
    
    FEATURE {
        bigint id PK
        varchar name
        text description
        boolean enabled
        bigint plan_id FK
    }
    
    SUBSCRIPTION {
        bigint id PK
        bigint customer_id
        bigint plan_id FK
        varchar status
        date start_date
        date expiration_date
        boolean auto_renew
    }
    
    PACKAGE {
        bigint id PK
        varchar name
        text description
        bigint plan_id FK
        date delivery_date
        int month
        int year
        decimal total_value
        boolean active
    }
    
    PACKAGE_ITEM {
        bigint id PK
        bigint package_id FK
        bigint product_id FK
        int quantity
        decimal unit_price
        varchar notes
    }
    
    DELIVERY_RECORD {
        bigint id PK
        bigint customer_id
        bigint package_id FK
        bigint product_id FK
        int quantity
        varchar status
        date scheduled_date
        date delivered_date
        varchar tracking_code
        text notes
    }
    
    PRODUCT ||--o{ PRODUCT_METADATA : has
    PRODUCT ||--o| CACHACA : is_a
    PRODUCT ||--o| COMIC : is_a
    PRODUCT ||--o| SOCCER_JERSEY : is_a
    
    PLAN ||--o{ FEATURE : has
    PLAN ||--o{ SUBSCRIPTION : has
    PLAN ||--o{ PACKAGE : has
    
    PACKAGE ||--o{ PACKAGE_ITEM : contains
    PRODUCT ||--o{ PACKAGE_ITEM : included_in
    
    PACKAGE ||--o{ DELIVERY_RECORD : tracked_by
    PRODUCT ||--o{ DELIVERY_RECORD : delivered
```
