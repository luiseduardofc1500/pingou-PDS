package com.subscription.examples.soccer.repository;

import com.subscription.examples.soccer.domain.SoccerJersey;
import com.subscription.framework.core.repository.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface SoccerJerseyRepository extends ProductRepository<SoccerJersey> {

    List<SoccerJersey> findBySizeAndActiveTrue(String size);

    List<SoccerJersey> findBySizeAndTeamNameNotInAndActiveTrue(String size, Collection<String> teamNames);
}
