package com.subscription.framework.examples.jersey;

import java.math.BigDecimal;

/**
 * DTO para requisições de criação/atualização de camisas de futebol.
 */
public record SoccerJerseyRequestDTO(
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
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
     * Converte o DTO para entidade SoccerJersey.
     */
    public SoccerJersey toEntity() {
        SoccerJersey jersey = new SoccerJersey();
        jersey.setName(name);
        jersey.setDescription(description);
        jersey.setPrice(price);
        jersey.setImageUrl(imageUrl);
        jersey.setActive(true);
        jersey.setTeam(team);
        jersey.setSeason(season);
        jersey.setJerseyType(jerseyType);
        jersey.setSize(size);
        jersey.setPlayerName(playerName);
        jersey.setPlayerNumber(playerNumber);
        jersey.setAuthentic(isAuthentic);
        jersey.setManufacturer(manufacturer);
        return jersey;
    }
}
