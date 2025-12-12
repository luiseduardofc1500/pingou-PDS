package com.subscription.framework.hq.service;

import com.subscription.framework.core.domain.PackageItem;
import com.subscription.framework.core.repository.PackageItemRepository;
import com.subscription.framework.hq.domain.PacoteHQ;
import com.subscription.framework.hq.domain.PlanoHQ;
import com.subscription.framework.hq.domain.Quadrinho;
import com.subscription.framework.hq.repository.PacoteHQRepository;
import com.subscription.framework.hq.repository.PlanoHQRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

/**
 * Serviço responsável pela geração de pacotes de HQs com curadoria automática
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PacoteHQService {

    private final PacoteHQRepository pacoteRepository;
    private final PlanoHQRepository planoRepository;
    private final PackageItemRepository packageItemRepository;
    private final CuradoriaHQService curadoriaService;
    private final PontosService pontosService;
    private final QuadrinhoService quadrinhoService;

    /**
     * Gera um pacote curado para um usuário baseado no plano
     */
    @Transactional
    public PacoteHQ gerarPacoteCurado(Long userId, Long planoId, LocalDate deliveryDate) {
        log.info("Gerando pacote curado para usuário {} com plano {}", userId, planoId);

        PlanoHQ plano = planoRepository.findById(planoId)
                .orElseThrow(() -> new IllegalArgumentException("Plano não encontrado"));

        // Valida se o plano está ativo
        if (!plano.getActive()) {
            throw new IllegalStateException("Plano não está ativo");
        }

        // Obtém período de entrega
        YearMonth yearMonth = YearMonth.from(deliveryDate);
        int month = yearMonth.getMonthValue();
        int year = yearMonth.getYear();

        // Verifica se já existe pacote para este período
        List<PacoteHQ> pacotesExistentes = pacoteRepository.findByUserIdAndPeriodo(userId, month, year);
        if (!pacotesExistentes.isEmpty()) {
            throw new IllegalStateException("Já existe pacote para este período");
        }

        // Cria o pacote
        String nomePacote = String.format("Pacote HQ %s - %02d/%d", plano.getName(), month, year);
        PacoteHQ pacote = new PacoteHQ(
                nomePacote,
                "Pacote curado especialmente para você",
                deliveryDate,
                month,
                year,
                plano,
                userId
        );

        // Usa o serviço de curadoria para selecionar as HQs
        int quantidadeHQs = plano.getMaxItemsPerDelivery();
        List<Quadrinho> hqsCuradas = curadoriaService.curarPacoteParaUsuario(userId, quantidadeHQs);

        if (hqsCuradas.isEmpty()) {
            throw new IllegalStateException("Não foi possível curar HQs para o pacote");
        }

        // Adiciona as HQs ao pacote
        pacote = pacoteRepository.save(pacote);
        
        for (Quadrinho hq : hqsCuradas) {
            PackageItem item = new PackageItem();
            item.setPkg(pacote);
            item.setProduct(hq);
            item.setQuantity(1);
            packageItemRepository.save(item);
            
            // Reduz o estoque
            quadrinhoService.reduzirEstoque(hq.getId());
        }

        // Atualiza informações do pacote
        pacote.atualizarContagens();
        pacote.calcularTotalPontos();
        pacote.recalculateTotalValue();
        pacote = pacoteRepository.save(pacote);

        // Adiciona pontos ao usuário
        int pontosBase = pacote.getTotalPontos();
        int pontosComMultiplicador = plano.calcularPontosComMultiplicador(pontosBase);
        pontosService.adicionarPontosPorHQs(userId, hqsCuradas, pacote.getId());

        log.info("Pacote {} gerado com sucesso. {} HQs curadas, {} pontos", 
                pacote.getId(), hqsCuradas.size(), pontosComMultiplicador);

        return pacote;
    }

    /**
     * Busca pacotes de um usuário
     */
    @Transactional(readOnly = true)
    public List<PacoteHQ> buscarPacotesPorUsuario(Long userId) {
        return pacoteRepository.findByUserIdOrderByDeliveryDateDesc(userId);
    }

    /**
     * Busca pacote por ID
     */
    @Transactional(readOnly = true)
    public PacoteHQ buscarPorId(Long pacoteId) {
        return pacoteRepository.findById(pacoteId)
                .orElseThrow(() -> new IllegalArgumentException("Pacote não encontrado"));
    }

    /**
     * Busca pacotes ativos de um usuário
     */
    @Transactional(readOnly = true)
    public List<PacoteHQ> buscarPacotesAtivos(Long userId) {
        return pacoteRepository.findPacotesAtivosPorUsuario(userId);
    }

    /**
     * Gera pacotes mensais para todos os usuários com assinaturas ativas
     * Este método seria chamado por um scheduler mensal
     */
    @Transactional
    public void gerarPacotesMensaisAutomaticos() {
        log.info("Iniciando geração automática de pacotes mensais");
        
        LocalDate proximaEntrega = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        
        // TODO: Buscar todas as assinaturas ativas e gerar pacotes
        // Por enquanto, apenas log da intenção
        log.info("Pacotes seriam gerados para entrega em: {}", proximaEntrega);
    }

    /**
     * Cancela um pacote
     */
    @Transactional
    public void cancelarPacote(Long pacoteId) {
        PacoteHQ pacote = buscarPorId(pacoteId);
        
        if (!pacote.getActive()) {
            throw new IllegalStateException("Pacote já está inativo");
        }

        // Verifica se ainda não foi enviado
        if (pacote.getDeliveryDate().isBefore(LocalDate.now())) {
            throw new IllegalStateException("Não é possível cancelar pacote já enviado");
        }

        // Devolve estoque
        for (PackageItem item : pacote.getItems()) {
            if (item.getProduct() instanceof Quadrinho) {
                Quadrinho hq = (Quadrinho) item.getProduct();
                quadrinhoService.adicionarEstoque(hq.getId(), item.getQuantity());
            }
        }

        pacote.setActive(false);
        pacoteRepository.save(pacote);
        
        log.info("Pacote {} cancelado com sucesso", pacoteId);
    }
}
