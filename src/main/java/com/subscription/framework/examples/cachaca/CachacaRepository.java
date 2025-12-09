package com.subscription.framework.examples.cachaca;

import com.subscription.framework.core.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório específico para cachaças.
 * 
 * Estende o repositório genérico de produtos e adiciona
 * métodos específicos do domínio de cachaças.
 */
@Repository
public interface CachacaRepository extends ProductRepository<Cachaca> {
    
    /**
     * Busca cachaças por região.
     */
    List<Cachaca> findByRegionAndActiveTrue(String region);
    
    /**
     * Busca cachaças por tipo.
     */
    List<Cachaca> findByCachacaTypeAndActiveTrue(CachacaType cachacaType);
    
    /**
     * Busca cachaças por tipo de envelhecimento.
     */
    List<Cachaca> findByAgingTypeAndActiveTrue(AgingType agingType);
    
    /**
     * Busca cachaças por destilaria.
     */
    List<Cachaca> findByDistilleryContainingIgnoreCaseAndActiveTrue(String distillery);
    
    /**
     * Busca cachaças envelhecidas por no mínimo X meses.
     */
    List<Cachaca> findByAgingMonthsGreaterThanEqualAndActiveTrue(Integer months);
}
