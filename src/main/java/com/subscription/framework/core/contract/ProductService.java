package com.subscription.framework.core.contract;

import com.subscription.framework.core.domain.Product;

import java.util.List;
import java.util.Optional;


public interface ProductService<T extends Product, D, R> {
    
   
    List<D> findAll();
    
    
    List<D> findAllActive();
    
  
    Optional<D> findById(Long id);
    
    
    D create(R request);
    
    
    D update(Long id, R request);
    
    
    void deactivate(Long id);
    
  
    void activate(Long id);
    
    List<D> findByCategory(String category);
    
    boolean canAddToPackage(Long productId);
}
