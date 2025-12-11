package com.subscription.framework.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
public class AIController {

    @PostMapping("/prompt")
    public ResponseEntity<String> prompt(@RequestBody String prompt) {
        // Simulação de resposta da IA
        String resposta = "[IA] Resposta simulada para: " + prompt;
        return ResponseEntity.ok(resposta);
    }
}
