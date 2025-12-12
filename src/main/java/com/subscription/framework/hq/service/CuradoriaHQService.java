package com.subscription.framework.hq.service;

import com.subscription.framework.hq.domain.Quadrinho;
import com.subscription.framework.hq.domain.UsuarioHistoricoHQ;
import com.subscription.framework.hq.domain.UsuarioPreferencias;
import com.subscription.framework.hq.domain.enums.CategoriaHQ;
import com.subscription.framework.hq.domain.enums.Editora;
import com.subscription.framework.hq.domain.enums.TipoHQ;
import com.subscription.framework.hq.repository.QuadrinhoRepository;
import com.subscription.framework.hq.repository.UsuarioHistoricoHQRepository;
import com.subscription.framework.hq.repository.UsuarioPreferenciasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela curadoria inteligente de HQs.
 * Utiliza as preferências do usuário e histórico para criar pacotes personalizados.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CuradoriaHQService {

    private final QuadrinhoRepository quadrinhoRepository;
    private final UsuarioPreferenciasRepository preferenciasRepository;
    private final UsuarioHistoricoHQRepository historicoRepository;

    /**
     * Gera uma curadoria de HQs para um usuário baseado em suas preferências
     * 
     * @param userId ID do usuário
     * @param quantidadeDesejada Quantidade de HQs desejadas no pacote
     * @return Lista de quadrinhos curados
     */
    @Transactional(readOnly = true)
    public List<Quadrinho> curarPacoteParaUsuario(Long userId, int quantidadeDesejada) {
        log.info("Iniciando curadoria para usuário {} com {} itens", userId, quantidadeDesejada);

        // Busca as preferências do usuário
        UsuarioPreferencias preferencias = preferenciasRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("Usuário não possui preferências cadastradas"));

        // Busca HQs já recebidas pelo usuário
        List<Long> hqsJaRecebidas = historicoRepository.findQuadrinhoIdsRecebidosPorUsuario(userId);
        
        // Busca HQs disponíveis
        List<Quadrinho> hqsDisponiveis = quadrinhoRepository.findAllDisponiveis().stream()
                .filter(hq -> !hqsJaRecebidas.contains(hq.getId()))
                .collect(Collectors.toList());

        if (hqsDisponiveis.isEmpty()) {
            log.warn("Nenhuma HQ disponível para o usuário {}", userId);
            return Collections.emptyList();
        }

        // Calcula a distribuição entre clássicas e modernas
        int quantidadeClassicas = calcularQuantidadeClassicas(quantidadeDesejada, preferencias.getPreferenciaClassicas());
        int quantidadeModernas = quantidadeDesejada - quantidadeClassicas;

        log.info("Distribuição: {} clássicas, {} modernas", quantidadeClassicas, quantidadeModernas);

        // Separa HQs por tipo
        List<Quadrinho> classicasDisponiveis = hqsDisponiveis.stream()
                .filter(hq -> hq.getTipoHQ() == TipoHQ.CLASSICA)
                .collect(Collectors.toList());

        List<Quadrinho> modernasDisponiveis = hqsDisponiveis.stream()
                .filter(hq -> hq.getTipoHQ() == TipoHQ.MODERNA)
                .collect(Collectors.toList());

        // Seleciona as HQs com base na pontuação de relevância
        List<Quadrinho> hqsSelecionadas = new ArrayList<>();

        // Seleciona clássicas
        hqsSelecionadas.addAll(selecionarMelhoresHQs(classicasDisponiveis, preferencias, quantidadeClassicas));

        // Seleciona modernas
        hqsSelecionadas.addAll(selecionarMelhoresHQs(modernasDisponiveis, preferencias, quantidadeModernas));

        // Se não tiver quantidade suficiente, completa com o que estiver disponível
        if (hqsSelecionadas.size() < quantidadeDesejada) {
            log.warn("Quantidade insuficiente de HQs. Disponíveis: {}, Desejadas: {}", 
                    hqsSelecionadas.size(), quantidadeDesejada);
            
            List<Quadrinho> hqsRestantes = hqsDisponiveis.stream()
                    .filter(hq -> !hqsSelecionadas.contains(hq))
                    .limit(quantidadeDesejada - hqsSelecionadas.size())
                    .collect(Collectors.toList());
            
            hqsSelecionadas.addAll(hqsRestantes);
        }

        // Verifica séries que o usuário está acompanhando
        hqsSelecionadas = priorizarSeriesAcompanhadas(hqsSelecionadas, preferencias, userId);

        log.info("Curadoria concluída: {} HQs selecionadas", hqsSelecionadas.size());
        return hqsSelecionadas;
    }

    /**
     * Calcula quantas HQs clássicas devem ser incluídas baseado na preferência
     */
    private int calcularQuantidadeClassicas(int quantidadeTotal, int preferenciaClassicas) {
        // preferenciaClassicas é um valor de 0 a 100
        double percentual = preferenciaClassicas / 100.0;
        return (int) Math.round(quantidadeTotal * percentual);
    }

    /**
     * Seleciona as melhores HQs baseado nas preferências do usuário
     */
    private List<Quadrinho> selecionarMelhoresHQs(List<Quadrinho> hqs, 
                                                   UsuarioPreferencias preferencias, 
                                                   int quantidade) {
        if (hqs.isEmpty() || quantidade <= 0) {
            return Collections.emptyList();
        }

        // Calcula pontuação de relevância para cada HQ
        Map<Quadrinho, Double> pontuacoes = new HashMap<>();
        for (Quadrinho hq : hqs) {
            double pontuacao = calcularPontuacaoRelevancia(hq, preferencias);
            pontuacoes.put(hq, pontuacao);
        }

        // Ordena por pontuação e retorna as top N
        return pontuacoes.entrySet().stream()
                .sorted(Map.Entry.<Quadrinho, Double>comparingByValue().reversed())
                .limit(quantidade)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * Calcula a pontuação de relevância de uma HQ para o usuário
     */
    private double calcularPontuacaoRelevancia(Quadrinho hq, UsuarioPreferencias preferencias) {
        double pontuacao = 0.0;

        // Pontos por editora favorita (peso: 30)
        if (preferencias.getEditorasFavoritas().contains(hq.getEditora())) {
            pontuacao += 30.0;
        }

        // Pontos por categorias favoritas (peso: 40)
        long categoriasMatch = hq.getCategorias().stream()
                .filter(preferencias.getCategoriasFavoritas()::contains)
                .count();
        
        if (!preferencias.getCategoriasFavoritas().isEmpty()) {
            double percentualMatch = (double) categoriasMatch / preferencias.getCategoriasFavoritas().size();
            pontuacao += 40.0 * percentualMatch;
        }

        // Pontos por edição de colecionador (peso: 20)
        if (hq.getEdicaoColecionador() && preferencias.getInteresseEdicoesColecionador()) {
            pontuacao += 20.0;
        }

        // Pontos por série acompanhada (peso: 10)
        if (hq.getSerie() != null && preferencias.getSeriesAcompanhadas().contains(hq.getSerie())) {
            pontuacao += 10.0;
        }

        // Adiciona um pequeno fator aleatório para variedade (peso: 5)
        pontuacao += new Random().nextDouble() * 5.0;

        return pontuacao;
    }

    /**
     * Prioriza HQs de séries que o usuário está acompanhando
     */
    private List<Quadrinho> priorizarSeriesAcompanhadas(List<Quadrinho> hqsSelecionadas,
                                                         UsuarioPreferencias preferencias,
                                                         Long userId) {
        if (preferencias.getSeriesAcompanhadas().isEmpty()) {
            return hqsSelecionadas;
        }

        // Para cada série acompanhada, tenta incluir o próximo volume
        for (String serie : preferencias.getSeriesAcompanhadas()) {
            List<Quadrinho> proximosVolumes = quadrinhoRepository
                    .findDisponiveisPorSerieNaoRecebidos(serie, userId);
            
            if (!proximosVolumes.isEmpty()) {
                // Se encontrou volumes da série, substitui uma HQ aleatória de menor prioridade
                Quadrinho proximoVolume = proximosVolumes.get(0);
                
                // Procura por uma HQ que não seja de editora ou categoria favorita para substituir
                for (int i = hqsSelecionadas.size() - 1; i >= 0; i--) {
                    Quadrinho hqAtual = hqsSelecionadas.get(i);
                    
                    if (!preferencias.getEditorasFavoritas().contains(hqAtual.getEditora()) &&
                        hqAtual.getCategorias().stream()
                                .noneMatch(preferencias.getCategoriasFavoritas()::contains)) {
                        
                        hqsSelecionadas.set(i, proximoVolume);
                        log.info("Substituindo HQ por próximo volume da série: {}", serie);
                        break;
                    }
                }
            }
        }

        return hqsSelecionadas;
    }

    /**
     * Verifica se um usuário já recebeu uma HQ específica
     */
    @Transactional(readOnly = true)
    public boolean usuarioJaRecebeuHQ(Long userId, Long quadrinhoId) {
        return historicoRepository.existsByUserIdAndQuadrinhoId(userId, quadrinhoId);
    }

    /**
     * Busca HQs recomendadas baseadas nas últimas avaliações do usuário
     */
    @Transactional(readOnly = true)
    public List<Quadrinho> buscarRecomendacoesBaseadasEmAvaliacoes(Long userId, int quantidade) {
        List<UsuarioHistoricoHQ> avaliacoes = historicoRepository.findAvaliacoesPorUsuario(userId);
        
        if (avaliacoes.isEmpty()) {
            return Collections.emptyList();
        }

        // Filtra apenas avaliações com nota >= 4
        List<UsuarioHistoricoHQ> avaliacoesPositivas = avaliacoes.stream()
                .filter(h -> h.getAvaliacao() != null && h.getAvaliacao() >= 4)
                .limit(10) // Considera as últimas 10 avaliações positivas
                .collect(Collectors.toList());

        if (avaliacoesPositivas.isEmpty()) {
            return Collections.emptyList();
        }

        // Identifica padrões nas avaliações positivas
        Set<CategoriaHQ> categoriasPreferidas = new HashSet<>();
        Set<Editora> editorasPreferidas = new HashSet<>();

        for (UsuarioHistoricoHQ hist : avaliacoesPositivas) {
            Quadrinho hq = hist.getQuadrinho();
            categoriasPreferidas.addAll(hq.getCategorias());
            editorasPreferidas.add(hq.getEditora());
        }

        // Busca HQs similares não recebidas
        List<Long> hqsJaRecebidas = historicoRepository.findQuadrinhoIdsRecebidosPorUsuario(userId);
        
        List<Quadrinho> recomendacoes = quadrinhoRepository.findAllDisponiveis().stream()
                .filter(hq -> !hqsJaRecebidas.contains(hq.getId()))
                .filter(hq -> editorasPreferidas.contains(hq.getEditora()) ||
                             hq.getCategorias().stream().anyMatch(categoriasPreferidas::contains))
                .limit(quantidade)
                .collect(Collectors.toList());

        return recomendacoes;
    }
}
