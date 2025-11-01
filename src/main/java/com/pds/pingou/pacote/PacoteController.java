package com.pds.pingou.pacote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pacotes")
public class PacoteController {
    
    @Autowired
    private PacoteService pacoteService;
    
    @GetMapping
    public List<PacoteResponseDTO> listarTodos() {
        return pacoteService.listarTodos();
    }

    @GetMapping("/me")
    public List<PacoteResponseDTO> listarMeusPacotes(Authentication authentication) {
        String email = authentication.getName();
        return pacoteService.listarParaUsuario(email);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PacoteResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pacoteService.buscarPorId(id));
    }
    
    @GetMapping("/plano/{planoId}")
    public List<PacoteResponseDTO> buscarPorPlano(@PathVariable Long planoId) {
        return pacoteService.buscarPorPlano(planoId);
    }
    
    @GetMapping("/mes/{mes}/ano/{ano}")
    public List<PacoteResponseDTO> buscarPorMesEAno(@PathVariable Integer mes, 
                                                    @PathVariable Integer ano) {
        return pacoteService.buscarPorMesEAno(mes, ano);
    }
    
    @PostMapping
    public ResponseEntity<PacoteResponseDTO> criar(@RequestBody PacoteRequestDTO dto) {
        PacoteResponseDTO pacote = pacoteService.criar(dto);
        return ResponseEntity.ok(pacote);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<PacoteResponseDTO> atualizar(@PathVariable Long id, 
                                                       @RequestBody PacoteRequestDTO dto) {
        PacoteResponseDTO pacote = pacoteService.atualizar(id, dto);
        return ResponseEntity.ok(pacote);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pacoteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/itens")
    public ResponseEntity<PacoteResponseDTO> adicionarItem(@PathVariable Long id,
                                                          @RequestBody ItemPacoteRequestDTO itemDto) {
        PacoteResponseDTO pacote = pacoteService.adicionarItem(id, itemDto);
        return ResponseEntity.ok(pacote);
    }
    
    @DeleteMapping("/{pacoteId}/itens/{itemId}")
    public ResponseEntity<PacoteResponseDTO> removerItem(@PathVariable Long pacoteId,
                                                        @PathVariable Long itemId) {
        PacoteResponseDTO pacote = pacoteService.removerItem(pacoteId, itemId);
        return ResponseEntity.ok(pacote);
    }
    
    @GetMapping("/{id}/itens")
    public ResponseEntity<List<ItemPacoteResponseDTO>> listarItensDoPacote(@PathVariable Long id) {
        List<ItemPacoteResponseDTO> itens = pacoteService.listarItensDoPacote(id);
        return ResponseEntity.ok(itens);
    }
    
    @PutMapping("/{pacoteId}/itens/{itemId}/quantidade")
    public ResponseEntity<PacoteResponseDTO> atualizarQuantidadeItem(@PathVariable Long pacoteId,
                                                                    @PathVariable Long itemId,
                                                                    @RequestParam Integer quantidade) {
        PacoteResponseDTO pacote = pacoteService.atualizarQuantidadeItem(pacoteId, itemId, quantidade);
        return ResponseEntity.ok(pacote);
    }
}