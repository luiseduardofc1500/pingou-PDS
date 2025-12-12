package com.subscription.framework.hq.controller;

import com.subscription.framework.hq.dto.PacoteHQResponseDTO;
import com.subscription.framework.hq.mapper.PacoteHQMapper;
import com.subscription.framework.hq.service.PacoteHQService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hq/pacotes")
@RequiredArgsConstructor
@Tag(name = "Pacotes HQ", description = "Gerenciamento de pacotes de HQs curados")
public class PacoteHQController {

    private final PacoteHQService pacoteService;
    private final PacoteHQMapper pacoteMapper;

    @PostMapping("/gerar")
    @Operation(summary = "Gerar pacote curado para o usuário")
    public ResponseEntity<PacoteHQResponseDTO> gerarPacoteCurado(
            Authentication authentication,
            @RequestParam Long planoId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate deliveryDate) {
        
        Long userId = getUserIdFromAuth(authentication);
        var pacote = pacoteService.gerarPacoteCurado(userId, planoId, deliveryDate);
        return ResponseEntity.status(HttpStatus.CREATED).body(pacoteMapper.toDTO(pacote));
    }

    @GetMapping
    @Operation(summary = "Listar meus pacotes")
    public ResponseEntity<List<PacoteHQResponseDTO>> listarMeusPacotes(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        var pacotes = pacoteService.buscarPacotesPorUsuario(userId);
        
        var response = pacotes.stream()
                .map(pacoteMapper::toDTO)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/ativos")
    @Operation(summary = "Listar meus pacotes ativos")
    public ResponseEntity<List<PacoteHQResponseDTO>> listarPacotesAtivos(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        var pacotes = pacoteService.buscarPacotesAtivos(userId);
        
        var response = pacotes.stream()
                .map(pacoteMapper::toDTO)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pacote por ID")
    public ResponseEntity<PacoteHQResponseDTO> buscarPorId(@PathVariable Long id) {
        var pacote = pacoteService.buscarPorId(id);
        return ResponseEntity.ok(pacoteMapper.toDTO(pacote));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar pacote")
    public ResponseEntity<Void> cancelarPacote(@PathVariable Long id) {
        pacoteService.cancelarPacote(id);
        return ResponseEntity.noContent().build();
    }

    private Long getUserIdFromAuth(Authentication authentication) {
        // TODO: Implementar extração do userId do token JWT
        return 1L;
    }
}
