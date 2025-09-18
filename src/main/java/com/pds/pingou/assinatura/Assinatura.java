package com.pds.pingou.assinatura;

import com.pds.pingou.enums.StatusAssinatura;
import com.pds.pingou.planos.Plano;
import com.pds.pingou.security.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "assinaturas")
public class Assinatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id", nullable = false)
    private Plano plano;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAssinatura status;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_expiracao")
    private LocalDate dataExpiracao;

    public Assinatura() {}

    public Assinatura(User user, Plano plano, StatusAssinatura status, LocalDate dataInicio, LocalDate dataExpiracao) {
        this.user = user;
        this.plano = plano;
        this.status = status;
        this.dataInicio = dataInicio;
        this.dataExpiracao = dataExpiracao;
    }
}