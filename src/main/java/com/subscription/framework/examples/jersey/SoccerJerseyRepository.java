package com.subscription.framework.examples.jersey;

import com.subscription.framework.core.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório específico para camisas de futebol.
 * Herda operações básicas do ProductRepository e adiciona queries específicas.
 */
@Repository
public interface SoccerJerseyRepository extends ProductRepository<SoccerJersey> {
    
    /**
     * Busca camisas por time.
     */
    List<SoccerJersey> findByTeamContainingIgnoreCase(String team);
    
    /**
     * Busca camisas por temporada.
     */
    List<SoccerJersey> findBySeason(String season);
    
    /**
     * Busca camisas por tipo (home, away, etc.).
     */
    List<SoccerJersey> findByJerseyType(JerseyType jerseyType);
    
    /**
     * Busca camisas por tamanho.
     */
    List<SoccerJersey> findBySize(JerseySize size);
    
    /**
     * Busca camisas por fabricante.
     */
    List<SoccerJersey> findByManufacturerContainingIgnoreCase(String manufacturer);
    
    /**
     * Busca camisas autênticas ou réplicas.
     */
    List<SoccerJersey> findByAuthentic(boolean authentic);
    
    /**
     * Busca camisas de um jogador específico.
     */
    List<SoccerJersey> findByPlayerNameContainingIgnoreCase(String playerName);
    
    /**
     * Busca camisas por time e temporada.
     */
    List<SoccerJersey> findByTeamContainingIgnoreCaseAndSeason(String team, String season);
    
    /**
     * Busca camisas por time, tipo e tamanho.
     */
    List<SoccerJersey> findByTeamContainingIgnoreCaseAndJerseyTypeAndSize(
            String team, JerseyType jerseyType, JerseySize size);
    
    /**
     * Busca camisas autênticas de um time específico.
     */
    List<SoccerJersey> findByTeamContainingIgnoreCaseAndAuthentic(String team, boolean authentic);
}
