package com.pds.pingou.pacote;

import com.pds.pingou.pacote.exception.PacoteNotFoundException;
import com.pds.pingou.planos.Plano;
import com.pds.pingou.planos.PlanoRepository;
import com.pds.pingou.planos.exception.PlanoNotFoundException;
import com.pds.pingou.produto.Produto;
import com.pds.pingou.produto.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacoteService {
    
    @Autowired
    private PacoteRepository pacoteRepository;
    
    @Autowired
    private PlanoRepository planoRepository;
    
    @Autowired
    private ProdutoRepository produtoRepository;
    
    @Autowired
    private ItemPacoteRepository itemPacoteRepository;
    
    public List<PacoteResponseDTO> listarTodos() {
        return pacoteRepository.findAll().stream()
                .map(PacoteMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public PacoteResponseDTO buscarPorId(Long id) {
        Pacote pacote = pacoteRepository.findById(id)
                .orElseThrow(() -> new PacoteNotFoundException(id));
        return PacoteMapper.toDTO(pacote);
    }
    
    public List<PacoteResponseDTO> buscarPorPlano(Long planoId) {
        Plano plano = planoRepository.findById(planoId)
                .orElseThrow(() -> new PlanoNotFoundException(planoId));
        
        return pacoteRepository.findByPlanoAndAtivoTrue(plano).stream()
                .map(PacoteMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<PacoteResponseDTO> buscarPorMesEAno(Integer mes, Integer ano) {
        return pacoteRepository.findByMesAndAnoAndAtivoTrue(mes, ano).stream()
                .map(PacoteMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public PacoteResponseDTO criar(PacoteRequestDTO dto) {
        Plano plano = planoRepository.findById(dto.getPlanoId())
                .orElseThrow(() -> new PlanoNotFoundException(dto.getPlanoId()));
        
        Pacote pacote = PacoteMapper.toEntity(dto, plano);
        pacote = pacoteRepository.save(pacote);
        
        // Adicionar itens se fornecidos
        if (dto.getItens() != null && !dto.getItens().isEmpty()) {
            this.adicionarItensToPacote(pacote, dto.getItens());
            pacote = pacoteRepository.save(pacote);
        }
        
        return PacoteMapper.toDTO(pacote);
    }
    
    @Transactional
    public PacoteResponseDTO atualizar(Long id, PacoteRequestDTO dto) {
        Pacote pacote = pacoteRepository.findById(id)
                .orElseThrow(() -> new PacoteNotFoundException(id));
        
        PacoteMapper.updateEntity(pacote, dto);
        pacote = pacoteRepository.save(pacote);
        return PacoteMapper.toDTO(pacote);
    }
    
    @Transactional
    public void deletar(Long id) {
        Pacote pacote = pacoteRepository.findById(id)
                .orElseThrow(() -> new PacoteNotFoundException(id));
        
        pacote.setAtivo(false);
        pacoteRepository.save(pacote);
    }
    
    @Transactional
    public PacoteResponseDTO adicionarItem(Long pacoteId, ItemPacoteRequestDTO itemDto) {
        Pacote pacote = pacoteRepository.findById(pacoteId)
                .orElseThrow(() -> new PacoteNotFoundException(pacoteId));
        
        Produto produto = produtoRepository.findById(itemDto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemDto.getProdutoId()));
        
        ItemPacote item = ItemPacoteMapper.toEntity(itemDto, pacote, produto);
        pacote.adicionarItem(item);
        
        pacote = pacoteRepository.save(pacote);
        return PacoteMapper.toDTO(pacote);
    }
    
    @Transactional
    public PacoteResponseDTO removerItem(Long pacoteId, Long itemId) {
        Pacote pacote = pacoteRepository.findById(pacoteId)
                .orElseThrow(() -> new PacoteNotFoundException(pacoteId));
        
        ItemPacote item = itemPacoteRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado: " + itemId));
        
        pacote.removerItem(item);
        itemPacoteRepository.delete(item);
        
        pacote = pacoteRepository.save(pacote);
        return PacoteMapper.toDTO(pacote);
    }
    
    /**
     * Método privado para adicionar múltiplos itens a um pacote.
     * Evita duplicação de código entre criar() e outros métodos.
     * 
     * @param pacote Pacote que receberá os itens
     * @param itensDto Lista de DTOs dos itens a serem adicionados
     */
    private void adicionarItensToPacote(Pacote pacote, List<ItemPacoteRequestDTO> itensDto) {
        for (ItemPacoteRequestDTO itemDto : itensDto) {
            this.adicionarItemToPacote(pacote, itemDto);
        }
    }
    
    /**
     * Método privado para adicionar um único item a um pacote.
     * Centraliza a validação e criação de ItemPacote.
     * 
     * @param pacote Pacote que receberá o item
     * @param itemDto DTO do item a ser adicionado
     */
    private void adicionarItemToPacote(Pacote pacote, ItemPacoteRequestDTO itemDto) {
        // Validar se o produto existe
        Produto produto = produtoRepository.findById(itemDto.getProdutoId())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemDto.getProdutoId()));
        
        // Validar se o produto está ativo
        if (!produto.getAtivo()) {
            throw new RuntimeException("Produto inativo não pode ser adicionado ao pacote: " + itemDto.getProdutoId());
        }
        
        // Validar quantidade
        if (itemDto.getQuantidade() == null || itemDto.getQuantidade() <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        
        // Verificar se não excede o limite do plano
        int totalItensAtual = pacote.getItens().stream()
                .mapToInt(ItemPacote::getQuantidade)
                .sum();
        
        if (totalItensAtual + itemDto.getQuantidade() > pacote.getPlano().getMaxProdutosPorMes()) {
            throw new RuntimeException("Quantidade de produtos excede o limite do plano: " + 
                    pacote.getPlano().getMaxProdutosPorMes());
        }
        
        // Criar e adicionar o item
        ItemPacote item = ItemPacoteMapper.toEntity(itemDto, pacote, produto);
        pacote.adicionarItem(item);
    }
    
    /**
     * Lista todos os itens de um pacote específico.
     * 
     * @param pacoteId ID do pacote
     * @return Lista de DTOs dos itens do pacote
     */
    public List<ItemPacoteResponseDTO> listarItensDoPacote(Long pacoteId) {
        Pacote pacote = pacoteRepository.findById(pacoteId)
                .orElseThrow(() -> new PacoteNotFoundException(pacoteId));
        
        return pacote.getItens().stream()
                .map(ItemPacoteMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Atualiza a quantidade de um item específico do pacote.
     * 
     * @param pacoteId ID do pacote
     * @param itemId ID do item
     * @param novaQuantidade Nova quantidade do item
     * @return DTO do pacote atualizado
     */
    @Transactional
    public PacoteResponseDTO atualizarQuantidadeItem(Long pacoteId, Long itemId, Integer novaQuantidade) {
        Pacote pacote = pacoteRepository.findById(pacoteId)
                .orElseThrow(() -> new PacoteNotFoundException(pacoteId));
        
        ItemPacote item = itemPacoteRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item não encontrado: " + itemId));
        
        // Validar se o item pertence ao pacote
        if (!item.getPacote().getId().equals(pacoteId)) {
            throw new RuntimeException("Item não pertence ao pacote especificado");
        }
        
        // Validar nova quantidade
        if (novaQuantidade == null || novaQuantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        
        // Calcular total sem o item atual
        int totalSemItemAtual = pacote.getItens().stream()
                .filter(i -> !i.getId().equals(itemId))
                .mapToInt(ItemPacote::getQuantidade)
                .sum();
        
        // Validar se não excede limite do plano
        if (totalSemItemAtual + novaQuantidade > pacote.getPlano().getMaxProdutosPorMes()) {
            throw new RuntimeException("Quantidade de produtos excede o limite do plano: " + 
                    pacote.getPlano().getMaxProdutosPorMes());
        }
        
        item.setQuantidade(novaQuantidade);
        itemPacoteRepository.save(item);
        
        return PacoteMapper.toDTO(pacote);
    }
}