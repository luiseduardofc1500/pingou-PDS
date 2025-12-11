package com.subscription.framework.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI subscriptionFrameworkOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Subscription Framework API")
                .description("""
                    ## Framework Extensível para Sistemas de Assinatura de Produtos
                    
                    Este framework fornece uma base completa para criar sistemas de assinatura,
                    incluindo:
                    
                    - **Planos**: Gerenciamento de planos de assinatura com diferentes tiers
                    - **Pacotes**: Agrupamento de produtos em pacotes de entrega
                    - **Assinaturas**: Ciclo de vida completo de assinaturas
                    - **Produtos**: Base abstrata para qualquer tipo de produto
                    
                    ### Como Usar
                    
                    1. Estenda as classes abstratas do framework
                    2. Implemente seus próprios produtos e controllers
                    3. Configure os serviços de billing, delivery e notification
                    
                    ### Endpoints Disponíveis
                    
                    Os endpoints abaixo são os controllers base que podem ser estendidos
                    para criar APIs específicas para seu domínio.
                    """)
                .version("1.0.0")
                .contact(new Contact()
                    .name("Subscription Framework")
                    .email("framework@subscription.com")))
            .tags(List.of(
                new Tag().name("Plans").description("Operações de gerenciamento de planos"),
                new Tag().name("Packages").description("Operações de gerenciamento de pacotes"),
                new Tag().name("Subscriptions").description("Operações de gerenciamento de assinaturas"),
                new Tag().name("Framework Info").description("Informações sobre o framework")
            ));
    }
}
