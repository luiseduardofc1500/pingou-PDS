package com.pds.pingou.AI;

import com.pds.pingou.AI.dto.AIQuestionDTO;
import com.pds.pingou.AI.dto.AIResponseDTO;
import com.pds.pingou.AI.service.AIService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para o módulo de Inteligência Artificial.
 * 
 * Fornece endpoints para interação com a assistente virtual do Pingou,
 * utilizando o padrão Factory para geração de prompts dinâmicos
 * baseados nos dados do banco de dados.
 * 
 * @author Pingou Team
 * @version 2.0
 * @since 1.0
 */
@RestController
@RequestMapping("/api/v1/ai")
public class AIController {
    
    private final AIService aiService;
    
    public AIController(AIService aiService) {
        this.aiService = aiService;
    }
    
    /**
     * Endpoint para fazer perguntas à assistente virtual.
     * 
     * @param questionDTO DTO contendo a pergunta do usuário
     * @return Resposta da IA
     */
    @PostMapping("/ask")
    public ResponseEntity<AIResponseDTO> ask(@Valid @RequestBody AIQuestionDTO questionDTO) {
        AIResponseDTO response = aiService.processQuestion(questionDTO);
        
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Endpoint simplificado para perguntas diretas (backward compatibility).
     * 
     * @param question Pergunta em formato de texto simples
     * @return Resposta da IA em texto simples
     */
    @PostMapping
    public ResponseEntity<String> askSimple(@RequestBody String question) {
        String response = aiService.processSimpleQuestion(question);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Endpoint para verificar se o serviço está funcionando.
     * 
     * @return Status do serviço
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("AI Service is running");
    }
}
