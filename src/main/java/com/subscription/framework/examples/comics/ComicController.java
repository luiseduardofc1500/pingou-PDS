package com.subscription.framework.examples.comics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller específico para HQs.
 * 
 * Exemplo de como criar um controller REST para histórias em quadrinhos
 * usando o framework de assinaturas.
 */
@RestController
@RequestMapping("/api/comics")
public class ComicController {
    
    private final ComicService comicService;
    
    public ComicController(ComicService comicService) {
        this.comicService = comicService;
    }
    
    @GetMapping
    public ResponseEntity<List<ComicResponseDTO>> findAll() {
        return ResponseEntity.ok(comicService.findAllActive());
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<ComicResponseDTO>> findAllIncludingInactive() {
        return ResponseEntity.ok(comicService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ComicResponseDTO> findById(@PathVariable Long id) {
        return comicService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<ComicResponseDTO> create(@RequestBody ComicRequestDTO request) {
        ComicResponseDTO created = comicService.create(request);
        return ResponseEntity.ok(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ComicResponseDTO> update(
            @PathVariable Long id, 
            @RequestBody ComicRequestDTO request) {
        ComicResponseDTO updated = comicService.update(id, request);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        comicService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
    
    // ========== Endpoints específicos de HQ ==========
    
    @GetMapping("/publisher/{publisher}")
    public ResponseEntity<List<ComicResponseDTO>> findByPublisher(@PathVariable String publisher) {
        return ResponseEntity.ok(comicService.findByPublisher(publisher));
    }
    
    @GetMapping("/series")
    public ResponseEntity<List<ComicResponseDTO>> findBySeries(@RequestParam String name) {
        return ResponseEntity.ok(comicService.findBySeries(name));
    }
    
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<ComicResponseDTO>> findByGenre(@PathVariable ComicGenre genre) {
        return ResponseEntity.ok(comicService.findByGenre(genre));
    }
    
    @GetMapping("/format/{format}")
    public ResponseEntity<List<ComicResponseDTO>> findByFormat(@PathVariable ComicFormat format) {
        return ResponseEntity.ok(comicService.findByFormat(format));
    }
    
    @GetMapping("/writer")
    public ResponseEntity<List<ComicResponseDTO>> findByWriter(@RequestParam String name) {
        return ResponseEntity.ok(comicService.findByWriter(name));
    }
    
    @GetMapping("/artist")
    public ResponseEntity<List<ComicResponseDTO>> findByArtist(@RequestParam String name) {
        return ResponseEntity.ok(comicService.findByArtist(name));
    }
    
    @GetMapping("/special-editions")
    public ResponseEntity<List<ComicResponseDTO>> findSpecialEditions() {
        return ResponseEntity.ok(comicService.findSpecialEditions());
    }
}
