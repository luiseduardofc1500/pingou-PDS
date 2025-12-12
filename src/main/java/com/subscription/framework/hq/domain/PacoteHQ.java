package com.subscription.framework.hq.domain;

import com.subscription.framework.core.domain.Package;
import com.subscription.framework.core.domain.Plan;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Pacote especializado para HQs com curadoria personalizada
 */
@Entity
@DiscriminatorValue("HQ")
@Getter
@Setter
public class PacoteHQ extends Package {

    /**
     * ID do usuário para quem o pacote foi curado
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * Total de pontos que o usuário ganhará com este pacote
     */
    @Column(name = "total_pontos")
    private Integer totalPontos;

    /**
     * Quantidade de HQs clássicas no pacote
     */
    @Column(name = "quantidade_classicas")
    private Integer quantidadeClassicas;

    /**
     * Quantidade de HQs modernas no pacote
     */
    @Column(name = "quantidade_modernas")
    private Integer quantidadeModernas;

    /**
     * Indica se o pacote foi curado automaticamente
     */
    @Column(name = "curadoria_automatica")
    private Boolean curadoriaAutomatica = true;

    /**
     * Nota de curadoria (relevância baseada nas preferências)
     */
    @Column(name = "nota_curadoria")
    private Double notaCuradoria;

    public PacoteHQ() {
        super();
    }

    public PacoteHQ(String name, String description, LocalDate deliveryDate,
                    Integer month, Integer year, Plan plan, Long userId) {
        super(name, description, deliveryDate, month, year, plan);
        this.userId = userId;
    }

    /**
     * Calcula o total de pontos baseado nas HQs do pacote
     */
    public void calcularTotalPontos() {
        this.totalPontos = getItems().stream()
                .filter(item -> item.getProduct() instanceof Quadrinho)
                .mapToInt(item -> {
                    Quadrinho hq = (Quadrinho) item.getProduct();
                    return hq.getPontosGanho() * item.getQuantity();
                })
                .sum();
    }

    /**
     * Atualiza as contagens de clássicas e modernas
     */
    public void atualizarContagens() {
        quantidadeClassicas = (int) getItems().stream()
                .filter(item -> item.getProduct() instanceof Quadrinho)
                .filter(item -> ((Quadrinho) item.getProduct()).getTipoHQ().name().equals("CLASSICA"))
                .count();

        quantidadeModernas = (int) getItems().stream()
                .filter(item -> item.getProduct() instanceof Quadrinho)
                .filter(item -> ((Quadrinho) item.getProduct()).getTipoHQ().name().equals("MODERNA"))
                .count();
    }
}
