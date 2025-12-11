package com.subscription.examples.soccer.domain;

import com.subscription.framework.core.domain.Product;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("SOCCER_JERSEY")
public class SoccerJersey extends Product {

    @Column(nullable = false)
    private String teamName;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String league;

    @Column(nullable = false)
    private String season;

    @Column(nullable = false)
    private Boolean personalized = false;

    @Column
    private String playerName;

    @Column
    private Integer playerNumber;

    @Column(nullable = false)
    private String size; // Usa valores de JerseySize, mas armazenado como String para simplicidade

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public Boolean getPersonalized() {
        return personalized;
    }

    public void setPersonalized(Boolean personalized) {
        this.personalized = personalized;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(Integer playerNumber) {
        this.playerNumber = playerNumber;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
