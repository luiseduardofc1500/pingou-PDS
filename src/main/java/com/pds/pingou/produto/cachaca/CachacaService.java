package com.pds.pingou.produto.cachaca;

import com.pds.pingou.produto.ProdutoRepository;
import com.pds.pingou.produto.cachaca.exception.CachacaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CachacaService {
    
    @Autowired
    private CachacaRepository cachacaRepository;
    
    public List<CachacaResponseDTO> listarTodas() {
        return cachacaRepository.findAll().stream()
                .map(produto -> CachacaMapper.toDTO((Cachaca) produto))
                .collect(Collectors.toList());
    }
    
    public List<CachacaResponseDTO> listarAtivas() {
        return cachacaRepository.findByAtivoTrue().stream()
                .map(cachaca -> CachacaMapper.toDTO((Cachaca) cachaca))
                .collect(Collectors.toList());
    }
    
    public CachacaResponseDTO buscarPorId(Long id) {
        Cachaca cachaca = cachacaRepository.findById(id)
                .map(produto -> (Cachaca) produto)
                .orElseThrow(() -> new CachacaNotFoundException(id));
        return CachacaMapper.toDTO(cachaca);
    }
    
    @Transactional
    public CachacaResponseDTO criar(CachacaRequestDTO dto) {
        Cachaca cachaca = CachacaMapper.toEntity(dto);
        cachaca = cachacaRepository.save(cachaca);
        return CachacaMapper.toDTO(cachaca);
    }
    
    @Transactional
    public CachacaResponseDTO atualizar(Long id, CachacaRequestDTO dto) {
        Cachaca cachaca = cachacaRepository.findById(id)
                .map(produto -> (Cachaca) produto)
                .orElseThrow(() -> new CachacaNotFoundException(id));
        
        CachacaMapper.updateEntity(cachaca, dto);
        cachaca = cachacaRepository.save(cachaca);
        return CachacaMapper.toDTO(cachaca);
    }
    
    @Transactional
    public void deletar(Long id) {
        Cachaca cachaca = cachacaRepository.findById(id)
                .map(produto -> (Cachaca) produto)
                .orElseThrow(() -> new CachacaNotFoundException(id));
        
        cachaca.setAtivo(false);
        cachacaRepository.save(cachaca);
    }
    
    public List<CachacaResponseDTO> buscarPorRegiao(String regiao) {
        return cachacaRepository.findByRegiaoAndAtivoTrue(regiao).stream()
                .map(CachacaMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public List<CachacaResponseDTO> buscarPorTipo(TipoCachaca tipo) {
        return cachacaRepository.findByTipoCachacaAndAtivoTrue(tipo).stream()
                .map(CachacaMapper::toDTO)
                .collect(Collectors.toList());
    }
}