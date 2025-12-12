package com.subscription.framework.hq.service;

import com.subscription.framework.hq.domain.Quadrinho;
import com.subscription.framework.hq.domain.UsuarioHistoricoHQ;
import com.subscription.framework.hq.domain.UsuarioPontos;
import com.subscription.framework.hq.repository.UsuarioHistoricoHQRepository;
import com.subscription.framework.hq.repository.UsuarioPontosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Serviço responsável pela gamificação e sistema de pontos
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PontosService {

    private final UsuarioPontosRepository pontosRepository;
    private final UsuarioHistoricoHQRepository historicoRepository;

    /**
     * Obtém ou cria o registro de pontos do usuário
     */
    @Transactional
    public UsuarioPontos obterOuCriarPontos(Long userId) {
        return pontosRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UsuarioPontos pontos = new UsuarioPontos(userId);
                    return pontosRepository.save(pontos);
                });
    }

    /**
     * Adiciona pontos ao usuário quando recebe HQs
     */
    @Transactional
    public void adicionarPontosPorHQs(Long userId, List<Quadrinho> quadrinhos, Long packageId) {
        UsuarioPontos pontos = obterOuCriarPontos(userId);
        
        int totalPontos = 0;
        
        for (Quadrinho hq : quadrinhos) {
            // Adiciona ao histórico
            UsuarioHistoricoHQ historico = new UsuarioHistoricoHQ(userId, hq, packageId);
            historicoRepository.save(historico);
            
            // Soma os pontos
            totalPontos += hq.getPontosGanho();
            
            log.info("HQ {} adicionada ao histórico do usuário {}, pontos: {}", 
                    hq.getName(), userId, hq.getPontosGanho());
        }
        
        pontos.adicionarPontos(totalPontos);
        pontosRepository.save(pontos);
        
        log.info("Total de {} pontos adicionados ao usuário {}. Pontos totais: {}", 
                totalPontos, userId, pontos.getPontosTotais());
    }

    /**
     * Adiciona pontos bonus por completar desafios
     */
    @Transactional
    public void adicionarPontosBonus(Long userId, int pontos, String motivo) {
        UsuarioPontos usuarioPontos = obterOuCriarPontos(userId);
        usuarioPontos.adicionarPontos(pontos);
        pontosRepository.save(usuarioPontos);
        
        log.info("Pontos bonus adicionados ao usuário {}: {} pontos por {}", 
                userId, pontos, motivo);
    }

    /**
     * Utiliza pontos do usuário para resgates
     */
    @Transactional
    public void utilizarPontos(Long userId, int pontos, String motivo) {
        UsuarioPontos usuarioPontos = pontosRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("Usuário não possui pontos"));
        
        usuarioPontos.utilizarPontos(pontos);
        pontosRepository.save(usuarioPontos);
        
        log.info("Usuário {} utilizou {} pontos para: {}", userId, pontos, motivo);
    }

    /**
     * Consulta pontos do usuário
     */
    @Transactional(readOnly = true)
    public UsuarioPontos consultarPontos(Long userId) {
        return pontosRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("Usuário não possui pontos"));
    }

    /**
     * Verifica se usuário tem pontos suficientes
     */
    @Transactional(readOnly = true)
    public boolean verificarPontosSuficientes(Long userId, int pontosNecessarios) {
        return pontosRepository.findByUserId(userId)
                .map(pontos -> pontos.temPontosSuficientes(pontosNecessarios))
                .orElse(false);
    }

    /**
     * Busca ranking de usuários por pontos
     */
    @Transactional(readOnly = true)
    public List<UsuarioPontos> buscarRankingPontos() {
        return pontosRepository.findTopUsuariosByPontos();
    }

    /**
     * Adiciona pontos quando usuário marca HQ como lida
     */
    @Transactional
    public void adicionarPontosPorLeitura(Long userId, Long historicoId) {
        UsuarioHistoricoHQ historico = historicoRepository.findById(historicoId)
                .orElseThrow(() -> new IllegalArgumentException("Histórico não encontrado"));
        
        if (!historico.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Histórico não pertence ao usuário");
        }
        
        if (historico.getLido()) {
            log.warn("HQ já marcada como lida anteriormente");
            return;
        }
        
        historico.marcarComoLido();
        historicoRepository.save(historico);
        
        // Bonus de 50 pontos por marcar como lido
        adicionarPontosBonus(userId, 50, "Leitura de HQ");
    }

    /**
     * Adiciona pontos quando usuário avalia uma HQ
     */
    @Transactional
    public void adicionarPontosPorAvaliacao(Long userId, Long historicoId, Integer nota, String comentario) {
        UsuarioHistoricoHQ historico = historicoRepository.findById(historicoId)
                .orElseThrow(() -> new IllegalArgumentException("Histórico não encontrado"));
        
        if (!historico.getUserId().equals(userId)) {
            throw new IllegalArgumentException("Histórico não pertence ao usuário");
        }
        
        if (historico.getAvaliacao() != null) {
            log.warn("HQ já avaliada anteriormente");
            return;
        }
        
        historico.avaliar(nota, comentario);
        historicoRepository.save(historico);
        
        // Bonus de 25 pontos por avaliar
        adicionarPontosBonus(userId, 25, "Avaliação de HQ");
    }

    /**
     * Calcula estatísticas de leitura do usuário
     */
    @Transactional(readOnly = true)
    public EstatisticasLeitura calcularEstatisticas(Long userId) {
        Long totalRecebidas = historicoRepository.countByUserId(userId);
        Long totalLidas = historicoRepository.countLidosByUserId(userId);
        
        double percentualLidas = totalRecebidas > 0 
                ? (totalLidas.doubleValue() / totalRecebidas.doubleValue()) * 100 
                : 0.0;
        
        return new EstatisticasLeitura(totalRecebidas, totalLidas, percentualLidas);
    }

    /**
     * Classe interna para estatísticas
     */
    public static class EstatisticasLeitura {
        private final Long totalRecebidas;
        private final Long totalLidas;
        private final Double percentualLidas;

        public EstatisticasLeitura(Long totalRecebidas, Long totalLidas, Double percentualLidas) {
            this.totalRecebidas = totalRecebidas;
            this.totalLidas = totalLidas;
            this.percentualLidas = percentualLidas;
        }

        public Long getTotalRecebidas() {
            return totalRecebidas;
        }

        public Long getTotalLidas() {
            return totalLidas;
        }

        public Double getPercentualLidas() {
            return percentualLidas;
        }
    }
}
