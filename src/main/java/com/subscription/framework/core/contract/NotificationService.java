package com.subscription.framework.core.contract;

/**
 * Contrato para notificações do sistema de assinaturas.
 * 
 * <p><b>Hotspot de Extensão:</b> Implemente esta interface para customizar
 * como as notificações são enviadas (email, SMS, push, etc.)</p>
 * 
 * @author Subscription Framework
 * @version 1.0
 */
public interface NotificationService {
    
    /**
     * Notifica o cliente sobre a criação de uma nova assinatura.
     * 
     * @param customerId ID do cliente
     * @param subscriptionId ID da assinatura
     */
    void notifySubscriptionCreated(Long customerId, Long subscriptionId);
    
    /**
     * Notifica o cliente sobre a ativação da assinatura.
     * 
     * @param customerId ID do cliente
     * @param subscriptionId ID da assinatura
     */
    void notifySubscriptionActivated(Long customerId, Long subscriptionId);
    
    /**
     * Notifica o cliente sobre o cancelamento da assinatura.
     * 
     * @param customerId ID do cliente
     * @param subscriptionId ID da assinatura
     */
    void notifySubscriptionCancelled(Long customerId, Long subscriptionId);
    
    /**
     * Notifica o cliente sobre a expiração próxima da assinatura.
     * 
     * @param customerId ID do cliente
     * @param subscriptionId ID da assinatura
     * @param daysUntilExpiration Dias até a expiração
     */
    void notifyExpirationWarning(Long customerId, Long subscriptionId, int daysUntilExpiration);
    
    /**
     * Notifica o cliente sobre a renovação automática da assinatura.
     * 
     * @param customerId ID do cliente
     * @param subscriptionId ID da assinatura
     */
    void notifySubscriptionRenewed(Long customerId, Long subscriptionId);
    
    /**
     * Notifica o cliente sobre o envio de um pacote.
     * 
     * @param customerId ID do cliente
     * @param packageId ID do pacote
     * @param trackingCode Código de rastreamento
     */
    void notifyPackageShipped(Long customerId, Long packageId, String trackingCode);
    
    /**
     * Notifica o cliente sobre a entrega de um pacote.
     * 
     * @param customerId ID do cliente
     * @param packageId ID do pacote
     */
    void notifyPackageDelivered(Long customerId, Long packageId);
    
    /**
     * Notifica o cliente sobre falha no pagamento.
     * 
     * @param customerId ID do cliente
     * @param subscriptionId ID da assinatura
     * @param reason Motivo da falha
     */
    void notifyPaymentFailed(Long customerId, Long subscriptionId, String reason);
    
    /**
     * Notifica o cliente sobre fim do período de trial.
     * 
     * @param customerId ID do cliente
     * @param subscriptionId ID da assinatura
     * @param daysRemaining Dias restantes do trial
     */
    void notifyTrialEnding(Long customerId, Long subscriptionId, int daysRemaining);
    
    /**
     * Envia uma notificação genérica.
     * 
     * @param customerId ID do cliente
     * @param subject Assunto
     * @param message Mensagem
     * @param type Tipo de notificação
     */
    void sendNotification(Long customerId, String subject, String message, NotificationType type);
    
    /**
     * Tipos de notificação.
     */
    enum NotificationType {
        EMAIL,
        SMS,
        PUSH,
        IN_APP
    }
}
