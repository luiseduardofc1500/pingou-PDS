# Guia de Extensão do Subscription Framework

Este guia mostra como criar uma nova implementação do framework para um domínio de negócio diferente.

---

## Passo a Passo Completo

### Cenário: Criar assinatura de **Vinhos**

Vamos criar todos os arquivos necessários para um novo domínio.

---

### 1. Criar a Entidade do Produto

Crie a classe que estende `Product`:

```java
package com.subscription.framework.examples.wine;

import com.subscription.framework.core.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Wine extends Product {
    
    @Column(nullable = false)
    private String winery;           // Vinícola
    
    @Column(nullable = false)
    private String region;           // Região (ex: Vale dos Vinhedos)
    
    @Column(nullable = false)
    private String country;          // País de origem
    
    @Column(nullable = false)
    private Integer vintage;         // Ano da safra
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WineType type;           // Tipo (tinto, branco, rosé, etc)
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GrapeVariety grape;      // Uva principal
    
    @Column(nullable = false)
    private Double alcoholContent;   // Teor alcoólico
    
    @Column(nullable = false)
    private Integer volume;          // Volume em ml (750, 375, 1500)
    
    private String harmonization;    // Sugestão de harmonização
    
    private Integer servingTemperature; // Temperatura ideal de serviço (°C)
    
    private Double rating;           // Nota (0-100 pontos)
    
    private boolean isOrganic;       // Se é orgânico/biodinâmico
}
```

---

### 2. Criar Enums Específicos

```java
package com.subscription.framework.examples.wine;

public enum WineType {
    RED("Tinto", "Red wine"),
    WHITE("Branco", "White wine"),
    ROSE("Rosé", "Rosé wine"),
    SPARKLING("Espumante", "Sparkling wine"),
    DESSERT("Sobremesa", "Dessert wine"),
    FORTIFIED("Fortificado", "Fortified wine like Port or Sherry");
    
    private final String displayName;
    private final String description;
    
    WineType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
}
```

```java
package com.subscription.framework.examples.wine;

public enum GrapeVariety {
    // Tintas
    CABERNET_SAUVIGNON("Cabernet Sauvignon"),
    MERLOT("Merlot"),
    PINOT_NOIR("Pinot Noir"),
    MALBEC("Malbec"),
    SYRAH("Syrah / Shiraz"),
    TANNAT("Tannat"),
    TEMPRANILLO("Tempranillo"),
    SANGIOVESE("Sangiovese"),
    
    // Brancas
    CHARDONNAY("Chardonnay"),
    SAUVIGNON_BLANC("Sauvignon Blanc"),
    RIESLING("Riesling"),
    MOSCATO("Moscato"),
    PINOT_GRIGIO("Pinot Grigio"),
    GEWURZTRAMINER("Gewürztraminer"),
    
    // Brasileiras
    BORDÔ("Bordô"),
    ISABEL("Isabel"),
    
    // Blends
    BLEND("Blend / Assemblage"),
    OTHER("Outra");
    
    private final String displayName;
    
    GrapeVariety(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() { return displayName; }
}
```

---

### 3. Criar o Repository

```java
package com.subscription.framework.examples.wine;

import com.subscription.framework.core.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WineRepository extends ProductRepository<Wine> {
    
    // Busca por características do vinho
    List<Wine> findByWineryContainingIgnoreCase(String winery);
    List<Wine> findByRegionContainingIgnoreCase(String region);
    List<Wine> findByCountryContainingIgnoreCase(String country);
    List<Wine> findByVintage(Integer vintage);
    List<Wine> findByType(WineType type);
    List<Wine> findByGrape(GrapeVariety grape);
    
    // Busca por faixa de teor alcoólico
    List<Wine> findByAlcoholContentBetween(Double min, Double max);
    
    // Busca por rating
    List<Wine> findByRatingGreaterThanEqual(Double minRating);
    
    // Orgânicos
    List<Wine> findByIsOrganic(boolean isOrganic);
    
    // Busca por safra (vinhos de guarda)
    List<Wine> findByVintageLessThanEqual(Integer year);
    
    // Combinações
    List<Wine> findByTypeAndGrape(WineType type, GrapeVariety grape);
    List<Wine> findByCountryAndRegion(String country, String region);
    List<Wine> findByTypeAndRatingGreaterThanEqual(WineType type, Double minRating);
}
```

---

### 4. Criar os DTOs

