package com.subscription.examples.soccer.repository;

import com.subscription.examples.soccer.domain.CustomerJerseyHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerJerseyHistoryRepository extends JpaRepository<CustomerJerseyHistory, Long> {

    List<CustomerJerseyHistory> findByCustomerId(Long customerId);

    Optional<CustomerJerseyHistory> findByCustomerIdAndTeamName(Long customerId, String teamName);
}
