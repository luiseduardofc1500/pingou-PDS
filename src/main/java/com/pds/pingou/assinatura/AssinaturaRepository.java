package com.pds.pingou.assinatura;

import com.pds.pingou.enums.StatusAssinatura;
import com.pds.pingou.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AssinaturaRepository extends JpaRepository<Assinatura, Long> {
    Optional<Assinatura> findByUser(User user);
    boolean existsByUser(User user);

    @Query("SELECT a FROM Assinatura a JOIN FETCH a.user WHERE a.plano.id = :planoId AND a.status = :status")
    List<Assinatura> findByPlanoAtivas(@Param("planoId") Long planoId, @Param("status") StatusAssinatura status);
}