```java
package com.subscription.framework.examples.wine;

import java.math.BigDecimal;

public record WineRequestDTO(
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
        String winery,
        String region,
        String country,
        Integer vintage,
        WineType type,
        GrapeVariety grape,
        Double alcoholContent,
        Integer volume,
        String harmonization,
        Integer servingTemperature,
        Double rating,
        boolean isOrganic
) {
    public Wine toEntity() {
        Wine wine = new Wine();
        wine.setName(name);
        wine.setDescription(description);
        wine.setPrice(price);
        wine.setImageUrl(imageUrl);
        wine.setActive(true);
        wine.setWinery(winery);
        wine.setRegion(region);
        wine.setCountry(country);
        wine.setVintage(vintage);
        wine.setType(type);
        wine.setGrape(grape);
        wine.setAlcoholContent(alcoholContent);
        wine.setVolume(volume);
        wine.setHarmonization(harmonization);
        wine.setServingTemperature(servingTemperature);
        wine.setRating(rating);
        wine.setOrganic(isOrganic);
        return wine;
    }
}
```

```java
package com.subscription.framework.examples.wine;

import java.math.BigDecimal;

public record WineResponseDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
        boolean active,
        String winery,
        String region,
        String country,
        Integer vintage,
        WineType type,
        GrapeVariety grape,
        Double alcoholContent,
        Integer volume,
        String harmonization,
        Integer servingTemperature,
        Double rating,
        boolean isOrganic
) {
    public static WineResponseDTO fromEntity(Wine wine) {
        return new WineResponseDTO(
                wine.getId(),
                wine.getName(),
                wine.getDescription(),
                wine.getPrice(),
                wine.getImageUrl(),
                wine.isActive(),
                wine.getWinery(),
                wine.getRegion(),
                wine.getCountry(),
                wine.getVintage(),
                wine.getType(),
                wine.getGrape(),
                wine.getAlcoholContent(),
                wine.getVolume(),
                wine.getHarmonization(),
                wine.getServingTemperature(),
                wine.getRating(),
                wine.isOrganic()
        );
    }
}
```

---

### 5. Criar o Service

```java
package com.subscription.framework.examples.wine;

import com.subscription.framework.core.service.AbstractProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

@Service
public class WineService extends AbstractProductService<Wine> {
    
    private final WineRepository wineRepository;
    
    public WineService(WineRepository wineRepository) {
        super(wineRepository);
        this.wineRepository = wineRepository;
    }
    
    // ===== Métodos específicos do domínio =====
    
    public List<Wine> findByWinery(String winery) {
        return wineRepository.findByWineryContainingIgnoreCase(winery);
    }
    
    public List<Wine> findByRegion(String region) {
        return wineRepository.findByRegionContainingIgnoreCase(region);
    }
    
    public List<Wine> findByCountry(String country) {
        return wineRepository.findByCountryContainingIgnoreCase(country);
    }
    
    public List<Wine> findByVintage(Integer vintage) {
        return wineRepository.findByVintage(vintage);
    }
    
    public List<Wine> findByType(WineType type) {
        return wineRepository.findByType(type);
    }
    
    public List<Wine> findByGrape(GrapeVariety grape) {
        return wineRepository.findByGrape(grape);
    }
    
    public List<Wine> findOrganic() {
        return wineRepository.findByIsOrganic(true);
    }
    
    public List<Wine> findTopRated(Double minRating) {
        return wineRepository.findByRatingGreaterThanEqual(minRating);
    }
    
    public List<Wine> findByTypeAndGrape(WineType type, GrapeVariety grape) {
        return wineRepository.findByTypeAndGrape(type, grape);
    }
    
    /**
     * Encontra vinhos de guarda (com pelo menos X anos).
     */
    public List<Wine> findAgedWines(int minimumYears) {
        int maxVintage = Year.now().getValue() - minimumYears;
        return wineRepository.findByVintageLessThanEqual(maxVintage);
    }
    
    // ===== Hooks do AbstractProductService =====
    
    @Override
    protected void validateProduct(Wine wine) {
        if (wine.getWinery() == null || wine.getWinery().isBlank()) {
            throw new IllegalArgumentException("Winery is required");
        }
        if (wine.getRegion() == null || wine.getRegion().isBlank()) {
            throw new IllegalArgumentException("Region is required");
        }
        if (wine.getCountry() == null || wine.getCountry().isBlank()) {
            throw new IllegalArgumentException("Country is required");
        }
        if (wine.getVintage() == null || wine.getVintage() < 1900 || 
            wine.getVintage() > Year.now().getValue()) {
            throw new IllegalArgumentException("Invalid vintage year");
        }
        if (wine.getType() == null) {
            throw new IllegalArgumentException("Wine type is required");
        }
        if (wine.getGrape() == null) {
            throw new IllegalArgumentException("Grape variety is required");
        }
        if (wine.getAlcoholContent() == null || wine.getAlcoholContent() < 0 || 
            wine.getAlcoholContent() > 25) {
            throw new IllegalArgumentException("Invalid alcohol content (0-25%)");
        }
        if (wine.getVolume() == null || wine.getVolume() <= 0) {
            throw new IllegalArgumentException("Volume must be positive");
        }
        if (wine.getPrice() == null || wine.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        if (wine.getRating() != null && (wine.getRating() < 0 || wine.getRating() > 100)) {
            throw new IllegalArgumentException("Rating must be between 0 and 100");
        }
    }
    
    @Override
    protected void beforeSave(Wine wine) {
        // Gera nome se não fornecido
        if (wine.getName() == null || wine.getName().isBlank()) {
            wine.setName(String.format("%s %s %d", 
                    wine.getWinery(), 
                    wine.getGrape().getDisplayName(), 
                    wine.getVintage()));
        }
        
        // Popula metadata
        wine.getMetadata().put("category", "wine");
        wine.getMetadata().put("type", wine.getType().name());
        wine.getMetadata().put("grape", wine.getGrape().name());
        wine.getMetadata().put("country", wine.getCountry());
        
        if (wine.isOrganic()) {
            wine.getMetadata().put("certification", "organic");
        }
    }
    
    @Override
    protected void afterSave(Wine wine) {
        // Ex: notificar sistema de estoque, atualizar catálogo
    }
    
    @Override
    protected void beforeDelete(Wine wine) {
        // Ex: verificar se não está em pacotes ativos
    }
}
```

