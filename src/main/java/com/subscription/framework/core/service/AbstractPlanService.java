package com.subscription.framework.core.service;

import com.subscription.framework.core.contract.PlanService;
import com.subscription.framework.core.domain.Feature;
import com.subscription.framework.core.domain.Plan;
import com.subscription.framework.core.repository.FeatureRepository;
import com.subscription.framework.core.repository.PlanRepository;
import com.subscription.framework.core.repository.SubscriptionRepository;
import com.subscription.framework.api.exception.PlanNotFoundException;
import com.subscription.framework.api.exception.FeatureNotFoundException;
import com.subscription.framework.api.exception.DuplicatePlanNameException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public abstract class AbstractPlanService implements PlanService {
    
    protected final PlanRepository planRepository;
    protected final FeatureRepository featureRepository;
    protected final SubscriptionRepository subscriptionRepository;
    
    protected AbstractPlanService(PlanRepository planRepository, 
                                  FeatureRepository featureRepository,
                                  SubscriptionRepository subscriptionRepository) {
        this.planRepository = planRepository;
        this.featureRepository = featureRepository;
        this.subscriptionRepository = subscriptionRepository;
    }
    
    @Override
    public List<Plan> findAll() {
        return planRepository.findAll();
    }
    
    @Override
    public List<Plan> findAllActive() {
        return planRepository.findByActiveTrue();
    }
    
    @Override
    public Optional<Plan> findById(Long id) {
        return planRepository.findById(id);
    }
    
    @Override
    public Optional<Plan> findByName(String name) {
        return planRepository.findByName(name);
    }
    
    @Override
    @Transactional
    public Plan create(Plan plan) {
        if (planRepository.existsByName(plan.getName())) {
            throw new DuplicatePlanNameException(plan.getName());
        }
        
        validatePlan(plan);
        beforeCreate(plan);
        Plan saved = planRepository.save(plan);
        afterCreate(saved);
        return saved;
    }
    
    @Override
    @Transactional
    public Plan update(Long id, Plan plan) {
        Plan existing = planRepository.findById(id)
                .orElseThrow(() -> new PlanNotFoundException(id));
        
        // Verificar nome duplicado se mudou
        if (!existing.getName().equals(plan.getName()) && 
            planRepository.existsByName(plan.getName())) {
            throw new DuplicatePlanNameException(plan.getName());
        }
        
        validatePlan(plan);
        beforeUpdate(existing, plan);
        
        existing.setName(plan.getName());
        existing.setDescription(plan.getDescription());
        existing.setPrice(plan.getPrice());
        existing.setMaxItemsPerDelivery(plan.getMaxItemsPerDelivery());
        existing.setDeliveryFrequency(plan.getDeliveryFrequency());
        existing.setTier(plan.getTier());
        existing.setTrialDays(plan.getTrialDays());
        existing.setAnnualDiscountPercent(plan.getAnnualDiscountPercent());
        
        Plan saved = planRepository.save(existing);
        afterUpdate(saved);
        return saved;
    }
    
    @Override
    @Transactional
    public void deactivate(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new PlanNotFoundException(id));
        plan.setActive(false);
        planRepository.save(plan);
    }
    
    @Override
    @Transactional
    public void activate(Long id) {
        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new PlanNotFoundException(id));
        plan.setActive(true);
        planRepository.save(plan);
    }
    
    @Override
    @Transactional
    public Plan addFeature(Long planId, Long featureId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new PlanNotFoundException(planId));
        Feature feature = featureRepository.findById(featureId)
                .orElseThrow(() -> new FeatureNotFoundException(featureId));
        
        plan.addFeature(feature);
        return planRepository.save(plan);
    }
    
    @Override
    @Transactional
    public Plan removeFeature(Long planId, Long featureId) {
        Plan plan = planRepository.findById(planId)
                .orElseThrow(() -> new PlanNotFoundException(planId));
        Feature feature = featureRepository.findById(featureId)
                .orElseThrow(() -> new FeatureNotFoundException(featureId));
        
        plan.removeFeature(feature);
        return planRepository.save(plan);
    }
    
    @Override
    public List<Plan> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        return planRepository.findByPriceBetweenAndActiveTrue(minPrice, maxPrice);
    }
    
    @Override
    public boolean canDelete(Long planId) {
        return subscriptionRepository.countActiveByPlanId(planId) == 0;
    }
    
    // ========== Hooks para extensão ==========
    
    /**
     * Hook chamado antes de criar um plano.
     * Pode ser sobrescrito para adicionar lógica customizada.
     */
    protected void beforeCreate(Plan plan) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado após criar um plano.
     */
    protected void afterCreate(Plan plan) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado antes de atualizar um plano.
     */
    protected void beforeUpdate(Plan existing, Plan updated) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado após atualizar um plano.
     */
    protected void afterUpdate(Plan plan) {
        // Default: nenhuma ação
    }
    
    /**
     * Valida um plano antes de salvar.
     * Pode ser sobrescrito para adicionar validações customizadas.
     */
    protected void validatePlan(Plan plan) {
        if (plan.getName() == null || plan.getName().isBlank()) {
            throw new IllegalArgumentException("Plan name is required");
        }
        if (plan.getPrice() == null || plan.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Plan price must be greater than zero");
        }
        if (plan.getMaxItemsPerDelivery() == null || plan.getMaxItemsPerDelivery() <= 0) {
            throw new IllegalArgumentException("Max items per delivery must be greater than zero");
        }
    }
}
