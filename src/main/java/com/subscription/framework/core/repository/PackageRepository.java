package com.subscription.framework.core.repository;

import com.subscription.framework.core.domain.Package;
import com.subscription.framework.core.domain.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositório para pacotes.
 * 
 * @author Subscription Framework
 * @version 1.0
 */
@Repository
public interface PackageRepository extends JpaRepository<Package, Long> {
    
    /**
     * Busca pacotes por plano.
     */
    List<Package> findByPlan(Plan plan);
    
    /**
     * Busca pacotes por plano ID.
     */
    List<Package> findByPlanId(Long planId);
    
    /**
     * Busca pacotes por mês e ano.
     */
    List<Package> findByMonthAndYear(Integer month, Integer year);
    
    /**
     * Busca pacotes por plano, mês e ano.
     */
    List<Package> findByPlanIdAndMonthAndYear(Long planId, Integer month, Integer year);
    
    /**
     * Busca pacotes ativos.
     */
    List<Package> findByActiveTrue();
    
    /**
     * Busca pacotes por data de entrega.
     */
    List<Package> findByDeliveryDate(LocalDate deliveryDate);
    
    /**
     * Busca pacotes com data de entrega após uma data específica.
     */
    List<Package> findByDeliveryDateAfterAndActiveTrue(LocalDate date);
    
    /**
     * Busca pacotes por tema.
     */
    List<Package> findByThemeContainingIgnoreCase(String theme);
}
