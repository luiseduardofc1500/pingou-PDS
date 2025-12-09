package com.subscription.framework.api.controller;

import com.subscription.framework.api.dto.PlanRequestDTO;
import com.subscription.framework.api.dto.PlanResponseDTO;
import com.subscription.framework.core.contract.PlanService;
import com.subscription.framework.core.domain.Plan;
import com.subscription.framework.api.exception.PlanNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller base para gerenciamento de planos.
 * 
 * <p><b>Hotspot de Extensão:</b> Estenda este controller e adicione
 * a anotação @RestController e @RequestMapping apropriados.</p>
 * 
 * @author Subscription Framework
 * @version 1.0
 */
public abstract class AbstractPlanController {
    
    protected final PlanService planService;
    
    protected AbstractPlanController(PlanService planService) {
        this.planService = planService;
    }
    
    @GetMapping
    public ResponseEntity<List<PlanResponseDTO>> findAll() {
        List<PlanResponseDTO> plans = planService.findAllActive().stream()
                .map(PlanResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(plans);
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<PlanResponseDTO>> findAllIncludingInactive() {
        List<PlanResponseDTO> plans = planService.findAll().stream()
                .map(PlanResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(plans);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PlanResponseDTO> findById(@PathVariable Long id) {
        Plan plan = planService.findById(id)
                .orElseThrow(() -> new PlanNotFoundException(id));
        return ResponseEntity.ok(PlanResponseDTO.fromEntity(plan));
    }
    
    @PostMapping
    public ResponseEntity<PlanResponseDTO> create(@RequestBody PlanRequestDTO request) {
        Plan plan = mapRequestToEntity(request);
        Plan created = planService.create(plan);
        return ResponseEntity.ok(PlanResponseDTO.fromEntity(created));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PlanResponseDTO> update(@PathVariable Long id, @RequestBody PlanRequestDTO request) {
        Plan plan = mapRequestToEntity(request);
        Plan updated = planService.update(id, plan);
        return ResponseEntity.ok(PlanResponseDTO.fromEntity(updated));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        planService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        planService.activate(id);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{planId}/features/{featureId}")
    public ResponseEntity<PlanResponseDTO> addFeature(@PathVariable Long planId, @PathVariable Long featureId) {
        Plan updated = planService.addFeature(planId, featureId);
        return ResponseEntity.ok(PlanResponseDTO.fromEntity(updated));
    }
    
    @DeleteMapping("/{planId}/features/{featureId}")
    public ResponseEntity<PlanResponseDTO> removeFeature(@PathVariable Long planId, @PathVariable Long featureId) {
        Plan updated = planService.removeFeature(planId, featureId);
        return ResponseEntity.ok(PlanResponseDTO.fromEntity(updated));
    }
    
    /**
     * Mapeia o DTO de requisição para a entidade Plan.
     */
    protected Plan mapRequestToEntity(PlanRequestDTO request) {
        Plan plan = new Plan();
        plan.setName(request.getName());
        plan.setDescription(request.getDescription());
        plan.setPrice(request.getPrice());
        plan.setMaxItemsPerDelivery(request.getMaxItemsPerDelivery());
        plan.setDeliveryFrequency(request.getDeliveryFrequency());
        plan.setTier(request.getTier());
        plan.setTrialDays(request.getTrialDays());
        plan.setAnnualDiscountPercent(request.getAnnualDiscountPercent());
        return plan;
    }
}
