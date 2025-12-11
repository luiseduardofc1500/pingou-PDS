package com.subscription.examples.soccer.controller;

import com.subscription.framework.api.controller.AbstractPlanController;
import com.subscription.framework.core.contract.PlanService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/soccer/plans")
@Tag(name = "Soccer Plans", description = "Planos de assinatura para camisas de futebol")
public class SoccerPlanController extends AbstractPlanController {

    public SoccerPlanController(PlanService planService) {
        super(planService);
    }
}
