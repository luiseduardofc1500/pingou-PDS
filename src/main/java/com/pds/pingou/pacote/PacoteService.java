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
            for (ItemPacoteRequestDTO itemDto : dto.getItens()) {
                Produto produto = produtoRepository.findById(itemDto.getProdutoId())
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + itemDto.getProdutoId()));
                
                ItemPacote item = ItemPacoteMapper.toEntity(itemDto, pacote, produto);
                pacote.adicionarItem(item);
            }
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
}