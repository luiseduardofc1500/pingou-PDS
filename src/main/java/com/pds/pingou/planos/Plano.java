package com.pds.pingou.planos;

import com.pds.pingou.pacote.Pacote;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade que representa um plano de assinatura no sistema Pingou.
 * 
 * Esta classe define os diferentes tipos de planos de assinatura oferecidos,
 * cada um com suas características específicas como preço, quantidade máxima
 * de produtos por mês e frequência de entrega. Os planos são associados a
 * pacotes mensais que definem exatamente quais produtos serão enviados.
 * 
 * @author Pingou Team
 * @version 2.0
 * @since 1.0
 */
@Setter
@Getter
@Entity
@Table(name = "planos")
public class Plano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nome comercial do plano */
    @Column(nullable = false, unique = true)
    private String nome;

    /** Descrição detalhada do plano e seus benefícios */
    @Column(nullable = false, length = 2000)
    private String descricao;

    /** Preço mensal do plano em reais */
    @Column(nullable = false)
    private BigDecimal preco;
    
    /** Quantidade máxima de produtos que podem ser enviados por mês */
    @Column(name = "max_produtos_por_mes", nullable = false)
    private Integer maxProdutosPorMes;
    
    /** Frequência das entregas (MENSAL, BIMESTRAL, etc.) */
    @Column(name = "frequencia_entrega", nullable = false)
    private String frequenciaEntrega = "MENSAL";
    
    /** Indica se o plano está ativo e disponível para assinatura */
    @Column(nullable = false)
    private Boolean ativo = true;
    
    /** Lista de pacotes mensais associados a este plano */
    @OneToMany(mappedBy = "plano", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Pacote> pacotes = new ArrayList<>();

    public Plano() {
    }

    /**
     * Construtor para criação de um plano com informações essenciais.
     * 
     * @param nome Nome comercial do plano
     * @param descricao Descrição detalhada do plano
     * @param preco Preço mensal em reais
     * @param maxProdutosPorMes Quantidade máxima de produtos por mês
     */
    public Plano(String nome, String descricao, BigDecimal preco, Integer maxProdutosPorMes) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.maxProdutosPorMes = maxProdutosPorMes;
    }
    
    /**
     * Adiciona um pacote mensal a este plano.
     * 
     * @param pacote Pacote a ser adicionado
     */
    public void adicionarPacote(Pacote pacote) {
        pacotes.add(pacote);
        pacote.setPlano(this);
    }
    
    /**
     * Remove um pacote mensal deste plano.
     * 
     * @param pacote Pacote a ser removido
     */
    public void removerPacote(Pacote pacote) {
        pacotes.remove(pacote);
        pacote.setPlano(null);
    }
}
