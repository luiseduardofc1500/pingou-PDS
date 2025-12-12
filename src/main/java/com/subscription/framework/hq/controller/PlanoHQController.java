package com.subscription.framework.hq.controller;

import com.subscription.framework.hq.dto.PlanoHQResponseDTO;
import com.subscription.framework.hq.mapper.PlanoHQMapper;
import com.subscription.framework.hq.service.PlanoHQService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hq/planos")
@RequiredArgsConstructor
@Tag(name = "Planos HQ", description = "Gerenciamento de planos de assinatura de HQs")
public class PlanoHQController {

    private final PlanoHQService planoService;
    private final PlanoHQMapper planoMapper;

    @GetMapping
    @Operation(summary = "Listar todos os planos ativos")
    public ResponseEntity<List<PlanoHQResponseDTO>> listarPlanosAtivos() {
        var planos = planoService.listarPlanosAtivos();
        var response = planos.stream()
                .map(planoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar plano por ID")
    public ResponseEntity<PlanoHQResponseDTO> buscarPorId(@PathVariable Long id) {
        var plano = planoService.buscarPorId(id);
        return ResponseEntity.ok(planoMapper.toDTO(plano));
    }

    @GetMapping("/colecionador")
    @Operation(summary = "Listar planos para colecionadores")
    public ResponseEntity<List<PlanoHQResponseDTO>> listarPlanosColecionador() {
        var planos = planoService.buscarPlanosColecionador();
        var response = planos.stream()
                .map(planoMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/inicializar-padrao")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar planos padrão do sistema")
    public ResponseEntity<Void> criarPlanosPadrao() {
        planoService.criarPlanosPadrao();
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/ativar")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Ativar plano")
    public ResponseEntity<Void> ativarPlano(@PathVariable Long id) {
        planoService.ativarPlano(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/desativar")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Desativar plano")
    public ResponseEntity<Void> desativarPlano(@PathVariable Long id) {
        planoService.desativarPlano(id);
        return ResponseEntity.ok().build();
    }
}
