package com.subscription.framework.core.repository;

import com.subscription.framework.core.domain.Plan;
import com.subscription.framework.core.domain.enums.PlanTier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para planos de assinatura.
 * 
 * @author Subscription Framework
 * @version 1.0
 */
@Repository
public interface PlanRepository extends JpaRepository<Plan, Long> {
    
    /**
     * Busca um plano pelo nome.
     */
    Optional<Plan> findByName(String name);
    
    /**
     * Verifica se existe um plano com o nome informado.
     */
    boolean existsByName(String name);
    
    /**
     * Busca planos ativos.
     */
    List<Plan> findByActiveTrue();
    
    /**
     * Busca planos por tier.
     */
    List<Plan> findByTierAndActiveTrue(PlanTier tier);
    
    /**
     * Busca planos por faixa de preço.
     */
    List<Plan> findByPriceBetweenAndActiveTrue(BigDecimal minPrice, BigDecimal maxPrice);
    
    /**
     * Busca planos ordenados por preço.
     */
    List<Plan> findByActiveTrueOrderByPriceAsc();
}
