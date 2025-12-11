package com.subscription.examples.soccer.service;

import com.subscription.examples.soccer.service.SoccerCustomerProfileService;
import com.subscription.framework.core.domain.Plan;
import com.subscription.framework.core.domain.Subscription;
import com.subscription.framework.core.repository.PlanRepository;
import com.subscription.framework.core.repository.SubscriptionRepository;
import com.subscription.framework.core.service.AbstractSubscriptionService;
import org.springframework.stereotype.Service;

@Service
public class SoccerSubscriptionService extends AbstractSubscriptionService {

    private final SoccerCustomerProfileService profileService;

    public SoccerSubscriptionService(SubscriptionRepository subscriptionRepository,
                                     PlanRepository planRepository,
                                     SoccerCustomerProfileService profileService) {
        super(subscriptionRepository, planRepository);
        this.profileService = profileService;
    }

    @Override
    protected void validateCustomer(Long customerId) {
        // Garante que o perfil do cliente existe; se não existir, lança exceção
        try {
            profileService.getProfile(customerId);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Cliente " + customerId + " não possui perfil de medidas cadastrado." );
        }
    }

    @Override
    protected void beforeCreateSubscription(Long customerId, Plan plan) {
        // Poderia integrar com billing/IA/notificações aqui
    }

    @Override
    protected void onSubscriptionExpired(Subscription subscription) {
        // Poderia notificar o cliente sobre expiração
    }

    @Override
    protected void onTrialExpired(Subscription subscription) {
        // Poderia notificar o cliente sobre fim do trial
    }
}
