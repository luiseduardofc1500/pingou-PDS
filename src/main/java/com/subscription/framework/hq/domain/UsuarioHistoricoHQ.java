package com.subscription.framework.hq.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entidade que registra o histórico de HQs recebidas por um usuário.
 * Usado para evitar duplicatas na curadoria.
 */
@Entity
@Table(name = "usuario_historico_hq", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "quadrinho_id"}))
@Getter
@Setter
public class UsuarioHistoricoHQ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID do usuário
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * Quadrinho recebido
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quadrinho_id", nullable = false)
    private Quadrinho quadrinho;

    /**
     * ID do pacote em que o quadrinho foi enviado
     */
    @Column(name = "package_id")
    private Long packageId;

    /**
     * Data em que o quadrinho foi enviado
     */
    @Column(name = "data_envio", nullable = false)
    private LocalDateTime dataEnvio;

    /**
     * Indica se o usuário marcou o quadrinho como lido
     */
    @Column(name = "lido", nullable = false)
    private Boolean lido = false;

    /**
     * Avaliação do usuário (1-5 estrelas)
     */
    @Column(name = "avaliacao")
    private Integer avaliacao;

    /**
     * Comentário do usuário sobre o quadrinho
     */
    @Column(name = "comentario", length = 1000)
    private String comentario;

    public UsuarioHistoricoHQ() {
        this.dataEnvio = LocalDateTime.now();
    }

    public UsuarioHistoricoHQ(Long userId, Quadrinho quadrinho, Long packageId) {
        this.userId = userId;
        this.quadrinho = quadrinho;
        this.packageId = packageId;
        this.dataEnvio = LocalDateTime.now();
    }

    /**
     * Marca o quadrinho como lido
     */
    public void marcarComoLido() {
        this.lido = true;
    }

    /**
     * Adiciona avaliação do usuário
     */
    public void avaliar(Integer nota, String comentario) {
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("Avaliação deve estar entre 1 e 5");
        }
        this.avaliacao = nota;
        this.comentario = comentario;
    }
}
