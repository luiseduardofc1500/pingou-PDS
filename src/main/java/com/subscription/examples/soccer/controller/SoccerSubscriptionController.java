package com.subscription.examples.soccer.controller;

import com.subscription.framework.api.controller.AbstractSubscriptionController;
import com.subscription.framework.core.contract.SubscriptionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/soccer/subscriptions")
@Tag(name = "Soccer Subscriptions", description = "Assinaturas do plano de camisas de futebol")
public class SoccerSubscriptionController extends AbstractSubscriptionController {

    public SoccerSubscriptionController(SubscriptionService subscriptionService) {
        super(subscriptionService);
    }
}
