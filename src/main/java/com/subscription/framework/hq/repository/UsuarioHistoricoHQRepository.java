package com.subscription.framework.hq.repository;

import com.subscription.framework.hq.domain.UsuarioHistoricoHQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioHistoricoHQRepository extends JpaRepository<UsuarioHistoricoHQ, Long> {

    List<UsuarioHistoricoHQ> findByUserId(Long userId);

    Optional<UsuarioHistoricoHQ> findByUserIdAndQuadrinhoId(Long userId, Long quadrinhoId);

    boolean existsByUserIdAndQuadrinhoId(Long userId, Long quadrinhoId);

    @Query("SELECT h.quadrinho.id FROM UsuarioHistoricoHQ h WHERE h.userId = :userId")
    List<Long> findQuadrinhoIdsRecebidosPorUsuario(@Param("userId") Long userId);

    @Query("SELECT COUNT(h) FROM UsuarioHistoricoHQ h WHERE h.userId = :userId")
    Long countByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(h) FROM UsuarioHistoricoHQ h WHERE h.userId = :userId AND h.lido = true")
    Long countLidosByUserId(@Param("userId") Long userId);

    List<UsuarioHistoricoHQ> findByUserIdAndLido(Long userId, Boolean lido);

    @Query("SELECT h FROM UsuarioHistoricoHQ h WHERE h.userId = :userId AND h.avaliacao IS NOT NULL ORDER BY h.dataEnvio DESC")
    List<UsuarioHistoricoHQ> findAvaliacoesPorUsuario(@Param("userId") Long userId);
}
