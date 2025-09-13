package com.pds.pingou.planos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/planos")
public class PlanoController {
    @Autowired
    private PlanoService planoService;

    @GetMapping
    public List<PlanoResponseDTO> listarTodos() {
        return planoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(planoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PlanoResponseDTO> criar(@RequestBody PlanoRequestDTO dto) {
        PlanoResponseDTO plano = planoService.criar(dto);
        return ResponseEntity.ok(plano);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanoResponseDTO> atualizar(@PathVariable Long id, @RequestBody PlanoRequestDTO dto) {
        PlanoResponseDTO plano = planoService.atualizar(id, dto);
        return ResponseEntity.ok(plano);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        planoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
