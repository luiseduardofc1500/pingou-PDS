package com.subscription.framework.hq.controller;

import com.subscription.framework.hq.dto.AvaliacaoHQRequestDTO;
import com.subscription.framework.hq.dto.HistoricoHQResponseDTO;
import com.subscription.framework.hq.dto.PontosResponseDTO;
import com.subscription.framework.hq.mapper.HistoricoHQMapper;
import com.subscription.framework.hq.mapper.PontosMapper;
import com.subscription.framework.hq.repository.UsuarioHistoricoHQRepository;
import com.subscription.framework.hq.service.PontosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/hq/pontos")
@RequiredArgsConstructor
@Tag(name = "Pontos e Gamificação", description = "Sistema de pontos e gamificação")
public class PontosController {

    private final PontosService pontosService;
    private final PontosMapper pontosMapper;
    private final UsuarioHistoricoHQRepository historicoRepository;
    private final HistoricoHQMapper historicoMapper;

    @GetMapping
    @Operation(summary = "Consultar pontos do usuário autenticado")
    public ResponseEntity<PontosResponseDTO> consultarMeusPontos(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        var pontos = pontosService.consultarPontos(userId);
        return ResponseEntity.ok(pontosMapper.toDTO(pontos));
    }

    @GetMapping("/historico")
    @Operation(summary = "Buscar histórico de HQs recebidas")
    public ResponseEntity<List<HistoricoHQResponseDTO>> buscarHistorico(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        var historico = historicoRepository.findByUserId(userId);
        
        List<HistoricoHQResponseDTO> response = historico.stream()
                .map(historicoMapper::toDTO)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping("/historico/nao-lidas")
    @Operation(summary = "Buscar HQs não lidas")
    public ResponseEntity<List<HistoricoHQResponseDTO>> buscarNaoLidas(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        var historico = historicoRepository.findByUserIdAndLido(userId, false);
        
        List<HistoricoHQResponseDTO> response = historico.stream()
                .map(historicoMapper::toDTO)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/historico/{historicoId}/marcar-lido")
    @Operation(summary = "Marcar HQ como lida e ganhar pontos")
    public ResponseEntity<Void> marcarComoLido(
            Authentication authentication,
            @PathVariable Long historicoId) {
        
        Long userId = getUserIdFromAuth(authentication);
        pontosService.adicionarPontosPorLeitura(userId, historicoId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/historico/avaliar")
    @Operation(summary = "Avaliar HQ e ganhar pontos")
    public ResponseEntity<Void> avaliarHQ(
            Authentication authentication,
            @Valid @RequestBody AvaliacaoHQRequestDTO request) {
        
        Long userId = getUserIdFromAuth(authentication);
        pontosService.adicionarPontosPorAvaliacao(
                userId, 
                request.getHistoricoId(), 
                request.getNota(), 
                request.getComentario()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/estatisticas")
    @Operation(summary = "Buscar estatísticas de leitura")
    public ResponseEntity<PontosService.EstatisticasLeitura> buscarEstatisticas(
            Authentication authentication) {
        
        Long userId = getUserIdFromAuth(authentication);
        var estatisticas = pontosService.calcularEstatisticas(userId);
        return ResponseEntity.ok(estatisticas);
    }

    @GetMapping("/ranking")
    @Operation(summary = "Buscar ranking de usuários por pontos")
    public ResponseEntity<List<PontosResponseDTO>> buscarRanking() {
        var ranking = pontosService.buscarRankingPontos();
        
        List<PontosResponseDTO> response = ranking.stream()
                .map(pontosMapper::toDTO)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    private Long getUserIdFromAuth(Authentication authentication) {
        // TODO: Implementar extração do userId do token JWT
        return 1L;
    }
}