---

### 6. Criar o Controller

```java
package com.subscription.framework.examples.wine;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wines")
public class WineController {
    
    private final WineService wineService;
    
    public WineController(WineService wineService) {
        this.wineService = wineService;
    }
    
    // ===== CRUD básico =====
    
    @GetMapping
    public ResponseEntity<List<WineResponseDTO>> findAll() {
        return ResponseEntity.ok(wineService.findAll().stream()
                .map(WineResponseDTO::fromEntity)
                .toList());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<WineResponseDTO> findById(@PathVariable Long id) {
        return wineService.findById(id)
                .map(WineResponseDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<WineResponseDTO> create(@RequestBody WineRequestDTO request) {
        Wine saved = wineService.save(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(WineResponseDTO.fromEntity(saved));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<WineResponseDTO> update(@PathVariable Long id, 
                                                   @RequestBody WineRequestDTO request) {
        return wineService.findById(id)
                .map(existing -> {
                    Wine updated = request.toEntity();
                    updated.setId(id);
                    return ResponseEntity.ok(WineResponseDTO.fromEntity(wineService.save(updated)));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (wineService.findById(id).isPresent()) {
            wineService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // ===== Endpoints específicos =====
    
    @GetMapping("/active")
    public ResponseEntity<List<WineResponseDTO>> findActive() {
        return ResponseEntity.ok(wineService.findActive().stream()
                .map(WineResponseDTO::fromEntity)
                .toList());
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<WineResponseDTO>> findByType(@PathVariable WineType type) {
        return ResponseEntity.ok(wineService.findByType(type).stream()
                .map(WineResponseDTO::fromEntity)
                .toList());
    }
    
    @GetMapping("/grape/{grape}")
    public ResponseEntity<List<WineResponseDTO>> findByGrape(@PathVariable GrapeVariety grape) {
        return ResponseEntity.ok(wineService.findByGrape(grape).stream()
                .map(WineResponseDTO::fromEntity)
                .toList());
    }
    
    @GetMapping("/winery/{winery}")
    public ResponseEntity<List<WineResponseDTO>> findByWinery(@PathVariable String winery) {
        return ResponseEntity.ok(wineService.findByWinery(winery).stream()
                .map(WineResponseDTO::fromEntity)
                .toList());
    }
    
    @GetMapping("/region/{region}")
    public ResponseEntity<List<WineResponseDTO>> findByRegion(@PathVariable String region) {
        return ResponseEntity.ok(wineService.findByRegion(region).stream()
                .map(WineResponseDTO::fromEntity)
                .toList());
    }
    
    @GetMapping("/country/{country}")
    public ResponseEntity<List<WineResponseDTO>> findByCountry(@PathVariable String country) {
        return ResponseEntity.ok(wineService.findByCountry(country).stream()
                .map(WineResponseDTO::fromEntity)
                .toList());
    }
    
    @GetMapping("/vintage/{year}")
    public ResponseEntity<List<WineResponseDTO>> findByVintage(@PathVariable Integer year) {
        return ResponseEntity.ok(wineService.findByVintage(year).stream()
                .map(WineResponseDTO::fromEntity)
                .toList());
    }
    
    @GetMapping("/organic")
    public ResponseEntity<List<WineResponseDTO>> findOrganic() {
        return ResponseEntity.ok(wineService.findOrganic().stream()
                .map(WineResponseDTO::fromEntity)
                .toList());
    }
    
    @GetMapping("/top-rated")
    public ResponseEntity<List<WineResponseDTO>> findTopRated(
            @RequestParam(defaultValue = "90") Double minRating) {
        return ResponseEntity.ok(wineService.findTopRated(minRating).stream()
                .map(WineResponseDTO::fromEntity)
                .toList());
    }
    
    @GetMapping("/aged")
    public ResponseEntity<List<WineResponseDTO>> findAged(
            @RequestParam(defaultValue = "5") int minimumYears) {
        return ResponseEntity.ok(wineService.findAgedWines(minimumYears).stream()
                .map(WineResponseDTO::fromEntity)
                .toList());
    }
}
```

