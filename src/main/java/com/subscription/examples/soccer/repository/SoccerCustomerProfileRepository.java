package com.subscription.examples.soccer.repository;

import com.subscription.examples.soccer.domain.SoccerCustomerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoccerCustomerProfileRepository extends JpaRepository<SoccerCustomerProfile, Long> {
}
