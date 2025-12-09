package com.subscription.framework.api.controller;

import com.subscription.framework.api.dto.*;
import com.subscription.framework.core.contract.PackageService;
import com.subscription.framework.core.domain.Package;
import com.subscription.framework.core.domain.Plan;
import com.subscription.framework.core.repository.PlanRepository;
import com.subscription.framework.api.exception.PackageNotFoundException;
import com.subscription.framework.api.exception.PlanNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller base para gerenciamento de pacotes.
 * 
 * <p><b>Hotspot de Extensão:</b> Estenda este controller e adicione
 * a anotação @RestController e @RequestMapping apropriados.</p>
 * 
 * @author Subscription Framework
 * @version 1.0
 */
public abstract class AbstractPackageController {
    
    protected final PackageService packageService;
    protected final PlanRepository planRepository;
    
    protected AbstractPackageController(PackageService packageService, PlanRepository planRepository) {
        this.packageService = packageService;
        this.planRepository = planRepository;
    }
    
    @GetMapping
    public ResponseEntity<List<PackageResponseDTO>> findAll() {
        List<PackageResponseDTO> packages = packageService.findAll().stream()
                .map(PackageResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(packages);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PackageResponseDTO> findById(@PathVariable Long id) {
        Package pkg = packageService.findById(id)
                .orElseThrow(() -> new PackageNotFoundException(id));
        return ResponseEntity.ok(PackageResponseDTO.fromEntity(pkg));
    }
    
    @GetMapping("/plan/{planId}")
    public ResponseEntity<List<PackageResponseDTO>> findByPlan(@PathVariable Long planId) {
        List<PackageResponseDTO> packages = packageService.findByPlanId(planId).stream()
                .map(PackageResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(packages);
    }
    
    @GetMapping("/period")
    public ResponseEntity<List<PackageResponseDTO>> findByPeriod(
            @RequestParam Integer month, @RequestParam Integer year) {
        List<PackageResponseDTO> packages = packageService.findByMonthAndYear(month, year).stream()
                .map(PackageResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(packages);
    }
    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PackageResponseDTO>> findByCustomer(@PathVariable Long customerId) {
        List<PackageResponseDTO> packages = packageService.findByCustomerId(customerId).stream()
                .map(PackageResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(packages);
    }
    
    @PostMapping
    public ResponseEntity<PackageResponseDTO> create(@RequestBody PackageRequestDTO request) {
        Plan plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new PlanNotFoundException(request.getPlanId()));
        
        Package pkg = mapRequestToEntity(request, plan);
        Package created = packageService.create(pkg);
        
        // Adicionar itens se fornecidos
        if (request.getItems() != null && !request.getItems().isEmpty()) {
            for (PackageItemRequestDTO itemRequest : request.getItems()) {
                created = packageService.addItem(
                        created.getId(), 
                        itemRequest.getProductId(), 
                        itemRequest.getQuantity());
            }
        }
        
        return ResponseEntity.ok(PackageResponseDTO.fromEntity(created));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PackageResponseDTO> update(@PathVariable Long id, @RequestBody PackageRequestDTO request) {
        Plan plan = planRepository.findById(request.getPlanId())
                .orElseThrow(() -> new PlanNotFoundException(request.getPlanId()));
        
        Package pkg = mapRequestToEntity(request, plan);
        Package updated = packageService.update(id, pkg);
        return ResponseEntity.ok(PackageResponseDTO.fromEntity(updated));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        packageService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{packageId}/items")
    public ResponseEntity<PackageResponseDTO> addItem(
            @PathVariable Long packageId, 
            @RequestBody PackageItemRequestDTO request) {
        Package updated = packageService.addItem(packageId, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(PackageResponseDTO.fromEntity(updated));
    }
    
    @DeleteMapping("/{packageId}/items/{itemId}")
    public ResponseEntity<PackageResponseDTO> removeItem(
            @PathVariable Long packageId, 
            @PathVariable Long itemId) {
        Package updated = packageService.removeItem(packageId, itemId);
        return ResponseEntity.ok(PackageResponseDTO.fromEntity(updated));
    }
    
    @PutMapping("/{packageId}/items/{itemId}")
    public ResponseEntity<PackageResponseDTO> updateItemQuantity(
            @PathVariable Long packageId, 
            @PathVariable Long itemId,
            @RequestParam Integer quantity) {
        Package updated = packageService.updateItemQuantity(packageId, itemId, quantity);
        return ResponseEntity.ok(PackageResponseDTO.fromEntity(updated));
    }
    
    @GetMapping("/{packageId}/items")
    public ResponseEntity<List<PackageItemResponseDTO>> getItems(@PathVariable Long packageId) {
        List<PackageItemResponseDTO> items = packageService.getItems(packageId).stream()
                .map(PackageItemResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(items);
    }
    
    @PostMapping("/{packageId}/deliver")
    public ResponseEntity<Void> registerDelivery(@PathVariable Long packageId) {
        packageService.registerDeliveryForSubscribers(packageId);
        return ResponseEntity.ok().build();
    }
    
    /**
     * Mapeia o DTO de requisição para a entidade Package.
     */
    protected Package mapRequestToEntity(PackageRequestDTO request, Plan plan) {
        Package pkg = new Package();
        pkg.setName(request.getName());
        pkg.setDescription(request.getDescription());
        pkg.setDeliveryDate(request.getDeliveryDate());
        pkg.setMonth(request.getMonth());
        pkg.setYear(request.getYear());
        pkg.setPlan(plan);
        pkg.setTheme(request.getTheme());
        return pkg;
    }
}
