package com.subscription.framework.examples.jersey;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para camisas de futebol.
 * Demonstra como expor endpoints específicos do domínio.
 */
@RestController
@RequestMapping("/api/v1/jerseys")
public class SoccerJerseyController {
    
    private final SoccerJerseyService soccerJerseyService;
    
    public SoccerJerseyController(SoccerJerseyService soccerJerseyService) {
        this.soccerJerseyService = soccerJerseyService;
    }
    
    // === CRUD básico ===
    
    @GetMapping
    public ResponseEntity<List<SoccerJerseyResponseDTO>> findAll() {
        return ResponseEntity.ok(soccerJerseyService.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SoccerJerseyResponseDTO> findById(@PathVariable Long id) {
        return soccerJerseyService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<SoccerJerseyResponseDTO> create(@RequestBody SoccerJerseyRequestDTO request) {
        SoccerJerseyResponseDTO created = soccerJerseyService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<SoccerJerseyResponseDTO> update(
            @PathVariable Long id,
            @RequestBody SoccerJerseyRequestDTO request) {
        SoccerJerseyResponseDTO updated = soccerJerseyService.update(id, request);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        soccerJerseyService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
    
    // === Endpoints específicos do domínio ===
    
    @GetMapping("/active")
    public ResponseEntity<List<SoccerJerseyResponseDTO>> findActive() {
        return ResponseEntity.ok(soccerJerseyService.findAllActive());
    }
    
    @GetMapping("/team/{team}")
    public ResponseEntity<List<SoccerJerseyResponseDTO>> findByTeam(@PathVariable String team) {
        List<SoccerJerseyResponseDTO> jerseys = soccerJerseyService.findByTeam(team)
                .stream()
                .map(SoccerJerseyResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(jerseys);
    }
    
    @GetMapping("/season/{season}")
    public ResponseEntity<List<SoccerJerseyResponseDTO>> findBySeason(@PathVariable String season) {
        List<SoccerJerseyResponseDTO> jerseys = soccerJerseyService.findBySeason(season)
                .stream()
                .map(SoccerJerseyResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(jerseys);
    }
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<SoccerJerseyResponseDTO>> findByType(@PathVariable JerseyType type) {
        List<SoccerJerseyResponseDTO> jerseys = soccerJerseyService.findByType(type)
                .stream()
                .map(SoccerJerseyResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(jerseys);
    }
    
    @GetMapping("/size/{size}")
    public ResponseEntity<List<SoccerJerseyResponseDTO>> findBySize(@PathVariable JerseySize size) {
        List<SoccerJerseyResponseDTO> jerseys = soccerJerseyService.findBySize(size)
                .stream()
                .map(SoccerJerseyResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(jerseys);
    }
    
    @GetMapping("/manufacturer/{manufacturer}")
    public ResponseEntity<List<SoccerJerseyResponseDTO>> findByManufacturer(
            @PathVariable String manufacturer) {
        List<SoccerJerseyResponseDTO> jerseys = soccerJerseyService.findByManufacturer(manufacturer)
                .stream()
                .map(SoccerJerseyResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(jerseys);
    }
    
    @GetMapping("/authentic")
    public ResponseEntity<List<SoccerJerseyResponseDTO>> findAuthentic() {
        List<SoccerJerseyResponseDTO> jerseys = soccerJerseyService.findAuthenticOnly()
                .stream()
                .map(SoccerJerseyResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(jerseys);
    }
    
    @GetMapping("/replicas")
    public ResponseEntity<List<SoccerJerseyResponseDTO>> findReplicas() {
        List<SoccerJerseyResponseDTO> jerseys = soccerJerseyService.findReplicasOnly()
                .stream()
                .map(SoccerJerseyResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(jerseys);
    }
    
    @GetMapping("/player/{playerName}")
    public ResponseEntity<List<SoccerJerseyResponseDTO>> findByPlayerName(
            @PathVariable String playerName) {
        List<SoccerJerseyResponseDTO> jerseys = soccerJerseyService.findByPlayerName(playerName)
                .stream()
                .map(SoccerJerseyResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(jerseys);
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<SoccerJerseyResponseDTO>> search(
            @RequestParam(required = false) String team,
            @RequestParam(required = false) String season,
            @RequestParam(required = false) JerseyType type,
            @RequestParam(required = false) JerseySize size,
            @RequestParam(required = false) Boolean authentic) {
        
        List<SoccerJersey> jerseys;
        
        // Combinação de filtros - pode ser expandido conforme necessidade
        if (team != null && type != null && size != null) {
            jerseys = soccerJerseyService.findByTeamTypeAndSize(team, type, size);
        } else if (team != null && season != null) {
            jerseys = soccerJerseyService.findByTeamAndSeason(team, season);
        } else if (team != null && authentic != null && authentic) {
            jerseys = soccerJerseyService.findAuthenticByTeam(team);
        } else if (team != null) {
            jerseys = soccerJerseyService.findByTeam(team);
        } else if (season != null) {
            jerseys = soccerJerseyService.findBySeason(season);
        } else if (type != null) {
            jerseys = soccerJerseyService.findByType(type);
        } else if (size != null) {
            jerseys = soccerJerseyService.findBySize(size);
        } else if (authentic != null) {
            jerseys = authentic ? soccerJerseyService.findAuthenticOnly() 
                               : soccerJerseyService.findReplicasOnly();
        } else {
            return ResponseEntity.ok(soccerJerseyService.findAllActive());
        }
        
        return ResponseEntity.ok(jerseys.stream()
                .map(SoccerJerseyResponseDTO::fromEntity)
                .toList());
    }
    
    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        soccerJerseyService.activate(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        soccerJerseyService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
