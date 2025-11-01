package com.pds.pingou.pacote.historico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoricoEnvioRepository extends JpaRepository<HistoricoEnvio, Long> {
    @Query("SELECT h FROM HistoricoEnvio h JOIN FETCH h.pacote p JOIN FETCH h.produto pr WHERE h.user.id = :userId ORDER BY h.dataEnvio DESC")
    List<HistoricoEnvio> findByUserId(@Param("userId") Long userId);

    @Query("SELECT h FROM HistoricoEnvio h JOIN FETCH h.pacote p JOIN FETCH h.produto pr JOIN FETCH h.user u ORDER BY h.dataEnvio DESC")
    List<HistoricoEnvio> findAllWithJoins();
}


