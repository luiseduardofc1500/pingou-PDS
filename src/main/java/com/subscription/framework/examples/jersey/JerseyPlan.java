package com.subscription.framework.examples.jersey;

import com.subscription.framework.core.domain.Plan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Plano específico para assinatura de camisas de futebol.
 * Estende Plan com características próprias do domínio esportivo.
 */
@Entity
@Table(name = "jersey_plans")
@DiscriminatorValue("JERSEY")
@Getter
@Setter
@NoArgsConstructor
public class JerseyPlan extends Plan {
    
    /**
     * Indica se o plano inclui apenas camisas autênticas (não réplicas)
     */
    @Column(name = "authentic_only")
    private Boolean authenticOnly = false;
    
    /**
     * Indica se o plano inclui camisas retrô/vintage
     */
    @Column(name = "includes_retro")
    private Boolean includesRetro = false;
    
    /**
     * Indica se o plano inclui camisas de edição especial
     */
    @Column(name = "includes_special_editions")
    private Boolean includesSpecialEditions = false;
    
    /**
     * Indica se o plano permite personalização (nome/número)
     */
    @Column(name = "allows_customization")
    private Boolean allowsCustomization = false;
    
    /**
     * Indica se inclui camisas de seleções nacionais
     */
    @Column(name = "includes_national_teams")
    private Boolean includesNationalTeams = true;
    
    /**
     * Indica se inclui camisas de clubes
     */
    @Column(name = "includes_clubs")
    private Boolean includesClubs = true;
    
    /**
     * Liga preferencial (se focado em uma liga específica)
     */
    @Column(name = "preferred_league")
    private String preferredLeague;
    
    /**
     * Fabricante preferencial (Nike, Adidas, Puma, etc.)
     */
    @Column(name = "preferred_manufacturer")
    private String preferredManufacturer;
    
    /**
     * Indica se inclui camisas femininas
     */
    @Column(name = "includes_women_sizes")
    private Boolean includesWomenSizes = false;
    
    /**
     * Indica se inclui camisas infantis
     */
    @Column(name = "includes_kids_sizes")
    private Boolean includesKidsSizes = false;
    
    /**
     * Indica se inclui acessórios (meias, shorts, etc.)
     */
    @Column(name = "includes_accessories")
    private Boolean includesAccessories = false;
    
    /**
     * Indica se inclui certificado de autenticidade
     */
    @Column(name = "includes_certificate")
    private Boolean includesCertificate = false;
    
    /**
     * Desconto em loja parceira
     */
    @Column(name = "store_discount_percent")
    private Integer storeDiscountPercent = 0;
    
    /**
     * Indica se inclui camisas de goleiro
     */
    @Column(name = "includes_goalkeeper")
    private Boolean includesGoalkeeper = false;
    
    /**
     * Indica se inclui camisas de treino
     */
    @Column(name = "includes_training")
    private Boolean includesTraining = false;
    
    /**
     * Verifica se é um plano para colecionadores
     */
    public boolean isCollectorPlan() {
        return authenticOnly || includesRetro || includesSpecialEditions;
    }
    
    /**
     * Retorna resumo dos benefícios
     */
    public String getBenefitsSummary() {
        StringBuilder benefits = new StringBuilder();
        
        if (authenticOnly) benefits.append("• Apenas camisas autênticas\n");
        if (includesRetro) benefits.append("• Camisas retrô/vintage\n");
        if (includesSpecialEditions) benefits.append("• Edições especiais\n");
        if (allowsCustomization) benefits.append("• Personalização (nome/número)\n");
        if (includesNationalTeams) benefits.append("• Seleções nacionais\n");
        if (includesClubs) benefits.append("• Clubes\n");
        if (includesAccessories) benefits.append("• Acessórios inclusos\n");
        if (includesCertificate) benefits.append("• Certificado de autenticidade\n");
        if (includesGoalkeeper) benefits.append("• Camisas de goleiro\n");
        if (includesTraining) benefits.append("• Camisas de treino\n");
        if (storeDiscountPercent > 0) {
            benefits.append("• ").append(storeDiscountPercent).append("% desconto em loja\n");
        }
        
        return benefits.toString();
    }
}
