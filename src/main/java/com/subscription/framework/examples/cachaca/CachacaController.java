package com.subscription.framework.examples.cachaca;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller específico para cachaças.
 * 
 * Exemplo de como criar um controller REST para um produto específico
 * usando o framework de assinaturas.
 */
@RestController
@RequestMapping("/api/cachacas")
public class CachacaController {
    
    private final CachacaService cachacaService;
    
    public CachacaController(CachacaService cachacaService) {
        this.cachacaService = cachacaService;
    }
    
    @GetMapping
    public ResponseEntity<List<CachacaResponseDTO>> findAll() {
        return ResponseEntity.ok(cachacaService.findAllActive());
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<CachacaResponseDTO>> findAllIncludingInactive() {
        return ResponseEntity.ok(cachacaService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CachacaResponseDTO> findById(@PathVariable Long id) {
        return cachacaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<CachacaResponseDTO> create(@RequestBody CachacaRequestDTO request) {
        CachacaResponseDTO created = cachacaService.create(request);
        return ResponseEntity.ok(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CachacaResponseDTO> update(
            @PathVariable Long id, 
            @RequestBody CachacaRequestDTO request) {
        CachacaResponseDTO updated = cachacaService.update(id, request);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cachacaService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        cachacaService.activate(id);
        return ResponseEntity.ok().build();
    }
    
    // ========== Endpoints específicos de cachaça ==========
    
    @GetMapping("/region/{region}")
    public ResponseEntity<List<CachacaResponseDTO>> findByRegion(@PathVariable String region) {
        return ResponseEntity.ok(cachacaService.findByRegion(region));
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<CachacaResponseDTO>> findByType(@PathVariable CachacaType type) {
        return ResponseEntity.ok(cachacaService.findByType(type));
    }
    
    @GetMapping("/aging/{agingType}")
    public ResponseEntity<List<CachacaResponseDTO>> findByAgingType(@PathVariable AgingType agingType) {
        return ResponseEntity.ok(cachacaService.findByAgingType(agingType));
    }
    
    @GetMapping("/distillery")
    public ResponseEntity<List<CachacaResponseDTO>> findByDistillery(@RequestParam String name) {
        return ResponseEntity.ok(cachacaService.findByDistillery(name));
    }
    
    @GetMapping("/aged")
    public ResponseEntity<List<CachacaResponseDTO>> findAgedAtLeast(@RequestParam Integer months) {
        return ResponseEntity.ok(cachacaService.findAgedAtLeast(months));
    }
}
