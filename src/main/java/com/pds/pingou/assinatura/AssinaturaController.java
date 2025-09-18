package com.pds.pingou.assinatura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/assinaturas")
public class AssinaturaController {
    @Autowired
    private AssinaturaService assinaturaService;

    @PostMapping("/ativar")
    public ResponseEntity<AssinaturaResponseDTO> ativar(@RequestBody AssinaturaRequestDTO dto) {
        var assinatura = assinaturaService.ativarAssinatura(dto.getUserId(), dto.getPlanoId());
        return ResponseEntity.ok(AssinaturaMapper.toDTO(assinatura));
    }

    @PostMapping("/desativar/{userId}")
    public ResponseEntity<AssinaturaResponseDTO> desativar(@PathVariable Long userId) {
        var assinatura = assinaturaService.desativarAssinatura(userId);
        return ResponseEntity.ok(AssinaturaMapper.toDTO(assinatura));
    }

    @GetMapping
    public ResponseEntity<List<AssinaturaResponseDTO>> listar() {
        var assinaturas = assinaturaService.listarAssinaturas();
        return ResponseEntity.ok(assinaturas.stream().map(AssinaturaMapper::toDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssinaturaResponseDTO> buscarPorId(@PathVariable Long id) {
        var assinatura = assinaturaService.buscarPorId(id);
        return ResponseEntity.ok(AssinaturaMapper.toDTO(assinatura));
    }

    @PostMapping
    public ResponseEntity<AssinaturaResponseDTO> criar(@RequestBody AssinaturaRequestDTO dto) {
        var assinatura = assinaturaService.criarAssinatura(dto);
        return ResponseEntity.ok(AssinaturaMapper.toDTO(assinatura));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssinaturaResponseDTO> editar(@PathVariable Long id, @RequestBody AssinaturaRequestDTO dto) {
        var assinatura = assinaturaService.editarAssinatura(id, dto);
        return ResponseEntity.ok(AssinaturaMapper.toDTO(assinatura));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        assinaturaService.deletarAssinatura(id);
        return ResponseEntity.noContent().build();
    }
}
