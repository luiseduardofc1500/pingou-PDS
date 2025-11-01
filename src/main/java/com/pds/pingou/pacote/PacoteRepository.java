package com.pds.pingou.pacote;

import com.pds.pingou.planos.Plano;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PacoteRepository extends JpaRepository<Pacote, Long> {
    List<Pacote> findByPlano(Plano plano);
    
    List<Pacote> findByMesAndAno(Integer mes, Integer ano);
    
    @Query("SELECT p FROM Pacote p WHERE p.dataEntrega BETWEEN :inicio AND :fim")
    List<Pacote> findByDataEntregaBetween(
        @Param("inicio") LocalDate inicio, 
        @Param("fim") LocalDate fim
    );
    
    @Query("SELECT p FROM Pacote p WHERE p.plano.id = :planoId AND p.mes = :mes AND p.ano = :ano")
    List<Pacote> findByPlanoIdAndMesAndAno(
        @Param("planoId") Long planoId, 
        @Param("mes") Integer mes, 
        @Param("ano") Integer ano
    );
}