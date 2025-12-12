package com.subscription.framework.hq.domain;

import com.subscription.framework.core.domain.Product;
import com.subscription.framework.hq.domain.enums.CategoriaHQ;
import com.subscription.framework.hq.domain.enums.Editora;
import com.subscription.framework.hq.domain.enums.TipoHQ;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidade que representa um Quadrinho (HQ) no sistema de assinatura.
 * Especialização da entidade Product do framework.
 */
@Entity
@DiscriminatorValue("QUADRINHO")
@Getter
@Setter
public class Quadrinho extends Product {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Editora editora;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_hq", nullable = false)
    private TipoHQ tipoHQ;

    /**
     * Pontos que o cliente ganha ao receber este quadrinho em sua assinatura
     */
    @Column(name = "pontos_ganho", nullable = false)
    private Integer pontosGanho;

    /**
     * Indica se é uma edição especial para colecionadores
     */
    @Column(name = "edicao_colecionador", nullable = false)
    private Boolean edicaoColecionador = false;

    /**
     * Número da edição
     */
    @Column(name = "numero_edicao")
    private String numeroEdicao;

    /**
     * Autor(es) do quadrinho
     */
    @Column(name = "autor", length = 500)
    private String autor;

    /**
     * Ilustrador(es) do quadrinho
     */
    @Column(name = "ilustrador", length = 500)
    private String ilustrador;

    /**
     * Data de publicação original
     */
    @Column(name = "data_publicacao")
    private LocalDate dataPublicacao;

    /**
     * ISBN do quadrinho
     */
    @Column(name = "isbn", unique = true)
    private String isbn;

    /**
     * Número de páginas
     */
    @Column(name = "numero_paginas")
    private Integer numeroPaginas;

    /**
     * Categorias do quadrinho (pode ter múltiplas)
     */
    @ElementCollection(targetClass = CategoriaHQ.class)
    @CollectionTable(name = "quadrinho_categorias", joinColumns = @JoinColumn(name = "quadrinho_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private Set<CategoriaHQ> categorias = new HashSet<>();

    /**
     * Série a qual o quadrinho pertence
     */
    @Column(name = "serie", length = 300)
    private String serie;

    /**
     * Quantidade em estoque
     */
    @Column(name = "estoque", nullable = false)
    private Integer estoque = 0;

    /**
     * Multiplicador de pontos para edições colecionador
     */
    private static final double MULTIPLICADOR_COLECIONADOR = 1.5;

    public Quadrinho() {
        super();
    }

    public Quadrinho(String name, String description, BigDecimal price, Editora editora, 
                     TipoHQ tipoHQ, Boolean edicaoColecionador) {
        super(name, description, price);
        this.editora = editora;
        this.tipoHQ = tipoHQ;
        this.edicaoColecionador = edicaoColecionador;
        this.pontosGanho = calcularPontos();
    }

    /**
     * Calcula os pontos baseado no tipo de HQ e se é edição colecionador
     */
    public int calcularPontos() {
        int pontosBase = tipoHQ.getPontosBase();
        
        if (edicaoColecionador) {
            pontosBase = (int) (pontosBase * MULTIPLICADOR_COLECIONADOR);
        }
        
        return pontosBase;
    }

    /**
     * Atualiza os pontos quando há mudança no tipo ou status de colecionador
     */
    @PrePersist
    @PreUpdate
    public void atualizarPontos() {
        if (this.pontosGanho == null || this.pontosGanho == 0) {
            this.pontosGanho = calcularPontos();
        }
    }

    /**
     * Adiciona uma categoria ao quadrinho
     */
    public void adicionarCategoria(CategoriaHQ categoria) {
        this.categorias.add(categoria);
    }

    /**
     * Remove uma categoria do quadrinho
     */
    public void removerCategoria(CategoriaHQ categoria) {
        this.categorias.remove(categoria);
    }

    /**
     * Verifica se o quadrinho tem uma categoria específica
     */
    public boolean temCategoria(CategoriaHQ categoria) {
        return this.categorias.contains(categoria);
    }

    /**
     * Verifica se há estoque disponível
     */
    public boolean temEstoqueDisponivel() {
        return this.estoque != null && this.estoque > 0;
    }

    /**
     * Reduz o estoque em uma unidade
     */
    public void reduzirEstoque() {
        if (temEstoqueDisponivel()) {
            this.estoque--;
        }
    }

    /**
     * Adiciona quantidade ao estoque
     */
    public void adicionarEstoque(int quantidade) {
        if (this.estoque == null) {
            this.estoque = quantidade;
        } else {
            this.estoque += quantidade;
        }
    }
}
