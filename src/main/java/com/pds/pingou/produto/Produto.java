package com.pds.pingou.produto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Classe abstrata que representa um produto genérico no sistema Pingou.
 * 
 * Esta classe serve como base para todos os tipos de produtos que podem ser
 * incluídos nos pacotes de assinatura, como cachaças, whiskys, vodkas, etc.
 * Utiliza o padrão de herança JPA com estratégia JOINED para permitir
 * especialização de produtos mantendo a integridade relacional.
 * 
 * @author Pingou Team
 * @version 1.0
 * @since 1.0
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "produtos")
@Getter
@Setter
public abstract class Produto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** Nome comercial do produto */
    @Column(nullable = false)
    private String nome;
    
    /** Descrição detalhada do produto */
    @Column(length = 1000)
    private String descricao;
    
    /** Preço do produto em reais */
    @Column(nullable = false)
    private BigDecimal preco;
    
    /** URL da imagem do produto */
    @Column(name = "url_imagem")
    private String urlImagem;
    
    /** Indica se o produto está ativo no sistema */
    @Column(nullable = false)
    private Boolean ativo = true;
    
    public Produto() {}
    
    /**
     * Construtor para criação de um produto com informações básicas.
     * 
     * @param nome Nome comercial do produto
     * @param descricao Descrição detalhada do produto
     * @param preco Preço do produto em reais
     */
    public Produto(String nome, String descricao, BigDecimal preco) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
    }
}