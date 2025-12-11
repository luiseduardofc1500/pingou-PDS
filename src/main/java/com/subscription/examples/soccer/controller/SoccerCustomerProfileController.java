package com.subscription.examples.soccer.controller;

import com.subscription.examples.soccer.dto.CustomerProfileRequestDTO;
import com.subscription.examples.soccer.dto.CustomerProfileResponseDTO;
import com.subscription.examples.soccer.service.SoccerCustomerProfileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/soccer/customers/profile")
@Tag(name = "Soccer Customer Profile", description = "Perfil de medidas dos clientes de camisas")
public class SoccerCustomerProfileController {

    private final SoccerCustomerProfileService profileService;

    public SoccerCustomerProfileController(SoccerCustomerProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<CustomerProfileResponseDTO> createOrUpdate(@RequestBody CustomerProfileRequestDTO request) {
        var profile = profileService.createOrUpdate(request);
        return ResponseEntity.ok(CustomerProfileResponseDTO.fromEntity(profile));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerProfileResponseDTO> getProfile(@PathVariable Long customerId) {
        var profile = profileService.getProfile(customerId);
        return ResponseEntity.ok(CustomerProfileResponseDTO.fromEntity(profile));
    }
}
