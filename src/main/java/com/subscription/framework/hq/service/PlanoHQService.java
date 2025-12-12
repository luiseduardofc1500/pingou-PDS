package com.subscription.framework.hq.service;

import com.subscription.framework.core.domain.enums.DeliveryFrequency;
import com.subscription.framework.hq.domain.PlanoHQ;
import com.subscription.framework.hq.repository.PlanoHQRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Serviço para gerenciar Planos de HQ
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PlanoHQService {

    private final PlanoHQRepository planoRepository;

    /**
     * Cria um novo plano de HQ
     */
    @Transactional
    public PlanoHQ criarPlano(PlanoHQ plano) {
        log.info("Criando novo plano: {}", plano.getName());

        // Valida percentuais
        if (!plano.validarPercentuais()) {
            throw new IllegalArgumentException(
                    "A soma dos percentuais de clássicas e modernas deve ser 100");
        }

        plano.setActive(true);
        plano = planoRepository.save(plano);
        
        log.info("Plano criado com sucesso. ID: {}", plano.getId());
        return plano;
    }

    /**
     * Busca plano por ID
     */
    @Transactional(readOnly = true)
    public PlanoHQ buscarPorId(Long id) {
        return planoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Plano não encontrado"));
    }

    /**
     * Lista todos os planos ativos
     */
    @Transactional(readOnly = true)
    public List<PlanoHQ> listarPlanosAtivos() {
        return planoRepository.findAllAtivosOrdenadosPorPreco();
    }

    /**
     * Lista todos os planos
     */
    @Transactional(readOnly = true)
    public List<PlanoHQ> listarTodos() {
        return planoRepository.findAll();
    }

    /**
     * Busca planos para colecionadores
     */
    @Transactional(readOnly = true)
    public List<PlanoHQ> buscarPlanosColecionador() {
        return planoRepository.findByPlanoColecionador(true);
    }

    /**
     * Atualiza um plano
     */
    @Transactional
    public PlanoHQ atualizarPlano(Long id, PlanoHQ planoAtualizado) {
        log.info("Atualizando plano ID: {}", id);

        PlanoHQ plano = buscarPorId(id);

        // Valida percentuais
        if (!planoAtualizado.validarPercentuais()) {
            throw new IllegalArgumentException(
                    "A soma dos percentuais de clássicas e modernas deve ser 100");
        }

        plano.setName(planoAtualizado.getName());
        plano.setDescription(planoAtualizado.getDescription());
        plano.setPrice(planoAtualizado.getPrice());
        plano.setMaxItemsPerDelivery(planoAtualizado.getMaxItemsPerDelivery());
        plano.setDeliveryFrequency(planoAtualizado.getDeliveryFrequency());
        plano.setPercentualClassicas(planoAtualizado.getPercentualClassicas());
        plano.setPercentualModernas(planoAtualizado.getPercentualModernas());
        plano.setIncluiEdicoesColecionador(planoAtualizado.getIncluiEdicoesColecionador());
        plano.setMultiplicadorPontos(planoAtualizado.getMultiplicadorPontos());
        plano.setFilosofiaCuradoria(planoAtualizado.getFilosofiaCuradoria());
        plano.setPlanoColecionador(planoAtualizado.getPlanoColecionador());

        plano = planoRepository.save(plano);
        
        log.info("Plano atualizado com sucesso");
        return plano;
    }

    /**
     * Desativa um plano
     */
    @Transactional
    public void desativarPlano(Long id) {
        log.info("Desativando plano ID: {}", id);

        PlanoHQ plano = buscarPorId(id);
        plano.setActive(false);
        planoRepository.save(plano);
        
        log.info("Plano desativado com sucesso");
    }

    /**
     * Ativa um plano
     */
    @Transactional
    public void ativarPlano(Long id) {
        log.info("Ativando plano ID: {}", id);

        PlanoHQ plano = buscarPorId(id);
        plano.setActive(true);
        planoRepository.save(plano);
        
        log.info("Plano ativado com sucesso");
    }

    /**
     * Cria planos padrão do sistema
     */
    @Transactional
    public void criarPlanosPadrao() {
        log.info("Criando planos padrão do sistema");

        // Plano Básico - Equilibrado
        PlanoHQ basico = new PlanoHQ(
                "HQ Básico",
                "Plano equilibrado com mix de clássicas e modernas",
                new BigDecimal("39.90"),
                3,
                DeliveryFrequency.MONTHLY,
                50, // 50% clássicas
                50  // 50% modernas
        );
        basico.setFilosofiaCuradoria("Mix equilibrado para quem quer conhecer diversos estilos");
        basico.setMultiplicadorPontos(1.0);
        criarPlano(basico);

        // Plano Clássico
        PlanoHQ classico = new PlanoHQ(
                "HQ Clássico",
                "Foco em quadrinhos clássicos icônicos",
                new BigDecimal("59.90"),
                5,
                DeliveryFrequency.MONTHLY,
                80, // 80% clássicas
                20  // 20% modernas
        );
        classico.setFilosofiaCuradoria("Para apreciadores das obras que marcaram época");
        classico.setMultiplicadorPontos(1.5);
        criarPlano(classico);

        // Plano Moderno
        PlanoHQ moderno = new PlanoHQ(
                "HQ Moderno",
                "As melhores HQs contemporâneas",
                new BigDecimal("49.90"),
                4,
                DeliveryFrequency.MONTHLY,
                20, // 20% clássicas
                80  // 80% modernas
        );
        moderno.setFilosofiaCuradoria("Descubra as histórias mais recentes e inovadoras");
        moderno.setMultiplicadorPontos(1.2);
        criarPlano(moderno);

        // Plano Colecionador Premium
        PlanoHQ premium = new PlanoHQ(
                "HQ Colecionador Premium",
                "Edições especiais e de colecionador",
                new BigDecimal("149.90"),
                6,
                DeliveryFrequency.MONTHLY,
                60, // 60% clássicas
                40  // 40% modernas
        );
        premium.setIncluiEdicoesColecionador(true);
        premium.setPlanoColecionador(true);
        premium.setFilosofiaCuradoria("Curadoria premium com edições raras e especiais");
        premium.setMultiplicadorPontos(2.0);
        criarPlano(premium);

        log.info("Planos padrão criados com sucesso");
    }
}
