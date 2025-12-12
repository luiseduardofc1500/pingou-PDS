package com.subscription.framework.hq.repository;

import com.subscription.framework.hq.domain.UsuarioPontos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioPontosRepository extends JpaRepository<UsuarioPontos, Long> {

    Optional<UsuarioPontos> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    @Query("SELECT u FROM UsuarioPontos u ORDER BY u.pontosTotais DESC")
    List<UsuarioPontos> findTopUsuariosByPontos();

    @Query("SELECT u FROM UsuarioPontos u WHERE u.nivel >= :nivelMinimo ORDER BY u.pontosTotais DESC")
    List<UsuarioPontos> findUsuariosPorNivelMinimo(@Param("nivelMinimo") Integer nivelMinimo);
}
