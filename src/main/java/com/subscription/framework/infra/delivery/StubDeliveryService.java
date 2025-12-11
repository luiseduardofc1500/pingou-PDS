package com.subscription.framework.infra.delivery;

import com.subscription.framework.core.contract.DeliveryService;
import com.subscription.framework.core.domain.DeliveryRecord;
import com.subscription.framework.core.domain.Package;
import com.subscription.framework.core.domain.enums.DeliveryStatus;
import com.subscription.framework.core.repository.DeliveryRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;


public class StubDeliveryService implements DeliveryService {
    
    private static final Logger logger = LoggerFactory.getLogger(StubDeliveryService.class);
    
    private final DeliveryRecordRepository deliveryRecordRepository;
    
    public StubDeliveryService(DeliveryRecordRepository deliveryRecordRepository) {
        this.deliveryRecordRepository = deliveryRecordRepository;
    }
    
    @Override
    public DeliveryRecord scheduleDelivery(Long customerId, Package pkg, String deliveryAddress) {
        logger.info("Scheduling delivery for customer {} - Package: {} - Address: {}", 
                customerId, pkg.getName(), deliveryAddress);
        
        // Cria um registro de entrega para cada item do pacote
        // Aqui simplificamos criando apenas um registro por pacote
        DeliveryRecord record = new DeliveryRecord();
        record.setCustomerId(customerId);
        record.setPkg(pkg);
        record.setProduct(pkg.getItems().isEmpty() ? null : pkg.getItems().get(0).getProduct());
        record.setQuantity(pkg.getTotalItemCount());
        record.setSentAt(LocalDateTime.now());
        record.setStatus(DeliveryStatus.PENDING);
        record.setDeliveryAddress(deliveryAddress);
        
        return deliveryRecordRepository.save(record);
    }
    
    @Override
    public String processShipment(Long deliveryRecordId) {
        logger.info("Processing shipment for delivery record: {}", deliveryRecordId);
        
        DeliveryRecord record = deliveryRecordRepository.findById(deliveryRecordId)
                .orElseThrow(() -> new RuntimeException("Delivery record not found: " + deliveryRecordId));
        
        // Gera um código de rastreamento fictício
        String trackingCode = "BR" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        record.setTrackingCode(trackingCode);
        record.setStatus(DeliveryStatus.SHIPPED);
        
        deliveryRecordRepository.save(record);
        
        logger.info("Shipment processed. Tracking code: {}", trackingCode);
        return trackingCode;
    }
    
    @Override
    public TrackingInfo getTrackingInfo(String trackingCode) {
        logger.info("Getting tracking info for: {}", trackingCode);
        
        // Retorna informações de rastreamento fictícias
        List<TrackingEvent> events = List.of(
                new TrackingEvent(
                        LocalDateTime.now().minusDays(2).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                        "Distribution Center",
                        "Package received at distribution center"
                ),
                new TrackingEvent(
                        LocalDateTime.now().minusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                        "In Transit",
                        "Package in transit to destination"
                ),
                new TrackingEvent(
                        LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                        "Local Hub",
                        "Package arrived at local hub"
                )
        );
        
        return new TrackingInfo(
                trackingCode,
                "IN_TRANSIT",
                "Local Hub",
                LocalDateTime.now().plusDays(2).format(DateTimeFormatter.ISO_LOCAL_DATE),
                events
        );
    }
    
    @Override
    public ShippingRate calculateShipping(String destinationZipCode, int weight) {
        logger.info("Calculating shipping for ZIP: {} - Weight: {}g", destinationZipCode, weight);
        
        // Cálculo fictício de frete baseado no peso
        BigDecimal basePrice = new BigDecimal("10.00");
        BigDecimal pricePerKg = new BigDecimal("5.00");
        BigDecimal totalPrice = basePrice.add(pricePerKg.multiply(BigDecimal.valueOf(weight / 1000.0)));
        
        int estimatedDays = 5; // 5 dias úteis padrão
        
        return new ShippingRate("Standard Carrier", "Standard Shipping", totalPrice, estimatedDays);
    }
    
    @Override
    public List<DeliveryRecord> getPendingDeliveries(Long customerId) {
        return deliveryRecordRepository.findByCustomerIdAndStatus(customerId, DeliveryStatus.PENDING);
    }
    
    @Override
    public List<DeliveryRecord> getDeliveryHistory(Long customerId) {
        return deliveryRecordRepository.findByCustomerId(customerId);
    }
    
    @Override
    public void markAsDelivered(Long deliveryRecordId) {
        DeliveryRecord record = deliveryRecordRepository.findById(deliveryRecordId)
                .orElseThrow(() -> new RuntimeException("Delivery record not found: " + deliveryRecordId));
        
        record.markAsDelivered();
        deliveryRecordRepository.save(record);
        
        logger.info("Delivery marked as delivered: {}", deliveryRecordId);
    }
    
    @Override
    public void markAsFailed(Long deliveryRecordId, String reason) {
        DeliveryRecord record = deliveryRecordRepository.findById(deliveryRecordId)
                .orElseThrow(() -> new RuntimeException("Delivery record not found: " + deliveryRecordId));
        
        record.markAsFailed(reason);
        deliveryRecordRepository.save(record);
        
        logger.info("Delivery marked as failed: {} - Reason: {}", deliveryRecordId, reason);
    }
}
