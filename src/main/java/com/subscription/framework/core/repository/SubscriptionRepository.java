package com.subscription.framework.core.repository;

import com.subscription.framework.core.domain.Subscription;
import com.subscription.framework.core.domain.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    
    /**
     * Busca assinaturas por cliente.
     */
    List<Subscription> findByCustomerId(Long customerId);
    
    /**
     * Busca assinatura ativa de um cliente.
     */
    Optional<Subscription> findByCustomerIdAndStatus(Long customerId, SubscriptionStatus status);
    
    /**
     * Verifica se o cliente tem assinatura com determinado status.
     */
    boolean existsByCustomerIdAndStatus(Long customerId, SubscriptionStatus status);
    
    /**
     * Verifica se o cliente tem qualquer assinatura.
     */
    boolean existsByCustomerId(Long customerId);
    
    /**
     * Busca assinaturas por status.
     */
    List<Subscription> findByStatus(SubscriptionStatus status);
    
    /**
     * Busca assinaturas ativas de um plano específico.
     */
    @Query("SELECT s FROM Subscription s WHERE s.plan.id = :planId AND s.status = :status")
    List<Subscription> findByPlanIdAndStatus(@Param("planId") Long planId, @Param("status") SubscriptionStatus status);
    
    /**
     * Busca assinaturas que expiram em uma data específica.
     */
    List<Subscription> findByExpirationDateAndStatus(LocalDate expirationDate, SubscriptionStatus status);
    
    /**
     * Busca assinaturas que expiram entre duas datas.
     */
    List<Subscription> findByExpirationDateBetweenAndStatus(
        LocalDate startDate, LocalDate endDate, SubscriptionStatus status);
    
    /**
     * Busca assinaturas em trial que expiram em uma data específica.
     */
    List<Subscription> findByIsTrialTrueAndTrialEndDate(LocalDate trialEndDate);
    
    /**
     * Conta assinaturas ativas por plano.
     */
    @Query("SELECT COUNT(s) FROM Subscription s WHERE s.plan.id = :planId AND s.status = 'ACTIVE'")
    long countActiveByPlanId(@Param("planId") Long planId);
}
