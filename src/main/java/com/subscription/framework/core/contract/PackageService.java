package com.subscription.framework.core.contract;

import com.subscription.framework.core.domain.Package;
import com.subscription.framework.core.domain.PackageItem;

import java.util.List;
import java.util.Optional;


public interface PackageService {
    
    
    List<Package> findAll();
    
    
    Optional<Package> findById(Long id);
    
   
    List<Package> findByPlanId(Long planId);
    
    
    List<Package> findByMonthAndYear(Integer month, Integer year);
  
    List<Package> findByCustomerId(Long customerId);
    
   
    Package create(Package pkg);
  
    Package update(Long id, Package pkg);
    
    
    void delete(Long id);
    
 
    Package addItem(Long packageId, Long productId, Integer quantity);
    
  
    Package removeItem(Long packageId, Long itemId);
    
   
    Package updateItemQuantity(Long packageId, Long itemId, Integer newQuantity);

    List<PackageItem> getItems(Long packageId);
    
    
    void registerDeliveryForSubscribers(Long packageId);
}
