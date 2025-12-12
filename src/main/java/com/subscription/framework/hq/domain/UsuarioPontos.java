package com.subscription.framework.hq.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entidade que gerencia os pontos de gamificação do usuário.
 */
@Entity
@Table(name = "usuario_pontos")
@Getter
@Setter
public class UsuarioPontos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID do usuário
     */
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    /**
     * Total de pontos acumulados
     */
    @Column(name = "pontos_totais", nullable = false)
    private Integer pontosTotais = 0;

    /**
     * Pontos disponíveis para uso
     */
    @Column(name = "pontos_disponiveis", nullable = false)
    private Integer pontosDisponiveis = 0;

    /**
     * Pontos já utilizados/resgatados
     */
    @Column(name = "pontos_utilizados", nullable = false)
    private Integer pontosUtilizados = 0;

    /**
     * Nível do usuário baseado em pontos
     */
    @Column(name = "nivel", nullable = false)
    private Integer nivel = 1;

    /**
     * Data de criação
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Data da última atualização
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constantes para progressão de nível
    private static final int PONTOS_POR_NIVEL = 1000;

    public UsuarioPontos() {
        this.createdAt = LocalDateTime.now();
    }

    public UsuarioPontos(Long userId) {
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Adiciona pontos ao usuário
     */
    public void adicionarPontos(int pontos) {
        if (pontos <= 0) {
            throw new IllegalArgumentException("Pontos devem ser positivos");
        }
        
        this.pontosTotais += pontos;
        this.pontosDisponiveis += pontos;
        this.updatedAt = LocalDateTime.now();
        
        // Atualiza o nível se necessário
        atualizarNivel();
    }

    /**
     * Utiliza pontos do usuário
     */
    public void utilizarPontos(int pontos) {
        if (pontos <= 0) {
            throw new IllegalArgumentException("Pontos devem ser positivos");
        }
        
        if (pontos > this.pontosDisponiveis) {
            throw new IllegalStateException("Pontos insuficientes");
        }
        
        this.pontosDisponiveis -= pontos;
        this.pontosUtilizados += pontos;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Atualiza o nível do usuário baseado nos pontos totais
     */
    private void atualizarNivel() {
        int novoNivel = (this.pontosTotais / PONTOS_POR_NIVEL) + 1;
        if (novoNivel > this.nivel) {
            this.nivel = novoNivel;
        }
    }

    /**
     * Retorna pontos necessários para o próximo nível
     */
    public int pontosParaProximoNivel() {
        int pontosProximoNivel = this.nivel * PONTOS_POR_NIVEL;
        return pontosProximoNivel - this.pontosTotais;
    }

    /**
     * Verifica se o usuário tem pontos suficientes
     */
    public boolean temPontosSuficientes(int pontos) {
        return this.pontosDisponiveis >= pontos;
    }
}
