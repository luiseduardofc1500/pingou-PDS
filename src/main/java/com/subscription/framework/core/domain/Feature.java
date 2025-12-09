package com.subscription.framework.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Entidade que representa uma funcionalidade (feature) de um plano.
 * 
 * Features são características ou benefícios que podem ser associados
 * a diferentes planos de assinatura. Permitem flexibilidade na composição
 * de planos sem necessidade de criar novas classes.
 * 
 * <p>Exemplos de features:</p>
 * <ul>
 *   <li>Frete grátis</li>
 *   <li>Acesso antecipado a novos produtos</li>
 *   <li>Brindes exclusivos</li>
 *   <li>Suporte prioritário</li>
 *   <li>Descontos em produtos avulsos</li>
 * </ul>
 * 
 * @author Subscription Framework
 * @version 1.0
 * @see Plan
 */
@Entity
@Table(name = "features")
@Getter
@Setter
public class Feature {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** Código único da feature (para uso programático) */
    @Column(nullable = false, unique = true)
    private String code;
    
    /** Nome amigável da feature */
    @Column(nullable = false)
    private String name;
    
    /** Descrição detalhada da feature */
    @Column(length = 1000)
    private String description;
    
    /** Indica se a feature está ativa */
    @Column(nullable = false)
    private Boolean active = true;
    
    /** Ícone ou imagem representativa (URL ou código de ícone) */
    @Column
    private String icon;
    
    public Feature() {}
    
    public Feature(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }
    
    /**
     * Constantes de códigos de features comuns.
     * Podem ser estendidas na implementação concreta.
     */
    public static final String FREE_SHIPPING = "FREE_SHIPPING";
    public static final String EARLY_ACCESS = "EARLY_ACCESS";
    public static final String EXCLUSIVE_GIFTS = "EXCLUSIVE_GIFTS";
    public static final String PRIORITY_SUPPORT = "PRIORITY_SUPPORT";
    public static final String ADDITIONAL_DISCOUNT = "ADDITIONAL_DISCOUNT";
}
