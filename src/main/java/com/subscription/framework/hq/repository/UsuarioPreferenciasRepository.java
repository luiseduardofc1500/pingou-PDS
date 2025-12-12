package com.subscription.framework.hq.repository;

import com.subscription.framework.hq.domain.UsuarioPreferencias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioPreferenciasRepository extends JpaRepository<UsuarioPreferencias, Long> {

    Optional<UsuarioPreferencias> findByUserId(Long userId);

    boolean existsByUserId(Long userId);

    void deleteByUserId(Long userId);
}
