package com.subscription.examples.soccer.service;

import com.subscription.examples.soccer.domain.SoccerJersey;
import com.subscription.examples.soccer.repository.SoccerJerseyRepository;
import com.subscription.framework.api.exception.ProductNotFoundException;
import com.subscription.framework.core.domain.Product;
import com.subscription.framework.core.repository.*;
import com.subscription.framework.core.service.AbstractPackageService;
import org.springframework.stereotype.Service;

@Service
public class SoccerPackageService extends AbstractPackageService {

    private final SoccerJerseyRepository jerseyRepository;

    public SoccerPackageService(PackageRepository packageRepository,
                                PackageItemRepository packageItemRepository,
                                PlanRepository planRepository,
                                SubscriptionRepository subscriptionRepository,
                                DeliveryRecordRepository deliveryRecordRepository,
                                SoccerJerseyRepository jerseyRepository) {
        super(packageRepository, packageItemRepository, planRepository, subscriptionRepository, deliveryRecordRepository);
        this.jerseyRepository = jerseyRepository;
    }

    @Override
    protected Product findProductOrThrow(Long productId) {
        SoccerJersey jersey = jerseyRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        return jersey;
    }
}
