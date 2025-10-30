package com.pds.pingou.produto.cachaca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cachacas")
public class CachacaController {
    
    @Autowired
    private CachacaService cachacaService;
    
    @GetMapping
    public List<CachacaResponseDTO> listarTodas() {
        return cachacaService.listarAtivas();
    }
    
    @GetMapping("/todas")
    public List<CachacaResponseDTO> listarTodasIncluindoInativas() {
        return cachacaService.listarTodas();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<CachacaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cachacaService.buscarPorId(id));
    }
    
    @PostMapping
    public ResponseEntity<CachacaResponseDTO> criar(@RequestBody CachacaRequestDTO dto) {
        CachacaResponseDTO cachaca = cachacaService.criar(dto);
        return ResponseEntity.ok(cachaca);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CachacaResponseDTO> atualizar(@PathVariable Long id, 
                                                        @RequestBody CachacaRequestDTO dto) {
        CachacaResponseDTO cachaca = cachacaService.atualizar(id, dto);
        return ResponseEntity.ok(cachaca);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        cachacaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/regiao/{regiao}")
    public List<CachacaResponseDTO> buscarPorRegiao(@PathVariable String regiao) {
        return cachacaService.buscarPorRegiao(regiao);
    }
    
    @GetMapping("/tipo/{tipo}")
    public List<CachacaResponseDTO> buscarPorTipo(@PathVariable TipoCachaca tipo) {
        return cachacaService.buscarPorTipo(tipo);
    }
}