package com.subscription.framework.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        // Simulação de autenticação
        if ("framework".equals(username) && "1234".equals(password)) {
            return ResponseEntity.ok("token-demo-123");
        }
        return ResponseEntity.status(401).body("Credenciais inválidas");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // Simulação de logout
        return ResponseEntity.ok("Logout realizado");
    }
}