---

## Checklist de Implementação

Para cada novo domínio, crie os seguintes arquivos:

| # | Arquivo | Descrição |
|---|---------|-----------|
| 1 | `MyProduct.java` | Entidade que estende `Product` |
| 2 | `MyProductType.java` | Enum(s) específico(s) do domínio |
| 3 | `MyProductRepository.java` | Interface que estende `ProductRepository<T>` |
| 4 | `MyProductRequestDTO.java` | DTO para criação/atualização |
| 5 | `MyProductResponseDTO.java` | DTO para resposta |
| 6 | `MyProductService.java` | Service que estende `AbstractProductService<T>` |
| 7 | `MyProductController.java` | Controller REST |

**Total: ~7 arquivos por domínio**

---

## Código Reutilizado vs Código Específico

| Componente | Framework (reutilizado) | Exemplo (específico) |
|------------|------------------------|---------------------|
| Entidade base | `Product` (campos comuns) | Campos do domínio |
| CRUD básico | `AbstractProductService` | - |
| Validações comuns | `AbstractProductService.save()` | `validateProduct()` override |
| Repository base | `ProductRepository<T>` | Queries específicas |
| Exception handling | `GlobalExceptionHandler` | - |
| Billing/Delivery/Notification | Interfaces + Stubs | - |

**Reutilização estimada: ~70-80% do código**

---

## Dicas de Extensão

### 1. Adicionando novas queries

Adicione métodos no Repository específico. O Spring Data JPA gera a implementação automaticamente:

```java
// Exemplo: buscar vinhos premiados de uma região
List<Wine> findByRegionAndRatingGreaterThanEqual(String region, Double minRating);
```

### 2. Adicionando validações

Override o método `validateProduct()` no Service:

```java
@Override
protected void validateProduct(Wine wine) {
    // Suas validações aqui
}
```

### 3. Executando ações pós-salvamento

Override os hooks `beforeSave()`, `afterSave()`, `beforeDelete()`:

```java
@Override
protected void afterSave(Wine wine) {
    // Ex: enviar para sistema de estoque
    inventoryService.updateStock(wine.getId(), wine.getQuantity());
}
```

### 4. Integrando serviços externos

Injete as interfaces de infraestrutura (Billing, Delivery, Notification) e chame nos hooks:

```java
@Service
public class WineService extends AbstractProductService<Wine> {
    
    private final NotificationService notificationService;
    
    public WineService(WineRepository repository, NotificationService notificationService) {
        super(repository);
        this.notificationService = notificationService;
    }
    
    @Override
    protected void afterSave(Wine wine) {
        if (wine.getRating() != null && wine.getRating() >= 95) {
            // Notifica sobre vinho excepcional
            notificationService.sendCustomNotification(
                "Novo vinho 95+ pontos disponível: " + wine.getName()
            );
        }
    }
}
```

---

## Endpoints Gerados

Para um produto `Wine`, os endpoints padrão são:

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/v1/wines` | Lista todos |
| GET | `/api/v1/wines/{id}` | Busca por ID |
| POST | `/api/v1/wines` | Cria novo |
| PUT | `/api/v1/wines/{id}` | Atualiza |
| DELETE | `/api/v1/wines/{id}` | Remove |
| GET | `/api/v1/wines/active` | Lista ativos |
| GET | `/api/v1/wines/type/{type}` | Filtra por tipo |
| GET | `/api/v1/wines/grape/{grape}` | Filtra por uva |
| GET | `/api/v1/wines/winery/{winery}` | Filtra por vinícola |
| ... | ... | ... |

Customize conforme o domínio!
