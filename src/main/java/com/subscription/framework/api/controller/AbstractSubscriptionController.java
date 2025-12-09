package com.subscription.framework.api.controller;

import com.subscription.framework.api.dto.SubscriptionRequestDTO;
import com.subscription.framework.api.dto.SubscriptionResponseDTO;
import com.subscription.framework.core.contract.SubscriptionService;
import com.subscription.framework.core.domain.Subscription;
import com.subscription.framework.api.exception.SubscriptionNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller base para gerenciamento de assinaturas.
 * 
 * <p><b>Hotspot de Extensão:</b> Estenda este controller e adicione
 * a anotação @RestController e @RequestMapping apropriados.</p>
 * 
 * @author Subscription Framework
 * @version 1.0
 */
public abstract class AbstractSubscriptionController {
    
    protected final SubscriptionService subscriptionService;
    
    protected AbstractSubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }
    
    @GetMapping
    public ResponseEntity<List<SubscriptionResponseDTO>> findAll() {
        List<SubscriptionResponseDTO> subscriptions = subscriptionService
                .findByStatus(com.subscription.framework.core.domain.enums.SubscriptionStatus.ACTIVE)
                .stream()
                .map(SubscriptionResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(subscriptions);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionResponseDTO> findById(@PathVariable Long id) {
        Subscription subscription = subscriptionService.findById(id)
                .orElseThrow(() -> new SubscriptionNotFoundException(id));
        return ResponseEntity.ok(SubscriptionResponseDTO.fromEntity(subscription));
    }
    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<SubscriptionResponseDTO>> findByCustomer(@PathVariable Long customerId) {
        List<SubscriptionResponseDTO> subscriptions = subscriptionService
                .findByCustomerId(customerId)
                .stream()
                .map(SubscriptionResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(subscriptions);
    }
    
    @GetMapping("/customer/{customerId}/active")
    public ResponseEntity<SubscriptionResponseDTO> findActiveByCustomer(@PathVariable Long customerId) {
        Subscription subscription = subscriptionService.findActiveByCustomerId(customerId)
                .orElseThrow(() -> new SubscriptionNotFoundException("No active subscription found for customer"));
        return ResponseEntity.ok(SubscriptionResponseDTO.fromEntity(subscription));
    }
    
    @PostMapping
    public ResponseEntity<SubscriptionResponseDTO> create(@RequestBody SubscriptionRequestDTO request) {
        Subscription subscription;
        
        if (Boolean.TRUE.equals(request.getStartTrial())) {
            int trialDays = request.getTrialDays() != null ? request.getTrialDays() : 0;
            subscription = subscriptionService.createTrialSubscription(
                    request.getCustomerId(), 
                    request.getPlanId(), 
                    trialDays);
        } else {
            subscription = subscriptionService.createSubscription(
                    request.getCustomerId(), 
                    request.getPlanId());
        }
        
        return ResponseEntity.ok(SubscriptionResponseDTO.fromEntity(subscription));
    }
    
    @PostMapping("/{id}/activate")
    public ResponseEntity<SubscriptionResponseDTO> activate(@PathVariable Long id) {
        Subscription subscription = subscriptionService.activate(id);
        return ResponseEntity.ok(SubscriptionResponseDTO.fromEntity(subscription));
    }
    
    @PostMapping("/{id}/pause")
    public ResponseEntity<SubscriptionResponseDTO> pause(@PathVariable Long id) {
        Subscription subscription = subscriptionService.pause(id);
        return ResponseEntity.ok(SubscriptionResponseDTO.fromEntity(subscription));
    }
    
    @PostMapping("/{id}/cancel")
    public ResponseEntity<SubscriptionResponseDTO> cancel(@PathVariable Long id) {
        Subscription subscription = subscriptionService.cancel(id);
        return ResponseEntity.ok(SubscriptionResponseDTO.fromEntity(subscription));
    }
    
    @PostMapping("/{id}/renew")
    public ResponseEntity<SubscriptionResponseDTO> renew(@PathVariable Long id) {
        Subscription subscription = subscriptionService.renew(id);
        return ResponseEntity.ok(SubscriptionResponseDTO.fromEntity(subscription));
    }
    
    @PutMapping("/{id}/plan/{planId}")
    public ResponseEntity<SubscriptionResponseDTO> changePlan(
            @PathVariable Long id, @PathVariable Long planId) {
        Subscription subscription = subscriptionService.changePlan(id, planId);
        return ResponseEntity.ok(SubscriptionResponseDTO.fromEntity(subscription));
    }
}
