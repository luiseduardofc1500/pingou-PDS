package com.subscription.framework.examples.jersey;

import java.math.BigDecimal;

/**
 * DTO para respostas de camisas de futebol.
 */
public record SoccerJerseyResponseDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
        boolean active,
        String team,
        String season,
        JerseyType jerseyType,
        JerseySize size,
        String playerName,
        Integer playerNumber,
        boolean isAuthentic,
        String manufacturer
) {
    
    /**
     * Cria um DTO a partir de uma entidade SoccerJersey.
     */
    public static SoccerJerseyResponseDTO fromEntity(SoccerJersey jersey) {
        return new SoccerJerseyResponseDTO(
                jersey.getId(),
                jersey.getName(),
                jersey.getDescription(),
                jersey.getPrice(),
                jersey.getImageUrl(),
                jersey.getActive() != null && jersey.getActive(),
                jersey.getTeam(),
                jersey.getSeason(),
                jersey.getJerseyType(),
                jersey.getSize(),
                jersey.getPlayerName(),
                jersey.getPlayerNumber(),
                jersey.getAuthentic() != null && jersey.getAuthentic(),
                jersey.getManufacturer()
        );
    }
}
