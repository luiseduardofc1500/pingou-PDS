package com.subscription.examples.soccer.service;

import com.subscription.framework.core.repository.FeatureRepository;
import com.subscription.framework.core.repository.PlanRepository;
import com.subscription.framework.core.repository.SubscriptionRepository;
import com.subscription.framework.core.service.AbstractPlanService;
import org.springframework.stereotype.Service;

@Service
public class SoccerPlanService extends AbstractPlanService {

    public SoccerPlanService(PlanRepository planRepository,
                             FeatureRepository featureRepository,
                             SubscriptionRepository subscriptionRepository) {
        super(planRepository, featureRepository, subscriptionRepository);
    }
}
