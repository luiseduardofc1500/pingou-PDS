package com.subscription.framework.core.domain;

import com.subscription.framework.core.domain.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entidade que registra o histórico de entregas de pacotes para assinantes.
 * 
 * Esta classe mantém um registro de todas as entregas realizadas,
 * permitindo rastrear o que foi enviado para cada cliente e quando.
 * 
 * @author Subscription Framework
 * @version 1.0
 * @see Package
 * @see Product
 * @see Subscription
 */
@Entity
@Table(name = "delivery_records")
@Getter
@Setter
public class DeliveryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** ID do cliente que recebeu a entrega */
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    /** Pacote que foi entregue */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id", nullable = false)
    private Package pkg;

    /** Produto específico entregue */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /** Quantidade do produto entregue */
    @Column(nullable = false)
    private Integer quantity;

    /** Data e hora do envio */
    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt;
    
    /** Data e hora da entrega efetiva */
    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;
    
    /** Status da entrega */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status = DeliveryStatus.PENDING;
    
    /** Código de rastreamento */
    @Column(name = "tracking_code")
    private String trackingCode;
    
    /** Observações sobre a entrega */
    @Column(length = 1000)
    private String notes;
    
    /** Endereço de entrega (JSON ou texto formatado) */
    @Column(name = "delivery_address", length = 2000)
    private String deliveryAddress;

    public DeliveryRecord() {}
    
    /**
     * Construtor para criação de um registro de entrega.
     */
    public DeliveryRecord(Long customerId, Package pkg, Product product, Integer quantity) {
        this.customerId = customerId;
        this.pkg = pkg;
        this.product = product;
        this.quantity = quantity;
        this.sentAt = LocalDateTime.now();
        this.status = DeliveryStatus.PENDING;
    }
    
    /**
     * Marca a entrega como enviada.
     * 
     * @param trackingCode Código de rastreamento
     */
    public void markAsShipped(String trackingCode) {
        this.status = DeliveryStatus.SHIPPED;
        this.trackingCode = trackingCode;
    }
    
    /**
     * Marca a entrega como em trânsito.
     */
    public void markAsInTransit() {
        this.status = DeliveryStatus.IN_TRANSIT;
    }
    
    /**
     * Marca a entrega como concluída.
     */
    public void markAsDelivered() {
        this.status = DeliveryStatus.DELIVERED;
        this.deliveredAt = LocalDateTime.now();
    }
    
    /**
     * Marca a entrega como falha.
     * 
     * @param reason Motivo da falha
     */
    public void markAsFailed(String reason) {
        this.status = DeliveryStatus.FAILED;
        this.notes = reason;
    }
}
