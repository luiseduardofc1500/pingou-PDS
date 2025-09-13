package com.pds.pingou.planos;

import com.pds.pingou.planos.exception.PlanoNotFoundException;
import com.pds.pingou.planos.exception.PlanoNomeDuplicadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanoService {
    @Autowired
    private PlanoRepository planoRepository;

    public List<PlanoResponseDTO> listarTodos() {
        return planoRepository.findAll().stream()
                .map(PlanoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PlanoResponseDTO buscarPorId(Long id) {
        Plano plano = planoRepository.findById(id)
                .orElseThrow(() -> new PlanoNotFoundException(id));
        return PlanoMapper.toDTO(plano);
    }

    @Transactional
    public PlanoResponseDTO criar(PlanoRequestDTO dto) {
        if (planoRepository.existsByNome(dto.getNome())) {
            throw new PlanoNomeDuplicadoException(dto.getNome());
        }
        Plano plano = PlanoMapper.toEntity(dto);
        plano = planoRepository.save(plano);
        return PlanoMapper.toDTO(plano);
    }

    @Transactional
    public PlanoResponseDTO atualizar(Long id, PlanoRequestDTO dto) {
        Plano plano = planoRepository.findById(id)
                .orElseThrow(() -> new PlanoNotFoundException(id));
        plano.setNome(dto.getNome());
        plano.setDescricao(dto.getDescricao());
        plano.setPreco(dto.getPreco());
        plano.setPlanoTipo(dto.getPlanoTipo());
        plano = planoRepository.save(plano);
        return PlanoMapper.toDTO(plano);
    }

    @Transactional
    public void deletar(Long id) {
        if (!planoRepository.existsById(id)) {
            throw new PlanoNotFoundException(id);
        }
        planoRepository.deleteById(id);
    }
}
