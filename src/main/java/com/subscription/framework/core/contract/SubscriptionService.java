package com.subscription.framework.core.contract;

import com.subscription.framework.core.domain.Subscription;
import com.subscription.framework.core.domain.enums.SubscriptionStatus;

import java.util.List;
import java.util.Optional;

/**
 * Contrato para serviços de gerenciamento de assinaturas.
 * 
 * <p><b>Hotspot de Extensão:</b> Implemente esta interface para customizar
 * o comportamento de assinaturas no seu domínio.</p>
 * 
 * @author Subscription Framework
 * @version 1.0
 */
public interface SubscriptionService {
    
    /**
     * Cria uma nova assinatura para um cliente.
     * 
     * @param customerId ID do cliente
     * @param planId ID do plano
     * @return Assinatura criada
     */
    Subscription createSubscription(Long customerId, Long planId);
    
    /**
     * Cria uma assinatura com período de trial.
     * 
     * @param customerId ID do cliente
     * @param planId ID do plano
     * @param trialDays Dias de trial
     * @return Assinatura criada
     */
    Subscription createTrialSubscription(Long customerId, Long planId, int trialDays);
    
    /**
     * Ativa uma assinatura.
     * 
     * @param subscriptionId ID da assinatura
     * @return Assinatura ativada
     */
    Subscription activate(Long subscriptionId);
    
    /**
     * Pausa uma assinatura.
     * 
     * @param subscriptionId ID da assinatura
     * @return Assinatura pausada
     */
    Subscription pause(Long subscriptionId);
    
    /**
     * Cancela uma assinatura.
     * 
     * @param subscriptionId ID da assinatura
     * @return Assinatura cancelada
     */
    Subscription cancel(Long subscriptionId);
    
    /**
     * Renova uma assinatura.
     * 
     * @param subscriptionId ID da assinatura
     * @return Assinatura renovada
     */
    Subscription renew(Long subscriptionId);
    
    /**
     * Altera o plano de uma assinatura.
     * 
     * @param subscriptionId ID da assinatura
     * @param newPlanId ID do novo plano
     * @return Assinatura atualizada
     */
    Subscription changePlan(Long subscriptionId, Long newPlanId);
    
    /**
     * Busca uma assinatura pelo ID.
     */
    Optional<Subscription> findById(Long id);
    
    /**
     * Busca a assinatura ativa de um cliente.
     */
    Optional<Subscription> findActiveByCustomerId(Long customerId);
    
    /**
     * Lista todas as assinaturas de um cliente.
     */
    List<Subscription> findByCustomerId(Long customerId);
    
    /**
     * Lista assinaturas por status.
     */
    List<Subscription> findByStatus(SubscriptionStatus status);
    
    /**
     * Lista todas as assinaturas ativas de um plano específico.
     */
    List<Subscription> findActiveByPlanId(Long planId);
    
    /**
     * Verifica se o cliente tem uma assinatura ativa.
     */
    boolean hasActiveSubscription(Long customerId);
    
    /**
     * Processa assinaturas expiradas.
     */
    void processExpiredSubscriptions();
    
    /**
     * Processa trials que expiraram.
     */
    void processExpiredTrials();
}
