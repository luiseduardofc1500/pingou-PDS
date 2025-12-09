package com.subscription.framework.core.domain;

import com.subscription.framework.core.domain.enums.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidade que representa uma assinatura de um cliente a um plano.
 * 
 * Esta classe é o coração do sistema de assinaturas, conectando um cliente
 * (representado pelo customerId genérico) a um plano específico, com controle
 * de status, datas e ciclo de vida.
 * 
 * <p><b>Integração:</b> O customerId é uma referência genérica que deve ser
 * mapeada para o sistema de usuários da aplicação concreta.</p>
 * 
 * @author Subscription Framework
 * @version 1.0
 * @see Plan
 * @see Package
 */
@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "subscription_type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 
     * ID do cliente no sistema externo.
     * Permite integração com qualquer sistema de usuários.
     */
    @Column(name = "customer_id", nullable = false)
    private Long customerId;
    
    /** Email do cliente (para notificações) */
    @Column(name = "customer_email")
    private String customerEmail;

    /** Plano associado a esta assinatura */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    /** Status atual da assinatura */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatus status;

    /** Data de início da assinatura */
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /** Data de expiração/próxima renovação */
    @Column(name = "expiration_date")
    private LocalDate expirationDate;
    
    /** Data de cancelamento, se aplicável */
    @Column(name = "cancelled_date")
    private LocalDate cancelledDate;
    
    /** Data de criação do registro */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    /** Data da última atualização */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /** ID da transação de pagamento externa */
    @Column(name = "external_payment_id")
    private String externalPaymentId;
    
    /** Indica se está em período de trial */
    @Column(name = "is_trial")
    private Boolean isTrial = false;
    
    /** Data de término do trial */
    @Column(name = "trial_end_date")
    private LocalDate trialEndDate;

    public Subscription() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Construtor para criação de uma nova assinatura.
     * 
     * @param customerId ID do cliente
     * @param plan Plano escolhido
     * @param status Status inicial
     * @param startDate Data de início
     * @param expirationDate Data de expiração
     */
    public Subscription(Long customerId, Plan plan, SubscriptionStatus status, 
                        LocalDate startDate, LocalDate expirationDate) {
        this.customerId = customerId;
        this.plan = plan;
        this.status = status;
        this.startDate = startDate;
        this.expirationDate = expirationDate;
        this.createdAt = LocalDateTime.now();
    }
    
    /**
     * Ativa a assinatura.
     */
    public void activate() {
        this.status = SubscriptionStatus.ACTIVE;
        this.isTrial = false;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Pausa a assinatura.
     */
    public void pause() {
        if (this.status.canPause()) {
            this.status = SubscriptionStatus.PAUSED;
            this.updatedAt = LocalDateTime.now();
        }
    }
    
    /**
     * Cancela a assinatura.
     */
    public void cancel() {
        if (this.status.canCancel()) {
            this.status = SubscriptionStatus.CANCELLED;
            this.cancelledDate = LocalDate.now();
            this.updatedAt = LocalDateTime.now();
        }
    }
    
    /**
     * Renova a assinatura por mais um período.
     */
    public void renew() {
        if (this.status.canRenew()) {
            this.status = SubscriptionStatus.ACTIVE;
            this.startDate = LocalDate.now();
            this.expirationDate = calculateNextExpirationDate();
            this.updatedAt = LocalDateTime.now();
        }
    }
    
    /**
     * Calcula a próxima data de expiração baseado na frequência do plano.
     */
    private LocalDate calculateNextExpirationDate() {
        return LocalDate.now().plusDays(plan.getDeliveryFrequency().getDaysInterval());
    }
    
    /**
     * Verifica se a assinatura está expirada.
     */
    public boolean isExpired() {
        return expirationDate != null && LocalDate.now().isAfter(expirationDate);
    }
    
    /**
     * Verifica se a assinatura está em período de trial.
     */
    public boolean isInTrial() {
        return isTrial && trialEndDate != null && !LocalDate.now().isAfter(trialEndDate);
    }
    
    /**
     * Inicia período de trial.
     * 
     * @param days Número de dias de trial
     */
    public void startTrial(int days) {
        this.isTrial = true;
        this.trialEndDate = LocalDate.now().plusDays(days);
        this.status = SubscriptionStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
