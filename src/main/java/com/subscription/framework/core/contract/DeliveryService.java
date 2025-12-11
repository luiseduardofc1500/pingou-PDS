package com.subscription.framework.core.contract;

import com.subscription.framework.core.domain.DeliveryRecord;
import com.subscription.framework.core.domain.Package;

import java.util.List;


public interface DeliveryService {
    
   
    DeliveryRecord scheduleDelivery(Long customerId, Package pkg, String deliveryAddress);
    
   
    String processShipment(Long deliveryRecordId);
    
  
    TrackingInfo getTrackingInfo(String trackingCode);
    
  
    ShippingRate calculateShipping(String destinationZipCode, int weight);
    
    
    List<DeliveryRecord> getPendingDeliveries(Long customerId);
    
  
    List<DeliveryRecord> getDeliveryHistory(Long customerId);
    
    void markAsDelivered(Long deliveryRecordId);
    
 
    void markAsFailed(Long deliveryRecordId, String reason);
    
    record TrackingInfo(
        String trackingCode,
        String status,
        String currentLocation,
        String estimatedDeliveryDate,
        List<TrackingEvent> events
    ) {}
    
    record TrackingEvent(
        String timestamp,
        String location,
        String description
    ) {}
    

    record ShippingRate(
        String carrier,
        String service,
        java.math.BigDecimal price,
        int estimatedDays
    ) {}
}
