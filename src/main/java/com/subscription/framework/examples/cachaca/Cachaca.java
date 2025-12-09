package com.subscription.framework.examples.cachaca;

import com.subscription.framework.core.domain.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Exemplo de implementação: Produto Cachaça.
 * 
 * Esta classe demonstra como estender o framework de assinaturas
 * para criar um produto específico do domínio de cachaças.
 * 
 * @author Subscription Framework
 * @version 1.0
 */
@Entity
@Table(name = "cachacas")
@Getter
@Setter
public class Cachaca extends Product {

    /** Região brasileira de origem da cachaça */
    @Column(nullable = false)
    private String region;
    
    /** Teor alcoólico da cachaça (ex: 38.5%) */
    @Column(name = "alcohol_content", nullable = false)
    private BigDecimal alcoholContent;
    
    /** Volume da garrafa em mililitros */
    @Column(nullable = false)
    private Integer volume;
    
    /** Tipo da cachaça */
    @Enumerated(EnumType.STRING)
    @Column(name = "cachaca_type", nullable = false)
    private CachacaType cachacaType;
    
    /** Tipo de envelhecimento */
    @Enumerated(EnumType.STRING)
    @Column(name = "aging_type")
    private AgingType agingType;
    
    /** Tempo de envelhecimento em meses */
    @Column(name = "aging_months")
    private Integer agingMonths;
    
    /** Nome do alambique/destilaria */
    @Column
    private String distillery;
    
    /** Ano de produção */
    @Column(name = "production_year")
    private Integer productionYear;
    
    public Cachaca() {
        super();
    }
    
    public Cachaca(String name, String description, BigDecimal price, 
                   String region, BigDecimal alcoholContent, Integer volume, 
                   CachacaType cachacaType) {
        super(name, description, price);
        this.region = region;
        this.alcoholContent = alcoholContent;
        this.volume = volume;
        this.cachacaType = cachacaType;
        this.setCategory("CACHACA");
    }
    
    /**
     * Validação específica para cachaça.
     */
    @Override
    public boolean isValidForPackage() {
        return super.isValidForPackage() 
                && alcoholContent != null 
                && alcoholContent.compareTo(BigDecimal.ZERO) > 0
                && volume != null 
                && volume > 0;
    }
}
