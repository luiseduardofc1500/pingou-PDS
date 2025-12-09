package com.subscription.framework.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um pacote de produtos a ser entregue.
 * 
 * Esta classe define um conjunto de produtos que será entregue aos assinantes
 * em um determinado período. Cada pacote está vinculado a um plano específico
 * e contém uma lista de itens (produtos) que serão enviados na data programada.
 * 
 * @author Subscription Framework
 * @version 1.0
 * @see Plan
 * @see PackageItem
 */
@Entity
@Table(name = "packages")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "package_type", discriminatorType = DiscriminatorType.STRING)
@Getter
@Setter
public class Package {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** Nome identificador do pacote */
    @Column(nullable = false)
    private String name;
    
    /** Descrição detalhada do pacote */
    @Column(length = 2000)
    private String description;
    
    /** Data programada para entrega do pacote */
    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate;
    
    /** Mês de referência do pacote (1-12) */
    @Column(nullable = false)
    private Integer month;
    
    /** Ano de referência do pacote */
    @Column(nullable = false)
    private Integer year;
    
    /** Plano ao qual este pacote pertence */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;
    
    /** Lista de itens (produtos) incluídos neste pacote */
    @OneToMany(mappedBy = "pkg", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PackageItem> items = new ArrayList<>();
    
    /** Indica se o pacote está ativo no sistema */
    @Column(nullable = false)
    private Boolean active = true;
    
    /** Valor total calculado do pacote */
    @Column(name = "total_value")
    private BigDecimal totalValue;
    
    /** Tema ou título especial do pacote (ex: "Edição de Natal") */
    @Column
    private String theme;
    
    public Package() {}
    
    /**
     * Construtor para criação de um pacote com informações essenciais.
     * 
     * @param name Nome identificador do pacote
     * @param description Descrição detalhada do pacote
     * @param deliveryDate Data programada para entrega
     * @param month Mês de referência (1-12)
     * @param year Ano de referência
     * @param plan Plano ao qual este pacote pertence
     */
    public Package(String name, String description, LocalDate deliveryDate, 
                   Integer month, Integer year, Plan plan) {
        this.name = name;
        this.description = description;
        this.deliveryDate = deliveryDate;
        this.month = month;
        this.year = year;
        this.plan = plan;
    }
    
    /**
     * Adiciona um item (produto) ao pacote.
     * 
     * @param item Item a ser adicionado ao pacote
     */
    public void addItem(PackageItem item) {
        items.add(item);
        item.setPkg(this);
        recalculateTotalValue();
    }
    
    /**
     * Remove um item (produto) do pacote.
     * 
     * @param item Item a ser removido do pacote
     */
    public void removeItem(PackageItem item) {
        items.remove(item);
        item.setPkg(null);
        recalculateTotalValue();
    }
    
    /**
     * Calcula o número total de itens no pacote.
     * 
     * @return Soma das quantidades de todos os itens
     */
    public int getTotalItemCount() {
        return items.stream()
                .mapToInt(PackageItem::getQuantity)
                .sum();
    }
    
    /**
     * Verifica se o pacote pode receber mais itens baseado no limite do plano.
     * 
     * @param additionalQuantity Quantidade a ser adicionada
     * @return true se pode adicionar, false caso contrário
     */
    public boolean canAddItems(int additionalQuantity) {
        return getTotalItemCount() + additionalQuantity <= plan.getMaxItemsPerDelivery();
    }
    
    /**
     * Recalcula o valor total do pacote baseado nos itens.
     */
    public void recalculateTotalValue() {
        this.totalValue = items.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    /**
     * Retorna o período formatado (MM/YYYY).
     */
    public String getFormattedPeriod() {
        return String.format("%02d/%d", month, year);
    }
}
