package com.subscription.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition(
    info = @Info(
        title = "Subscription Framework API",
        version = "1.0",
        description = "Framework extensível para sistemas de assinatura de produtos. " +
                      "Fornece endpoints base para gerenciamento de Planos, Pacotes e Assinaturas.",
        contact = @Contact(
            name = "Subscription Framework Team"
        )
    ),
    servers = { 
        @Server(url = "/", description = "Default Server URL")
    }
)
@SpringBootApplication(scanBasePackages = {"com.subscription.framework", "com.subscription.examples"})
@EntityScan(basePackages = {"com.subscription.framework", "com.subscription.examples"})
@EnableJpaRepositories(basePackages = {"com.subscription.framework", "com.subscription.examples"})
public class SubscriptionFrameworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubscriptionFrameworkApplication.class, args);
    }
}
