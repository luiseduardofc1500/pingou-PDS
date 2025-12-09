package com.subscription.framework.core.contract;

import com.subscription.framework.core.domain.DeliveryRecord;
import com.subscription.framework.core.domain.Package;

import java.util.List;

/**
 * Contrato para integração com sistemas de entrega/logística.
 * 
 * <p><b>Hotspot de Extensão:</b> Implemente esta interface para integrar
 * com seu sistema de logística (Correios, transportadoras, etc.)</p>
 * 
 * @author Subscription Framework
 * @version 1.0
 */
public interface DeliveryService {
    
    /**
     * Agenda o envio de um pacote para um cliente.
     * 
     * @param customerId ID do cliente
     * @param pkg Pacote a ser enviado
     * @param deliveryAddress Endereço de entrega
     * @return Registro de entrega criado
     */
    DeliveryRecord scheduleDelivery(Long customerId, Package pkg, String deliveryAddress);
    
    /**
     * Processa o envio de um pacote (gera etiqueta, etc.).
     * 
     * @param deliveryRecordId ID do registro de entrega
     * @return Código de rastreamento
     */
    String processShipment(Long deliveryRecordId);
    
    /**
     * Obtém o status de rastreamento de uma entrega.
     * 
     * @param trackingCode Código de rastreamento
     * @return Informações de rastreamento
     */
    TrackingInfo getTrackingInfo(String trackingCode);
    
    /**
     * Calcula o frete para um endereço.
     * 
     * @param destinationZipCode CEP de destino
     * @param weight Peso em gramas
     * @return Valor do frete
     */
    ShippingRate calculateShipping(String destinationZipCode, int weight);
    
    /**
     * Lista as entregas pendentes de um cliente.
     */
    List<DeliveryRecord> getPendingDeliveries(Long customerId);
    
    /**
     * Lista o histórico de entregas de um cliente.
     */
    List<DeliveryRecord> getDeliveryHistory(Long customerId);
    
    /**
     * Marca uma entrega como concluída.
     * 
     * @param deliveryRecordId ID do registro de entrega
     */
    void markAsDelivered(Long deliveryRecordId);
    
    /**
     * Registra uma falha na entrega.
     * 
     * @param deliveryRecordId ID do registro de entrega
     * @param reason Motivo da falha
     */
    void markAsFailed(Long deliveryRecordId, String reason);
    
    /**
     * Classe para informações de rastreamento.
     */
    record TrackingInfo(
        String trackingCode,
        String status,
        String currentLocation,
        String estimatedDeliveryDate,
        List<TrackingEvent> events
    ) {}
    
    /**
     * Classe para eventos de rastreamento.
     */
    record TrackingEvent(
        String timestamp,
        String location,
        String description
    ) {}
    
    /**
     * Classe para taxa de frete.
     */
    record ShippingRate(
        String carrier,
        String service,
        java.math.BigDecimal price,
        int estimatedDays
    ) {}
}
