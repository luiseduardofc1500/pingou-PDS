package com.pds.pingou.planos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "planos")
public class Plano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private BigDecimal preco;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlanoTipo planoTipo;

    public Plano() {
    }

    public Plano(String nome, String descricao, BigDecimal preco, PlanoTipo planoTipo) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.planoTipo = planoTipo;
    }

}
