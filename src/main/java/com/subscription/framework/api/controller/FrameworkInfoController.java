package com.subscription.framework.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller que expõe informações sobre o Subscription Framework.
 * 
 * <p>Este controller fornece endpoints para verificar o status do framework
 * e obter informações sobre como usá-lo.</p>
 * 
 * @author Subscription Framework
 * @version 1.0
 */
@RestController
@RequestMapping("/framework")
@Tag(name = "Framework Info", description = "Informações sobre o Subscription Framework")
public class FrameworkInfoController {

    @GetMapping("/health")
    @Operation(summary = "Health check", description = "Verifica se o framework está funcionando")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("framework", "Subscription Framework");
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    @Operation(summary = "Framework info", description = "Retorna informações detalhadas sobre o framework")
    public ResponseEntity<Map<String, Object>> info() {
        Map<String, Object> response = new HashMap<>();
        response.put("name", "Subscription Framework");
        response.put("version", "1.0.0");
        response.put("description", "Framework extensível para sistemas de assinatura de produtos");
        
        Map<String, String> coreComponents = new HashMap<>();
        coreComponents.put("Plan", "Representa um plano de assinatura com tier, preço e features");
        coreComponents.put("Package", "Agrupa produtos para entrega periódica");
        coreComponents.put("Subscription", "Gerencia o ciclo de vida de uma assinatura");
        coreComponents.put("Product", "Classe base abstrata para qualquer tipo de produto");
        response.put("coreComponents", coreComponents);
        
        Map<String, String> abstractControllers = new HashMap<>();
        abstractControllers.put("AbstractPlanController", "Controller base para gerenciamento de planos");
        abstractControllers.put("AbstractPackageController", "Controller base para gerenciamento de pacotes");
        abstractControllers.put("AbstractSubscriptionController", "Controller base para gerenciamento de assinaturas");
        response.put("abstractControllers", abstractControllers);
        
        Map<String, String> services = new HashMap<>();
        services.put("BillingService", "Interface para integração com sistemas de pagamento");
        services.put("DeliveryService", "Interface para integração com sistemas de entrega");
        services.put("NotificationService", "Interface para integração com sistemas de notificação");
        response.put("extensionPoints", services);
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/guide")
    @Operation(summary = "Usage guide", description = "Retorna um guia de como usar o framework")
    public ResponseEntity<Map<String, Object>> guide() {
        Map<String, Object> response = new HashMap<>();
        response.put("title", "Guia de Uso do Subscription Framework");
        
        List<Map<String, String>> steps = List.of(
            Map.of(
                "step", "1",
                "title", "Defina seu Produto",
                "description", "Estenda a classe Product para criar seu tipo de produto específico. " +
                              "Ex: class Cachaca extends Product { ... }"
            ),
            Map.of(
                "step", "2",
                "title", "Crie seu Plano",
                "description", "Estenda a classe Plan para definir os planos de assinatura disponíveis. " +
                              "Ex: class CachacaPlan extends Plan { ... }"
            ),
            Map.of(
                "step", "3",
                "title", "Configure os Pacotes",
                "description", "Estenda a classe Package para definir como os produtos são agrupados. " +
                              "Ex: class CachacaPackage extends Package { ... }"
            ),
            Map.of(
                "step", "4",
                "title", "Implemente a Assinatura",
                "description", "Estenda a classe Subscription para gerenciar o ciclo de vida. " +
                              "Ex: class CachacaSubscription extends Subscription { ... }"
            ),
            Map.of(
                "step", "5",
                "title", "Crie os Controllers",
                "description", "Estenda os AbstractControllers e adicione @RestController e @RequestMapping"
            ),
            Map.of(
                "step", "6",
                "title", "Configure os Serviços",
                "description", "Implemente BillingService, DeliveryService e NotificationService " +
                              "ou use os Stubs fornecidos para desenvolvimento"
            )
        );
        response.put("steps", steps);
        
        return ResponseEntity.ok(response);
    }
}
