package com.subscription.framework.hq.controller;

import com.subscription.framework.hq.dto.OnboardingRequestDTO;
import com.subscription.framework.hq.dto.PreferenciasResponseDTO;
import com.subscription.framework.hq.domain.enums.CategoriaHQ;
import com.subscription.framework.hq.domain.enums.Editora;
import com.subscription.framework.hq.mapper.PreferenciasMapper;
import com.subscription.framework.hq.service.PreferenciasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/hq/preferencias")
@RequiredArgsConstructor
@Tag(name = "Preferências", description = "Gerenciamento de preferências de usuário")
public class PreferenciasController {

    private final PreferenciasService preferenciasService;
    private final PreferenciasMapper preferenciasMapper;

    @PostMapping("/onboarding")
    @Operation(summary = "Completar onboarding de preferências")
    public ResponseEntity<PreferenciasResponseDTO> onboarding(
            Authentication authentication,
            @Valid @RequestBody OnboardingRequestDTO request) {
        
        Long userId = getUserIdFromAuth(authentication);
        var preferencias = preferenciasService.processarOnboarding(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(preferenciasMapper.toDTO(preferencias));
    }

    @GetMapping
    @Operation(summary = "Buscar preferências do usuário autenticado")
    public ResponseEntity<PreferenciasResponseDTO> buscarMinhasPreferencias(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        var preferencias = preferenciasService.buscarPreferencias(userId);
        return ResponseEntity.ok(preferenciasMapper.toDTO(preferencias));
    }

    @GetMapping("/onboarding/status")
    @Operation(summary = "Verificar se usuário completou onboarding")
    public ResponseEntity<Boolean> verificarOnboarding(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        boolean completo = preferenciasService.verificarOnboardingCompleto(userId);
        return ResponseEntity.ok(completo);
    }

    @PutMapping("/categorias")
    @Operation(summary = "Atualizar categorias favoritas")
    public ResponseEntity<PreferenciasResponseDTO> atualizarCategorias(
            Authentication authentication,
            @RequestBody Set<CategoriaHQ> categorias) {
        
        Long userId = getUserIdFromAuth(authentication);
        var preferencias = preferenciasService.atualizarCategoriasFavoritas(userId, categorias);
        return ResponseEntity.ok(preferenciasMapper.toDTO(preferencias));
    }

    @PutMapping("/editoras")
    @Operation(summary = "Atualizar editoras favoritas")
    public ResponseEntity<PreferenciasResponseDTO> atualizarEditoras(
            Authentication authentication,
            @RequestBody Set<Editora> editoras) {
        
        Long userId = getUserIdFromAuth(authentication);
        var preferencias = preferenciasService.atualizarEditorasFavoritas(userId, editoras);
        return ResponseEntity.ok(preferenciasMapper.toDTO(preferencias));
    }

    @PutMapping("/classicas")
    @Operation(summary = "Atualizar preferência de HQs clássicas (0-100)")
    public ResponseEntity<PreferenciasResponseDTO> atualizarPreferenciaClassicas(
            Authentication authentication,
            @RequestParam Integer percentual) {
        
        Long userId = getUserIdFromAuth(authentication);
        var preferencias = preferenciasService.atualizarPreferenciaClassicas(userId, percentual);
        return ResponseEntity.ok(preferenciasMapper.toDTO(preferencias));
    }

    @PutMapping("/colecionador")
    @Operation(summary = "Atualizar interesse em edições de colecionador")
    public ResponseEntity<PreferenciasResponseDTO> atualizarInteresseColecionador(
            Authentication authentication,
            @RequestParam Boolean interesse) {
        
        Long userId = getUserIdFromAuth(authentication);
        var preferencias = preferenciasService.atualizarInteresseEdicoesColecionador(userId, interesse);
        return ResponseEntity.ok(preferenciasMapper.toDTO(preferencias));
    }

    @PostMapping("/series")
    @Operation(summary = "Adicionar série para acompanhar")
    public ResponseEntity<PreferenciasResponseDTO> adicionarSerie(
            Authentication authentication,
            @RequestParam String serie) {
        
        Long userId = getUserIdFromAuth(authentication);
        var preferencias = preferenciasService.adicionarSerieAcompanhada(userId, serie);
        return ResponseEntity.ok(preferenciasMapper.toDTO(preferencias));
    }

    @DeleteMapping("/series")
    @Operation(summary = "Remover série acompanhada")
    public ResponseEntity<PreferenciasResponseDTO> removerSerie(
            Authentication authentication,
            @RequestParam String serie) {
        
        Long userId = getUserIdFromAuth(authentication);
        var preferencias = preferenciasService.removerSerieAcompanhada(userId, serie);
        return ResponseEntity.ok(preferenciasMapper.toDTO(preferencias));
    }

    private Long getUserIdFromAuth(Authentication authentication) {
        // TODO: Implementar extração do userId do token JWT
        // Por enquanto, retorna um valor mockado
        return 1L;
    }
}
