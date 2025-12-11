package com.subscription.examples.soccer.controller;

import com.subscription.examples.soccer.dto.SoccerJerseyRequestDTO;
import com.subscription.examples.soccer.dto.SoccerJerseyResponseDTO;
import com.subscription.examples.soccer.dto.JerseyRecommendationResponseDTO;
import com.subscription.examples.soccer.service.SoccerJerseyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/soccer/jerseys")
@Tag(name = "Soccer Jerseys", description = "Catálogo de camisas de futebol")
public class SoccerJerseyController {

    private final SoccerJerseyService jerseyService;

    public SoccerJerseyController(SoccerJerseyService jerseyService) {
        this.jerseyService = jerseyService;
    }

    @GetMapping
    public ResponseEntity<List<SoccerJerseyResponseDTO>> findAll() {
        return ResponseEntity.ok(jerseyService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SoccerJerseyResponseDTO> findById(@PathVariable Long id) {
        return jerseyService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SoccerJerseyResponseDTO> create(@RequestBody SoccerJerseyRequestDTO request) {
        return ResponseEntity.ok(jerseyService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SoccerJerseyResponseDTO> update(@PathVariable Long id,
                                                          @RequestBody SoccerJerseyRequestDTO request) {
        return ResponseEntity.ok(jerseyService.update(id, request));
    }

    @PostMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        jerseyService.deactivate(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        jerseyService.activate(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/size/{size}")
    public ResponseEntity<List<SoccerJerseyResponseDTO>> findBySize(@PathVariable String size) {
        return ResponseEntity.ok(jerseyService.findAllBySize(size));
    }

    @GetMapping("/recommendation/{customerId}")
    public ResponseEntity<JerseyRecommendationResponseDTO> recommend(@PathVariable Long customerId) {
        var jersey = jerseyService.recommendJerseyForCustomer(customerId);
        return ResponseEntity.ok(JerseyRecommendationResponseDTO.of(customerId, jersey));
    }
}
