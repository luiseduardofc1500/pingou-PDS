package com.subscription.framework.hq.repository;

import com.subscription.framework.hq.domain.PacoteHQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacoteHQRepository extends JpaRepository<PacoteHQ, Long> {

    List<PacoteHQ> findByUserId(Long userId);

    List<PacoteHQ> findByUserIdOrderByDeliveryDateDesc(Long userId);

    @Query("SELECT p FROM PacoteHQ p WHERE p.userId = :userId AND p.month = :month AND p.year = :year")
    List<PacoteHQ> findByUserIdAndPeriodo(
        @Param("userId") Long userId, 
        @Param("month") Integer month, 
        @Param("year") Integer year
    );

    @Query("SELECT p FROM PacoteHQ p WHERE p.userId = :userId AND p.active = true ORDER BY p.deliveryDate DESC")
    List<PacoteHQ> findPacotesAtivosPorUsuario(@Param("userId") Long userId);
}
