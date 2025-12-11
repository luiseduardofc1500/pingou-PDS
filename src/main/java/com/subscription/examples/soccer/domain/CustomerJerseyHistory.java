package com.subscription.examples.soccer.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "soccer_customer_jersey_history")
public class CustomerJerseyHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Column(name = "jersey_count", nullable = false)
    private Integer jerseyCount = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getJerseyCount() {
        return jerseyCount;
    }

    public void setJerseyCount(Integer jerseyCount) {
        this.jerseyCount = jerseyCount;
    }

    public void increment() {
        this.jerseyCount = (this.jerseyCount == null ? 0 : this.jerseyCount) + 1;
    }
}
