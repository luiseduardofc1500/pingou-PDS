package com.subscription.framework.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Classe abstrata que representa um produto genérico no sistema de assinaturas.
 * 
 * Esta classe serve como base para todos os tipos de produtos que podem ser
 * incluídos nos pacotes de assinatura. Utiliza o padrão de herança JPA com 
 * estratégia JOINED para permitir especialização de produtos mantendo a 
 * integridade relacional.
 * 
 * <p><b>Hotspot de Extensão:</b> Para criar um novo tipo de produto, estenda
 * esta classe e adicione os atributos específicos do seu domínio.</p>
 * 
 * <p>Exemplo de extensão:</p>
 * <pre>
 * {@code
 * @Entity
 * @Table(name = "comics")
 * public class Comic extends Product {
 *     private String publisher;
 *     private Integer issueNumber;
 *     private String series;
 * }
 * }
 * </pre>
 * 
 * @author Subscription Framework
 * @version 1.0
 * @see Plan
 * @see Package
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "products")
@Getter
@Setter
public abstract class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** Nome comercial do produto */
    @Column(nullable = false)
    private String name;
    
    /** Descrição detalhada do produto */
    @Column(length = 2000)
    private String description;
    
    /** Preço unitário do produto */
    @Column(nullable = false)
    private BigDecimal price;
    
    /** URL da imagem do produto */
    @Column(name = "image_url")
    private String imageUrl;
    
    /** SKU (Stock Keeping Unit) - código único do produto */
    @Column(unique = true)
    private String sku;
    
    /** Indica se o produto está ativo no sistema */
    @Column(nullable = false)
    private Boolean active = true;
    
    /** Categoria do produto (pode ser usado para filtros) */
    @Column
    private String category;
    
    public Product() {}
    
    /**
     * Construtor para criação de um produto com informações básicas.
     * 
     * @param name Nome comercial do produto
     * @param description Descrição detalhada do produto
     * @param price Preço unitário do produto
     */
    public Product(String name, String description, BigDecimal price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
    
    /**
     * Construtor completo para criação de um produto.
     * 
     * @param name Nome comercial do produto
     * @param description Descrição detalhada do produto
     * @param price Preço unitário do produto
     * @param sku Código SKU do produto
     * @param category Categoria do produto
     */
    public Product(String name, String description, BigDecimal price, String sku, String category) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.sku = sku;
        this.category = category;
    }
    
    /**
     * Método de validação que pode ser sobrescrito pelas subclasses.
     * 
     * @return true se o produto é válido para ser adicionado a um pacote
     */
    public boolean isValidForPackage() {
        return active && price != null && price.compareTo(BigDecimal.ZERO) > 0;
    }
}
