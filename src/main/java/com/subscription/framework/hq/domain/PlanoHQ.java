package com.subscription.framework.hq.domain;

import com.subscription.framework.core.domain.Plan;
import com.subscription.framework.core.domain.enums.DeliveryFrequency;
import com.subscription.framework.core.domain.enums.PlanTier;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Plano especializado para assinatura de HQs
 */
@Entity
@DiscriminatorValue("HQ")
@Getter
@Setter
public class PlanoHQ extends Plan {

    /**
     * Percentual de HQs clássicas no pacote (0-100)
     */
    @Column(name = "percentual_classicas")
    private Integer percentualClassicas;

    /**
     * Percentual de HQs modernas no pacote (0-100)
     */
    @Column(name = "percentual_modernas")
    private Integer percentualModernas;

    /**
     * Indica se inclui edições de colecionador
     */
    @Column(name = "inclui_edicoes_colecionador")
    private Boolean incluiEdicoesColecionador = false;

    /**
     * Multiplicador de pontos para este plano
     */
    @Column(name = "multiplicador_pontos")
    private Double multiplicadorPontos = 1.0;

    /**
     * Filosofia de curadoria do plano
     */
    @Column(name = "filosofia_curadoria", length = 1000)
    private String filosofiaCuradoria;

    /**
     * Indica se é um plano focado em colecionadores
     */
    @Column(name = "plano_colecionador")
    private Boolean planoColecionador = false;

    public PlanoHQ() {
        super();
    }

    public PlanoHQ(String name, String description, BigDecimal price, 
                   Integer maxItemsPerDelivery, DeliveryFrequency deliveryFrequency,
                   Integer percentualClassicas, Integer percentualModernas) {
        super();
        setName(name);
        setDescription(description);
        setPrice(price);
        setMaxItemsPerDelivery(maxItemsPerDelivery);
        setDeliveryFrequency(deliveryFrequency);
        setTier(PlanTier.BASIC);
        this.percentualClassicas = percentualClassicas;
        this.percentualModernas = percentualModernas;
    }

    /**
     * Valida se os percentuais de clássicas e modernas somam 100
     */
    public boolean validarPercentuais() {
        if (percentualClassicas == null || percentualModernas == null) {
            return false;
        }
        return (percentualClassicas + percentualModernas) == 100;
    }

    /**
     * Calcula o valor de pontos considerando o multiplicador do plano
     */
    public int calcularPontosComMultiplicador(int pontosBase) {
        if (multiplicadorPontos == null) {
            return pontosBase;
        }
        return (int) (pontosBase * multiplicadorPontos);
    }
}
