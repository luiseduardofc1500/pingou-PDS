package com.subscription.framework.core.service;

import com.subscription.framework.core.contract.SubscriptionService;
import com.subscription.framework.core.domain.Plan;
import com.subscription.framework.core.domain.Subscription;
import com.subscription.framework.core.domain.enums.SubscriptionStatus;
import com.subscription.framework.core.repository.PlanRepository;
import com.subscription.framework.core.repository.SubscriptionRepository;
import com.subscription.framework.api.exception.PlanNotFoundException;
import com.subscription.framework.api.exception.SubscriptionNotFoundException;
import com.subscription.framework.api.exception.DuplicateSubscriptionException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public abstract class AbstractSubscriptionService implements SubscriptionService {
    
    protected final SubscriptionRepository subscriptionRepository;
    protected final PlanRepository planRepository;
    
    protected AbstractSubscriptionService(SubscriptionRepository subscriptionRepository,
                                          PlanRepository planRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.planRepository = planRepository;
    }
    
    @Override
    @Transactional
    public Subscription createSubscription(Long customerId, Long planId) {
        // Verificar se já existe assinatura ativa
        if (subscriptionRepository.existsByCustomerIdAndStatus(customerId, SubscriptionStatus.ACTIVE)) {
            throw new DuplicateSubscriptionException("Customer already has an active subscription");
        }
        
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new PlanNotFoundException(planId));
        
        validateCustomer(customerId);
        beforeCreateSubscription(customerId, plan);
        
        Subscription subscription = new Subscription(
                customerId,
                plan,
                SubscriptionStatus.ACTIVE,
                LocalDate.now(),
                calculateExpirationDate(plan)
        );
        
        Subscription saved = subscriptionRepository.save(subscription);
        afterCreateSubscription(saved);
        
        return saved;
    }
    
    @Override
    @Transactional
    public Subscription createTrialSubscription(Long customerId, Long planId, int trialDays) {
        if (subscriptionRepository.existsByCustomerId(customerId)) {
            throw new DuplicateSubscriptionException("Customer already has a subscription");
        }
        
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new PlanNotFoundException(planId));
        
        validateCustomer(customerId);
        
        Subscription subscription = new Subscription(
                customerId,
                plan,
                SubscriptionStatus.ACTIVE,
                LocalDate.now(),
                null
        );
        subscription.startTrial(trialDays > 0 ? trialDays : plan.getTrialDays());
        
        return subscriptionRepository.save(subscription);
    }
    
    @Override
    @Transactional
    public Subscription activate(Long subscriptionId) {
        Subscription subscription = findOrThrow(subscriptionId);
        subscription.activate();
        return subscriptionRepository.save(subscription);
    }
    
    @Override
    @Transactional
    public Subscription pause(Long subscriptionId) {
        Subscription subscription = findOrThrow(subscriptionId);
        beforePause(subscription);
        subscription.pause();
        Subscription saved = subscriptionRepository.save(subscription);
        afterPause(saved);
        return saved;
    }
    
    @Override
    @Transactional
    public Subscription cancel(Long subscriptionId) {
        Subscription subscription = findOrThrow(subscriptionId);
        beforeCancel(subscription);
        subscription.cancel();
        Subscription saved = subscriptionRepository.save(subscription);
        afterCancel(saved);
        return saved;
    }
    
    @Override
    @Transactional
    public Subscription renew(Long subscriptionId) {
        Subscription subscription = findOrThrow(subscriptionId);
        beforeRenew(subscription);
        subscription.renew();
        Subscription saved = subscriptionRepository.save(subscription);
        afterRenew(saved);
        return saved;
    }
    
    @Override
    @Transactional
    public Subscription changePlan(Long subscriptionId, Long newPlanId) {
        Subscription subscription = findOrThrow(subscriptionId);
        Plan newPlan = planRepository.findById(newPlanId)
                .orElseThrow(() -> new PlanNotFoundException(newPlanId));
        
        beforeChangePlan(subscription, newPlan);
        subscription.setPlan(newPlan);
        Subscription saved = subscriptionRepository.save(subscription);
        afterChangePlan(saved);
        
        return saved;
    }
    
    @Override
    public Optional<Subscription> findById(Long id) {
        return subscriptionRepository.findById(id);
    }
    
    @Override
    public Optional<Subscription> findActiveByCustomerId(Long customerId) {
        return subscriptionRepository.findByCustomerIdAndStatus(customerId, SubscriptionStatus.ACTIVE);
    }
    
    @Override
    public List<Subscription> findByCustomerId(Long customerId) {
        return subscriptionRepository.findByCustomerId(customerId);
    }
    
    @Override
    public List<Subscription> findByStatus(SubscriptionStatus status) {
        return subscriptionRepository.findByStatus(status);
    }
    
    @Override
    public List<Subscription> findActiveByPlanId(Long planId) {
        return subscriptionRepository.findByPlanIdAndStatus(planId, SubscriptionStatus.ACTIVE);
    }
    
    @Override
    public boolean hasActiveSubscription(Long customerId) {
        return subscriptionRepository.existsByCustomerIdAndStatus(customerId, SubscriptionStatus.ACTIVE);
    }
    
    @Override
    @Transactional
    public void processExpiredSubscriptions() {
        LocalDate today = LocalDate.now();
        List<Subscription> expiredSubscriptions = subscriptionRepository
                .findByExpirationDateBetweenAndStatus(today.minusDays(1), today, SubscriptionStatus.ACTIVE);
        
        for (Subscription subscription : expiredSubscriptions) {
            subscription.setStatus(SubscriptionStatus.EXPIRED);
            subscriptionRepository.save(subscription);
            onSubscriptionExpired(subscription);
        }
    }
    
    @Override
    @Transactional
    public void processExpiredTrials() {
        LocalDate today = LocalDate.now();
        List<Subscription> expiredTrials = subscriptionRepository
                .findByIsTrialTrueAndTrialEndDate(today);
        
        for (Subscription subscription : expiredTrials) {
            subscription.setIsTrial(false);
            onTrialExpired(subscription);
            subscriptionRepository.save(subscription);
        }
    }
    
    // ========== Métodos auxiliares ==========
    
    protected Subscription findOrThrow(Long subscriptionId) {
        return subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new SubscriptionNotFoundException(subscriptionId));
    }
    
    protected LocalDate calculateExpirationDate(Plan plan) {
        return LocalDate.now().plusDays(plan.getDeliveryFrequency().getDaysInterval());
    }
    
    // ========== Hooks para extensão ==========
    
    /**
     * Valida se o cliente existe e pode criar uma assinatura.
     * Deve ser implementado pela aplicação concreta.
     */
    protected abstract void validateCustomer(Long customerId);
    
    /**
     * Hook chamado antes de criar uma assinatura.
     */
    protected void beforeCreateSubscription(Long customerId, Plan plan) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado após criar uma assinatura.
     */
    protected void afterCreateSubscription(Subscription subscription) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado antes de pausar uma assinatura.
     */
    protected void beforePause(Subscription subscription) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado após pausar uma assinatura.
     */
    protected void afterPause(Subscription subscription) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado antes de cancelar uma assinatura.
     */
    protected void beforeCancel(Subscription subscription) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado após cancelar uma assinatura.
     */
    protected void afterCancel(Subscription subscription) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado antes de renovar uma assinatura.
     */
    protected void beforeRenew(Subscription subscription) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado após renovar uma assinatura.
     */
    protected void afterRenew(Subscription subscription) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado antes de mudar de plano.
     */
    protected void beforeChangePlan(Subscription subscription, Plan newPlan) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado após mudar de plano.
     */
    protected void afterChangePlan(Subscription subscription) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado quando uma assinatura expira.
     */
    protected void onSubscriptionExpired(Subscription subscription) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado quando um trial expira.
     */
    protected void onTrialExpired(Subscription subscription) {
        // Default: nenhuma ação
    }
}
