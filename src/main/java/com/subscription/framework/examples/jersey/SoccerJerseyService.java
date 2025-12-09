package com.subscription.framework.examples.jersey;

import com.subscription.framework.core.service.AbstractProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Serviço para gerenciamento de camisas de futebol.
 * Demonstra como estender o AbstractProductService para um domínio específico.
 */
@Service
public class SoccerJerseyService extends AbstractProductService<SoccerJersey, SoccerJerseyResponseDTO, SoccerJerseyRequestDTO> {
    
    private final SoccerJerseyRepository soccerJerseyRepository;
    
    public SoccerJerseyService(SoccerJerseyRepository soccerJerseyRepository) {
        super(soccerJerseyRepository);
        this.soccerJerseyRepository = soccerJerseyRepository;
    }
    
    // === Implementação dos métodos abstratos ===
    
    @Override
    protected SoccerJerseyResponseDTO toDTO(SoccerJersey entity) {
        return SoccerJerseyResponseDTO.fromEntity(entity);
    }
    
    @Override
    protected SoccerJersey toEntity(SoccerJerseyRequestDTO dto) {
        return dto.toEntity();
    }
    
    @Override
    protected void updateEntity(SoccerJersey entity, SoccerJerseyRequestDTO dto) {
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setPrice(dto.price());
        entity.setImageUrl(dto.imageUrl());
        entity.setTeam(dto.team());
        entity.setSeason(dto.season());
        entity.setJerseyType(dto.jerseyType());
        entity.setSize(dto.size());
        entity.setPlayerName(dto.playerName());
        entity.setPlayerNumber(dto.playerNumber());
        entity.setAuthentic(dto.isAuthentic());
        entity.setManufacturer(dto.manufacturer());
    }
    
    // === Métodos específicos do domínio ===
    
    /**
     * Busca camisas por time.
     */
    public List<SoccerJersey> findByTeam(String team) {
        return soccerJerseyRepository.findByTeamContainingIgnoreCase(team);
    }
    
    /**
     * Busca camisas por temporada.
     */
    public List<SoccerJersey> findBySeason(String season) {
        return soccerJerseyRepository.findBySeason(season);
    }
    
    /**
     * Busca camisas por tipo (home, away, etc.).
     */
    public List<SoccerJersey> findByType(JerseyType jerseyType) {
        return soccerJerseyRepository.findByJerseyType(jerseyType);
    }
    
    /**
     * Busca camisas por tamanho.
     */
    public List<SoccerJersey> findBySize(JerseySize size) {
        return soccerJerseyRepository.findBySize(size);
    }
    
    /**
     * Busca camisas por fabricante.
     */
    public List<SoccerJersey> findByManufacturer(String manufacturer) {
        return soccerJerseyRepository.findByManufacturerContainingIgnoreCase(manufacturer);
    }
    
    /**
     * Busca apenas camisas autênticas.
     */
    public List<SoccerJersey> findAuthenticOnly() {
        return soccerJerseyRepository.findByAuthentic(true);
    }
    
    /**
     * Busca apenas réplicas.
     */
    public List<SoccerJersey> findReplicasOnly() {
        return soccerJerseyRepository.findByAuthentic(false);
    }
    
    /**
     * Busca camisas de um jogador específico.
     */
    public List<SoccerJersey> findByPlayerName(String playerName) {
        return soccerJerseyRepository.findByPlayerNameContainingIgnoreCase(playerName);
    }
    
    /**
     * Busca camisas por time e temporada.
     */
    public List<SoccerJersey> findByTeamAndSeason(String team, String season) {
        return soccerJerseyRepository.findByTeamContainingIgnoreCaseAndSeason(team, season);
    }
    
    /**
     * Busca camisas com filtros combinados.
     */
    public List<SoccerJersey> findByTeamTypeAndSize(String team, JerseyType jerseyType, JerseySize size) {
        return soccerJerseyRepository.findByTeamContainingIgnoreCaseAndJerseyTypeAndSize(
                team, jerseyType, size);
    }
    
    /**
     * Busca camisas autênticas de um time.
     */
    public List<SoccerJersey> findAuthenticByTeam(String team) {
        return soccerJerseyRepository.findByTeamContainingIgnoreCaseAndAuthentic(team, true);
    }
    
    // === Implementação dos hooks do AbstractProductService ===
    
    @Override
    protected void validateRequest(SoccerJerseyRequestDTO request) {
        if (request.team() == null || request.team().isBlank()) {
            throw new IllegalArgumentException("Team is required for soccer jersey");
        }
        if (request.season() == null || request.season().isBlank()) {
            throw new IllegalArgumentException("Season is required for soccer jersey");
        }
        if (request.jerseyType() == null) {
            throw new IllegalArgumentException("Jersey type is required");
        }
        if (request.size() == null) {
            throw new IllegalArgumentException("Size is required for soccer jersey");
        }
        if (request.price() == null || request.price().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
    }
    
    @Override
    protected void beforeCreate(SoccerJerseyRequestDTO request) {
        // Hook para ações pré-criação
    }
    
    @Override
    protected void afterCreate(SoccerJersey jersey) {
        // Hook para ações pós-criação
        // Ex: notificar sistema de estoque, atualizar catálogo, etc.
        
        // Gera nome automático se não fornecido
        if (jersey.getName() == null || jersey.getName().isBlank()) {
            StringBuilder nameBuilder = new StringBuilder();
            nameBuilder.append(jersey.getTeam())
                    .append(" ")
                    .append(jersey.getJerseyType().getDisplayName())
                    .append(" ")
                    .append(jersey.getSeason());
            
            if (jersey.getPlayerName() != null && !jersey.getPlayerName().isBlank()) {
                nameBuilder.append(" - ").append(jersey.getPlayerName());
                if (jersey.getPlayerNumber() != null) {
                    nameBuilder.append(" #").append(jersey.getPlayerNumber());
                }
            }
            
            jersey.setName(nameBuilder.toString());
        }
        
        // Define metadados
        jersey.getMetadata().put("category", "soccer-jersey");
        jersey.getMetadata().put("jerseyType", jersey.getJerseyType().name());
        jersey.getMetadata().put("size", jersey.getSize().getCode());
        
        if (jersey.getAuthentic() != null && jersey.getAuthentic()) {
            jersey.getMetadata().put("authenticity", "authentic");
        } else {
            jersey.getMetadata().put("authenticity", "replica");
        }
    }
}
