package com.subscription.framework.hq.controller;

import com.subscription.framework.hq.domain.enums.CategoriaHQ;
import com.subscription.framework.hq.domain.enums.Editora;
import com.subscription.framework.hq.domain.enums.TipoHQ;
import com.subscription.framework.hq.dto.QuadrinhoRequestDTO;
import com.subscription.framework.hq.dto.QuadrinhoResponseDTO;
import com.subscription.framework.hq.service.QuadrinhoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/hq/quadrinhos")
@RequiredArgsConstructor
@Tag(name = "Quadrinhos", description = "Gerenciamento de HQs")
public class QuadrinhoController {

    private final QuadrinhoService quadrinhoService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar novo quadrinho")
    public ResponseEntity<QuadrinhoResponseDTO> criar(@Valid @RequestBody QuadrinhoRequestDTO request) {
        QuadrinhoResponseDTO response = quadrinhoService.criarQuadrinho(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar quadrinho por ID")
    public ResponseEntity<QuadrinhoResponseDTO> buscarPorId(@PathVariable Long id) {
        QuadrinhoResponseDTO response = quadrinhoService.buscarPorId(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos os quadrinhos")
    public ResponseEntity<List<QuadrinhoResponseDTO>> listarTodos() {
        List<QuadrinhoResponseDTO> response = quadrinhoService.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/disponiveis")
    @Operation(summary = "Listar quadrinhos disponíveis")
    public ResponseEntity<List<QuadrinhoResponseDTO>> listarDisponiveis() {
        List<QuadrinhoResponseDTO> response = quadrinhoService.listarDisponiveis();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/editora/{editora}")
    @Operation(summary = "Buscar quadrinhos por editora")
    public ResponseEntity<List<QuadrinhoResponseDTO>> buscarPorEditora(@PathVariable Editora editora) {
        List<QuadrinhoResponseDTO> response = quadrinhoService.buscarPorEditora(editora);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/tipo/{tipo}")
    @Operation(summary = "Buscar quadrinhos por tipo (CLASSICA/MODERNA)")
    public ResponseEntity<List<QuadrinhoResponseDTO>> buscarPorTipo(@PathVariable TipoHQ tipo) {
        List<QuadrinhoResponseDTO> response = quadrinhoService.buscarPorTipo(tipo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/categorias")
    @Operation(summary = "Buscar quadrinhos por categorias")
    public ResponseEntity<List<QuadrinhoResponseDTO>> buscarPorCategorias(
            @RequestParam Set<CategoriaHQ> categorias) {
        List<QuadrinhoResponseDTO> response = quadrinhoService.buscarPorCategorias(categorias);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/serie/{serie}")
    @Operation(summary = "Buscar quadrinhos por série")
    public ResponseEntity<List<QuadrinhoResponseDTO>> buscarPorSerie(@PathVariable String serie) {
        List<QuadrinhoResponseDTO> response = quadrinhoService.buscarPorSerie(serie);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/colecionador")
    @Operation(summary = "Buscar edições de colecionador")
    public ResponseEntity<List<QuadrinhoResponseDTO>> buscarEdicoesColecionador() {
        List<QuadrinhoResponseDTO> response = quadrinhoService.buscarEdicoesColecionador();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Atualizar quadrinho")
    public ResponseEntity<QuadrinhoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody QuadrinhoRequestDTO request) {
        QuadrinhoResponseDTO response = quadrinhoService.atualizar(id, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/estoque/adicionar")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Adicionar estoque ao quadrinho")
    public ResponseEntity<QuadrinhoResponseDTO> adicionarEstoque(
            @PathVariable Long id,
            @RequestParam Integer quantidade) {
        QuadrinhoResponseDTO response = quadrinhoService.adicionarEstoque(id, quantidade);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/ativar")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Ativar quadrinho")
    public ResponseEntity<QuadrinhoResponseDTO> ativar(@PathVariable Long id) {
        QuadrinhoResponseDTO response = quadrinhoService.ativar(id);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/desativar")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Desativar quadrinho")
    public ResponseEntity<QuadrinhoResponseDTO> desativar(@PathVariable Long id) {
        QuadrinhoResponseDTO response = quadrinhoService.desativar(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Deletar quadrinho")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        quadrinhoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
