package com.subscription.framework.core.contract;

import com.subscription.framework.core.domain.Plan;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public interface PlanService {
    
  
    List<Plan> findAll();
  
    List<Plan> findAllActive();
    
   
    Optional<Plan> findById(Long id);
    
    
    Optional<Plan> findByName(String name);
   
    Plan create(Plan plan);
    
    
    Plan update(Long id, Plan plan);
    
    
    void deactivate(Long id);
    
   
    void activate(Long id);
    
    
    Plan addFeature(Long planId, Long featureId);
    
   
    Plan removeFeature(Long planId, Long featureId);
    
   
    List<Plan> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
    
    
    boolean canDelete(Long planId);
}
