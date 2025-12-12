package com.subscription.framework.hq.repository;

import com.subscription.framework.hq.domain.PlanoHQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanoHQRepository extends JpaRepository<PlanoHQ, Long> {

    List<PlanoHQ> findByPlanoColecionador(Boolean planoColecionador);

    List<PlanoHQ> findByIncluiEdicoesColecionador(Boolean inclui);

    @Query("SELECT p FROM PlanoHQ p WHERE p.active = true ORDER BY p.price ASC")
    List<PlanoHQ> findAllAtivosOrdenadosPorPreco();

    @Query("SELECT p FROM PlanoHQ p WHERE p.percentualClassicas >= :minClassicas AND p.active = true")
    List<PlanoHQ> findByPercentualClassicasMinimo(Integer minClassicas);
}
