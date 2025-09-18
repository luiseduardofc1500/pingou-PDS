package com.pds.pingou.assinatura;

import com.pds.pingou.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AssinaturaRepository extends JpaRepository<Assinatura, Long> {
    Optional<Assinatura> findByUser(User user);
    boolean existsByUser(User user);
}
