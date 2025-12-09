package com.subscription.framework.core.contract;

import com.subscription.framework.core.domain.Subscription;

import java.math.BigDecimal;

/**
 * Contrato para integração com sistemas de billing/pagamento.
 * 
 * <p><b>Hotspot de Extensão:</b> Implemente esta interface para integrar
 * com seu gateway de pagamento preferido (Stripe, PayPal, PagSeguro, etc.)</p>
 * 
 * @author Subscription Framework
 * @version 1.0
 */
public interface BillingService {
    
    /**
     * Processa o pagamento de uma assinatura.
     * 
     * @param subscription Assinatura a ser cobrada
     * @return ID da transação no sistema de pagamento
     */
    String processPayment(Subscription subscription);
    
    /**
     * Processa o pagamento de um valor específico.
     * 
     * @param customerId ID do cliente
     * @param amount Valor a ser cobrado
     * @param description Descrição da cobrança
     * @return ID da transação no sistema de pagamento
     */
    String processPayment(Long customerId, BigDecimal amount, String description);
    
    /**
     * Cancela uma cobrança recorrente.
     * 
     * @param externalPaymentId ID da transação no sistema de pagamento
     * @return true se cancelado com sucesso
     */
    boolean cancelRecurringPayment(String externalPaymentId);
    
    /**
     * Reembolsa um pagamento.
     * 
     * @param externalPaymentId ID da transação no sistema de pagamento
     * @param amount Valor a ser reembolsado (null para reembolso total)
     * @return ID da transação de reembolso
     */
    String refund(String externalPaymentId, BigDecimal amount);
    
    /**
     * Verifica o status de um pagamento.
     * 
     * @param externalPaymentId ID da transação no sistema de pagamento
     * @return Status do pagamento
     */
    PaymentStatus getPaymentStatus(String externalPaymentId);
    
    /**
     * Cria uma sessão de checkout para o cliente.
     * 
     * @param customerId ID do cliente
     * @param planId ID do plano
     * @param successUrl URL de redirecionamento em caso de sucesso
     * @param cancelUrl URL de redirecionamento em caso de cancelamento
     * @return URL da sessão de checkout
     */
    String createCheckoutSession(Long customerId, Long planId, String successUrl, String cancelUrl);
    
    /**
     * Enum para status de pagamento.
     */
    enum PaymentStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED,
        REFUNDED,
        CANCELLED
    }
}
