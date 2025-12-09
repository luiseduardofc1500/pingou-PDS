package com.subscription.framework.core.repository;

import com.subscription.framework.core.domain.DeliveryRecord;
import com.subscription.framework.core.domain.enums.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositório para registros de entrega.
 * 
 * @author Subscription Framework
 * @version 1.0
 */
@Repository
public interface DeliveryRecordRepository extends JpaRepository<DeliveryRecord, Long> {
    
    /**
     * Busca registros por cliente.
     */
    List<DeliveryRecord> findByCustomerId(Long customerId);
    
    /**
     * Busca registros por pacote.
     */
    List<DeliveryRecord> findByPkgId(Long packageId);
    
    /**
     * Busca registros por status.
     */
    List<DeliveryRecord> findByStatus(DeliveryStatus status);
    
    /**
     * Busca registros por cliente e status.
     */
    List<DeliveryRecord> findByCustomerIdAndStatus(Long customerId, DeliveryStatus status);
    
    /**
     * Busca registro por código de rastreamento.
     */
    Optional<DeliveryRecord> findByTrackingCode(String trackingCode);
    
    /**
     * Busca registros enviados entre duas datas.
     */
    List<DeliveryRecord> findBySentAtBetween(LocalDateTime start, LocalDateTime end);
    
    /**
     * Busca registros pendentes ordenados por data de envio.
     */
    List<DeliveryRecord> findByStatusOrderBySentAtAsc(DeliveryStatus status);
}
