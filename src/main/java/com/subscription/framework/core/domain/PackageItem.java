package com.subscription.framework.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Entidade que representa um item específico dentro de um pacote.
 * 
 * Esta classe faz a ligação entre um pacote e os produtos que o compõem,
 * permitindo especificar a quantidade de cada produto e observações
 * particulares. É uma entidade de relacionamento many-to-many entre
 * Package e Product, com atributos adicionais.
 * 
 * @author Subscription Framework
 * @version 1.0
 * @see Package
 * @see Product
 */
@Entity
@Table(name = "package_items")
@Getter
@Setter
public class PackageItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** Pacote ao qual este item pertence */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id", nullable = false)
    private Package pkg;
    
    /** Produto incluído neste item */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    /** Quantidade do produto no pacote */
    @Column(nullable = false)
    private Integer quantity = 1;
    
    /** Observações especiais sobre o item */
    @Column(length = 500)
    private String notes;
    
    /** Preço unitário no momento da inclusão (para histórico) */
    @Column(name = "unit_price_at_time")
    private BigDecimal unitPriceAtTime;
    
    public PackageItem() {}
    
    /**
     * Construtor para criação de um item de pacote.
     * 
     * @param pkg Pacote ao qual este item pertence
     * @param product Produto incluído neste item
     * @param quantity Quantidade do produto no pacote
     */
    public PackageItem(Package pkg, Product product, Integer quantity) {
        this.pkg = pkg;
        this.product = product;
        this.quantity = quantity;
        this.unitPriceAtTime = product.getPrice();
    }
    
    /**
     * Calcula o subtotal deste item.
     * 
     * @return Preço unitário * quantidade
     */
    public BigDecimal getSubtotal() {
        BigDecimal price = unitPriceAtTime != null ? unitPriceAtTime : product.getPrice();
        return price.multiply(BigDecimal.valueOf(quantity));
    }
    
    /**
     * Atualiza a quantidade do item.
     * 
     * @param newQuantity Nova quantidade
     * @throws IllegalArgumentException se a quantidade for inválida
     */
    public void updateQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        this.quantity = newQuantity;
    }
}
