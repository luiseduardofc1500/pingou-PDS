package com.subscription.framework.hq.service;

import com.subscription.framework.core.service.AbstractProductService;
import com.subscription.framework.hq.domain.Quadrinho;
import com.subscription.framework.hq.domain.enums.CategoriaHQ;
import com.subscription.framework.hq.domain.enums.Editora;
import com.subscription.framework.hq.domain.enums.TipoHQ;
import com.subscription.framework.hq.dto.QuadrinhoRequestDTO;
import com.subscription.framework.hq.dto.QuadrinhoResponseDTO;
import com.subscription.framework.hq.mapper.QuadrinhoMapper;
import com.subscription.framework.hq.repository.QuadrinhoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Serviço para gerenciar Quadrinhos (HQs)
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class QuadrinhoService {

    private final QuadrinhoRepository quadrinhoRepository;
    private final QuadrinhoMapper quadrinhoMapper;

    /**
     * Cria um novo quadrinho
     */
    @Transactional
    public QuadrinhoResponseDTO criarQuadrinho(QuadrinhoRequestDTO request) {
        log.info("Criando novo quadrinho: {}", request.getName());

        Quadrinho quadrinho = quadrinhoMapper.toEntity(request);
        quadrinho.setActive(true);
        
        quadrinho = quadrinhoRepository.save(quadrinho);
        
        log.info("Quadrinho criado com sucesso. ID: {}", quadrinho.getId());
        return quadrinhoMapper.toDTO(quadrinho);
    }

    /**
     * Busca quadrinho por ID
     */
    @Transactional(readOnly = true)
    public QuadrinhoResponseDTO buscarPorId(Long id) {
        Quadrinho quadrinho = quadrinhoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quadrinho não encontrado"));
        
        return quadrinhoMapper.toDTO(quadrinho);
    }

    /**
     * Lista todos os quadrinhos
     */
    @Transactional(readOnly = true)
    public List<QuadrinhoResponseDTO> listarTodos() {
        return quadrinhoRepository.findAll().stream()
                .map(quadrinhoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Lista apenas quadrinhos disponíveis (ativos e com estoque)
     */
    @Transactional(readOnly = true)
    public List<QuadrinhoResponseDTO> listarDisponiveis() {
        return quadrinhoRepository.findAllDisponiveis().stream()
                .map(quadrinhoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca quadrinhos por editora
     */
    @Transactional(readOnly = true)
    public List<QuadrinhoResponseDTO> buscarPorEditora(Editora editora) {
        return quadrinhoRepository.findDisponiveisByEditora(editora).stream()
                .map(quadrinhoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca quadrinhos por tipo (Clássica/Moderna)
     */
    @Transactional(readOnly = true)
    public List<QuadrinhoResponseDTO> buscarPorTipo(TipoHQ tipo) {
        return quadrinhoRepository.findDisponiveisByTipo(tipo).stream()
                .map(quadrinhoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca quadrinhos por categorias
     */
    @Transactional(readOnly = true)
    public List<QuadrinhoResponseDTO> buscarPorCategorias(Set<CategoriaHQ> categorias) {
        return quadrinhoRepository.findDisponiveisByCategorias(categorias).stream()
                .map(quadrinhoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca quadrinhos por série
     */
    @Transactional(readOnly = true)
    public List<QuadrinhoResponseDTO> buscarPorSerie(String serie) {
        return quadrinhoRepository.findBySerie(serie).stream()
                .map(quadrinhoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca edições de colecionador
     */
    @Transactional(readOnly = true)
    public List<QuadrinhoResponseDTO> buscarEdicoesColecionador() {
        return quadrinhoRepository.findByEdicaoColecionador(true).stream()
                .filter(q -> q.getActive() && q.temEstoqueDisponivel())
                .map(quadrinhoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Atualiza um quadrinho
     */
    @Transactional
    public QuadrinhoResponseDTO atualizar(Long id, QuadrinhoRequestDTO request) {
        log.info("Atualizando quadrinho ID: {}", id);

        Quadrinho quadrinho = quadrinhoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quadrinho não encontrado"));

        quadrinhoMapper.updateEntity(request, quadrinho);
        quadrinho = quadrinhoRepository.save(quadrinho);
        
        log.info("Quadrinho atualizado com sucesso");
        return quadrinhoMapper.toDTO(quadrinho);
    }

    /**
     * Adiciona estoque ao quadrinho
     */
    @Transactional
    public QuadrinhoResponseDTO adicionarEstoque(Long id, Integer quantidade) {
        log.info("Adicionando {} unidades ao estoque do quadrinho ID: {}", quantidade, id);

        Quadrinho quadrinho = quadrinhoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quadrinho não encontrado"));

        quadrinho.adicionarEstoque(quantidade);
        quadrinho = quadrinhoRepository.save(quadrinho);
        
        return quadrinhoMapper.toDTO(quadrinho);
    }

    /**
     * Remove estoque do quadrinho
     */
    @Transactional
    public QuadrinhoResponseDTO reduzirEstoque(Long id) {
        Quadrinho quadrinho = quadrinhoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quadrinho não encontrado"));

        if (!quadrinho.temEstoqueDisponivel()) {
            throw new IllegalStateException("Quadrinho sem estoque disponível");
        }

        quadrinho.reduzirEstoque();
        quadrinho = quadrinhoRepository.save(quadrinho);
        
        return quadrinhoMapper.toDTO(quadrinho);
    }

    /**
     * Ativa um quadrinho
     */
    @Transactional
    public QuadrinhoResponseDTO ativar(Long id) {
        Quadrinho quadrinho = quadrinhoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quadrinho não encontrado"));

        quadrinho.setActive(true);
        quadrinho = quadrinhoRepository.save(quadrinho);
        
        return quadrinhoMapper.toDTO(quadrinho);
    }

    /**
     * Desativa um quadrinho
     */
    @Transactional
    public QuadrinhoResponseDTO desativar(Long id) {
        Quadrinho quadrinho = quadrinhoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quadrinho não encontrado"));

        quadrinho.setActive(false);
        quadrinho = quadrinhoRepository.save(quadrinho);
        
        return quadrinhoMapper.toDTO(quadrinho);
    }

    /**
     * Deleta um quadrinho
     */
    @Transactional
    public void deletar(Long id) {
        log.info("Deletando quadrinho ID: {}", id);

        if (!quadrinhoRepository.existsById(id)) {
            throw new IllegalArgumentException("Quadrinho não encontrado");
        }

        quadrinhoRepository.deleteById(id);
        log.info("Quadrinho deletado com sucesso");
    }
}
