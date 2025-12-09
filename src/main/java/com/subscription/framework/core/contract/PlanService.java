package com.subscription.framework.core.contract;

import com.subscription.framework.core.domain.Plan;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Contrato para serviços de gerenciamento de planos.
 * 
 * @author Subscription Framework
 * @version 1.0
 */
public interface PlanService {
    
    /**
     * Lista todos os planos.
     */
    List<Plan> findAll();
    
    /**
     * Lista todos os planos ativos.
     */
    List<Plan> findAllActive();
    
    /**
     * Busca um plano pelo ID.
     */
    Optional<Plan> findById(Long id);
    
    /**
     * Busca um plano pelo nome.
     */
    Optional<Plan> findByName(String name);
    
    /**
     * Cria um novo plano.
     */
    Plan create(Plan plan);
    
    /**
     * Atualiza um plano existente.
     */
    Plan update(Long id, Plan plan);
    
    /**
     * Desativa um plano.
     */
    void deactivate(Long id);
    
    /**
     * Ativa um plano.
     */
    void activate(Long id);
    
    /**
     * Adiciona uma feature a um plano.
     */
    Plan addFeature(Long planId, Long featureId);
    
    /**
     * Remove uma feature de um plano.
     */
    Plan removeFeature(Long planId, Long featureId);
    
    /**
     * Busca planos por faixa de preço.
     */
    List<Plan> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    
    /**
     * Verifica se um plano pode ser deletado (não tem assinaturas ativas).
     */
    boolean canDelete(Long planId);
}
