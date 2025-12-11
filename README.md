# Subscription Framework

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-brightgreen)
![Java](https://img.shields.io/badge/Java-21-orange)
![H2 Database](https://img.shields.io/badge/H2-Database-blue)

## Sobre o Projeto

O **Subscription Framework** � um framework extens�vel para criar sistemas de assinatura de produtos. Ele fornece uma base s�lida com componentes abstratos que podem ser estendidos para qualquer tipo de produto ou servi�o.

### Funcionalidades do Framework

-  **Gerenciamento de Planos** - Classes abstratas para diferentes planos de assinatura
-  **Gerenciamento de Pacotes** - Agrupamento de produtos para entrega
-  **Ciclo de Vida de Assinaturas** - Estados: Trial, Active, Paused, Cancelled, Expired
-  **Produtos Gen�ricos** - Classe base abstrata para qualquer tipo de produto
-  **Integra��o de Billing** - Interface para sistemas de pagamento
-  **Integra��o de Delivery** - Interface para sistemas de entrega
-  **Notifica��es** - Interface para sistemas de notifica��o
-  **Documenta��o Swagger** - API documentada automaticamente

## Arquitetura do Framework

```
com.subscription.framework/
 api/
    controller/           # Controllers abstratos
    dto/                  # DTOs de request/response
    exception/            # Exce��es customizadas
 core/
    contract/             # Interfaces de servi�o
    domain/               # Entidades de dom�nio
    repository/           # Reposit�rios JPA
    service/              # Implementa��es abstratas
 infra/
     billing/              # Stub de billing
     delivery/             # Stub de delivery
     notification/         # Stub de notification
     config/               # Configura��es
```

## Como Usar o Framework

### 1. Defina seu Produto

```java
@Entity
@DiscriminatorValue("MY_PRODUCT")
public class MyProduct extends Product {
    private String customField;
}
```

### 2. Crie seu Plano

```java
@Entity
@DiscriminatorValue("MY_PLAN")
public class MyPlan extends Plan {
    // campos espec�ficos do seu plano
}
```

### 3. Implemente seu Controller

```java
@RestController
@RequestMapping("/api/my-subscriptions")
public class MySubscriptionController extends AbstractSubscriptionController {
    public MySubscriptionController(SubscriptionService service) {
        super(service);
    }
}
```

## Executando o Framework

### Pr�-requisitos

- Java 21+
- Maven 3.8+

### Comandos

```bash
# Compilar
./mvnw clean compile

# Executar
./mvnw spring-boot:run

# Testes
./mvnw test
```

### Endpoints Dispon�veis

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/v3/api-docs
- **H2 Console**: http://localhost:8080/h2-console
- **Health Check**: http://localhost:8080/framework/health
- **Framework Info**: http://localhost:8080/framework/info

## Licen�a

Este projeto est� licenciado sob a licen�a MIT.
