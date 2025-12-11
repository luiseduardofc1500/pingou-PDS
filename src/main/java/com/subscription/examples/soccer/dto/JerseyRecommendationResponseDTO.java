package com.subscription.examples.soccer.dto;

import com.subscription.examples.soccer.domain.SoccerJersey;

public class JerseyRecommendationResponseDTO {

    private Long customerId;
    private Long jerseyId;
    private String teamName;
    private String league;
    private String season;
    private String size;

    public static JerseyRecommendationResponseDTO of(Long customerId, SoccerJersey jersey) {
        JerseyRecommendationResponseDTO dto = new JerseyRecommendationResponseDTO();
        dto.customerId = customerId;
        dto.jerseyId = jersey.getId();
        dto.teamName = jersey.getTeamName();
        dto.league = jersey.getLeague();
        dto.season = jersey.getSeason();
        dto.size = jersey.getSize();
        return dto;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Long getJerseyId() {
        return jerseyId;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getLeague() {
        return league;
    }

    public String getSeason() {
        return season;
    }

    public String getSize() {
        return size;
    }
}
