package com.subscription.examples.soccer.dto;

import com.subscription.examples.soccer.domain.SoccerJersey;

import java.math.BigDecimal;

public class SoccerJerseyResponseDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String sku;

    private String teamName;
    private String country;
    private String league;
    private String season;
    private Boolean personalized;
    private String playerName;
    private Integer playerNumber;
    private String size;

    public static SoccerJerseyResponseDTO fromEntity(SoccerJersey jersey) {
        SoccerJerseyResponseDTO dto = new SoccerJerseyResponseDTO();
        dto.id = jersey.getId();
        dto.name = jersey.getName();
        dto.description = jersey.getDescription();
        dto.price = jersey.getPrice();
        dto.sku = jersey.getSku();
        dto.teamName = jersey.getTeamName();
        dto.country = jersey.getCountry();
        dto.league = jersey.getLeague();
        dto.season = jersey.getSeason();
        dto.personalized = jersey.getPersonalized();
        dto.playerName = jersey.getPlayerName();
        dto.playerNumber = jersey.getPlayerNumber();
        dto.size = jersey.getSize();
        return dto;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getSku() {
        return sku;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getCountry() {
        return country;
    }

    public String getLeague() {
        return league;
    }

    public String getSeason() {
        return season;
    }

    public Boolean getPersonalized() {
        return personalized;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Integer getPlayerNumber() {
        return playerNumber;
    }

    public String getSize() {
        return size;
    }
}
