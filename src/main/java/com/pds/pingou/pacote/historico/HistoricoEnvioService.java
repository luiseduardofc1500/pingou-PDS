package com.pds.pingou.pacote.historico;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoricoEnvioService {

    private final HistoricoEnvioRepository repository;

    public HistoricoEnvioService(HistoricoEnvioRepository repository) {
        this.repository = repository;
    }

    public List<HistoricoEnvioDTO> listAll() {
        return repository.findAllWithJoins().stream().map(HistoricoEnvioMapper::toDTO).collect(Collectors.toList());
    }

    public List<HistoricoEnvioDTO> listByUser(Long userId) {
        return repository.findByUserId(userId).stream().map(HistoricoEnvioMapper::toDTO).collect(Collectors.toList());
    }
}


