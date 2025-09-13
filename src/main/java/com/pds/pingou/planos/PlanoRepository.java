package com.pds.pingou.planos;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanoRepository extends JpaRepository<Plano, Long> {
    boolean existsByNome(String nome);
}
