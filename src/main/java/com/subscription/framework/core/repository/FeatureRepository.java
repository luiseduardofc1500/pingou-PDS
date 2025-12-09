package com.subscription.framework.core.repository;

import com.subscription.framework.core.domain.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para features.
 * 
 * @author Subscription Framework
 * @version 1.0
 */
@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
    
    /**
     * Busca feature pelo código.
     */
    Optional<Feature> findByCode(String code);
    
    /**
     * Verifica se existe feature com o código.
     */
    boolean existsByCode(String code);
    
    /**
     * Busca features ativas.
     */
    List<Feature> findByActiveTrue();
}
