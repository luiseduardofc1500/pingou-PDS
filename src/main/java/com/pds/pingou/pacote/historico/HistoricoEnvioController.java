package com.pds.pingou.pacote.historico;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/historico")
public class HistoricoEnvioController {

    private final HistoricoEnvioService service;

    public HistoricoEnvioController(HistoricoEnvioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<HistoricoEnvioDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<HistoricoEnvioDTO>> listByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.listByUser(userId));
    }
}


