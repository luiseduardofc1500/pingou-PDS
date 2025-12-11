package com.subscription.examples.soccer.controller;

import com.subscription.framework.api.controller.AbstractPackageController;
import com.subscription.framework.core.contract.PackageService;
import com.subscription.framework.core.repository.PlanRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/soccer/packages")
@Tag(name = "Soccer Packages", description = "Pacotes de entrega de camisas de futebol")
public class SoccerPackageController extends AbstractPackageController {

    public SoccerPackageController(PackageService packageService, PlanRepository planRepository) {
        super(packageService, planRepository);
    }
}
