package com.subscription.framework.examples.jersey;

import com.subscription.framework.core.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Exemplo de implementação: Produto Camisa de Futebol.
 * 
 * Esta classe demonstra como estender o framework de assinaturas
 * para criar um produto específico do domínio de camisas de futebol.
 * 
 * @author Subscription Framework
 * @version 1.0
 */
@Entity
@Table(name = "soccer_jerseys")
@Getter
@Setter
public class SoccerJersey extends Product {

    /** Nome do time */
    @Column(nullable = false)
    private String team;
    
    /** Liga/Campeonato do time */
    @Column
    private String league;
    
    /** País do time */
    @Column(nullable = false)
    private String country;
    
    /** Temporada da camisa (ex: 2024/2025) */
    @Column(nullable = false)
    private String season;
    
    /** Tipo da camisa */
    @Enumerated(EnumType.STRING)
    @Column(name = "jersey_type", nullable = false)
    private JerseyType jerseyType;
    
    /** Tamanho da camisa */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JerseySize size;
    
    /** Fabricante/Marca */
    @Column(nullable = false)
    private String manufacturer;
    
    /** Nome do jogador (se personalizada) */
    @Column(name = "player_name")
    private String playerName;
    
    /** Número do jogador (se personalizada) */
    @Column(name = "player_number")
    private Integer playerNumber;
    
    /** É versão de jogador (Player Version) */
    @Column(name = "is_player_version")
    private Boolean isPlayerVersion = false;
    
    /** Material da camisa */
    @Column
    private String material;
    
    /** É edição limitada */
    @Column(name = "is_limited_edition")
    private Boolean isLimitedEdition = false;
    
/** É camisa retrô */
    @Column(name = "is_retro")
    private Boolean isRetro = false;
    
    /** É camisa autêntica (não réplica) */
    @Column(name = "is_authentic")
    private Boolean authentic = false;
    
    /** Metadados extras da camisa */
    @ElementCollection
    @CollectionTable(name = "soccer_jersey_metadata", joinColumns = @JoinColumn(name = "jersey_id"))
    @MapKeyColumn(name = "meta_key")
    @Column(name = "meta_value")
    private Map<String, String> metadata = new HashMap<>();

    public SoccerJersey() {
        super();
    }    public SoccerJersey(String name, String description, BigDecimal price,
                        String team, String country, String season,
                        JerseyType jerseyType, JerseySize size, String manufacturer) {
        super(name, description, price);
        this.team = team;
        this.country = country;
        this.season = season;
        this.jerseyType = jerseyType;
        this.size = size;
        this.manufacturer = manufacturer;
        this.setCategory("SOCCER_JERSEY");
    }
    
    /**
     * Retorna a descrição completa da camisa.
     */
    public String getFullDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(team).append(" ").append(jerseyType.getDisplayName());
        sb.append(" ").append(season);
        
        if (playerName != null && !playerName.isBlank()) {
            sb.append(" - ").append(playerName);
            if (playerNumber != null) {
                sb.append(" #").append(playerNumber);
            }
        }
        
        sb.append(" (").append(size.getDisplayName()).append(")");
        
        return sb.toString();
    }
    
    @Override
    public boolean isValidForPackage() {
        return super.isValidForPackage() 
                && team != null && !team.isBlank()
                && size != null;
    }
}
