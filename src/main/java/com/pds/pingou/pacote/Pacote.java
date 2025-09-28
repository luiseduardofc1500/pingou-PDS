package com.pds.pingou.pacote;

import com.pds.pingou.planos.Plano;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um pacote mensal de produtos no sistema Pingou.
 * 
 * Esta classe define um conjunto de produtos que será entregue aos assinantes
 * em um determinado mês/ano. Cada pacote está vinculado a um plano específico
 * e contém uma lista de itens (produtos) que serão enviados na data programada.
 * É fundamental para a logística de entregas mensais do sistema de assinatura.
 * 
 * @author Pingou Team
 * @version 1.0
 * @since 1.0
 */
@Entity
@Table(name = "pacotes")
@Getter
@Setter
public class Pacote {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /** Nome identificador do pacote */
    @Column(nullable = false)
    private String nome;
    
    /** Descrição detalhada do pacote */
    @Column(length = 1000)
    private String descricao;
    
    /** Data programada para entrega do pacote */
    @Column(name = "data_entrega", nullable = false)
    private LocalDate dataEntrega;
    
    /** Mês de referência do pacote (1-12) */
    @Column(nullable = false)
    private Integer mes;
    
    /** Ano de referência do pacote */
    @Column(nullable = false)
    private Integer ano;
    
    /** Plano ao qual este pacote pertence */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plano_id", nullable = false)
    private Plano plano;
    
    /** Lista de produtos incluídos neste pacote */
    @OneToMany(mappedBy = "pacote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemPacote> itens = new ArrayList<>();
    
    /** Indica se o pacote está ativo no sistema */
    @Column(nullable = false)
    private Boolean ativo = true;
    
    public Pacote() {}
    
    /**
     * Construtor para criação de um pacote com informações essenciais.
     * 
     * @param nome Nome identificador do pacote
     * @param descricao Descrição detalhada do pacote
     * @param dataEntrega Data programada para entrega
     * @param mes Mês de referência (1-12)
     * @param ano Ano de referência
     * @param plano Plano ao qual este pacote pertence
     */
    public Pacote(String nome, String descricao, LocalDate dataEntrega, 
                  Integer mes, Integer ano, Plano plano) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataEntrega = dataEntrega;
        this.mes = mes;
        this.ano = ano;
        this.plano = plano;
    }
    
    /**
     * Adiciona um item (produto) ao pacote.
     * 
     * @param item Item a ser adicionado ao pacote
     */
    public void adicionarItem(ItemPacote item) {
        itens.add(item);
        item.setPacote(this);
    }
    
    /**
     * Remove um item (produto) do pacote.
     * 
     * @param item Item a ser removido do pacote
     */
    public void removerItem(ItemPacote item) {
        itens.remove(item);
        item.setPacote(null);
    }
}