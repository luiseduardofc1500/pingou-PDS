package com.pds.pingou.AI.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.pds.pingou.AI.dto.AIQuestionDTO;
import com.pds.pingou.AI.dto.AIResponseDTO;
import com.pds.pingou.AI.prompt.AIPromptProvider;
import com.pds.pingou.AI.prompt.AIPromptProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AIService {
    
    private static final Logger logger = LoggerFactory.getLogger(AIService.class);
    private static final String DEFAULT_MODEL = "gemini-2.0-flash";
    
    private final AIPromptProviderFactory promptProviderFactory;
    private final Client aiClient;
    
    @Value("${ai.model:gemini-2.0-flash}")
    private String modelName;
    
    public AIService(AIPromptProviderFactory promptProviderFactory) {
        this.promptProviderFactory = promptProviderFactory;
        this.aiClient = new Client();
    }

    public AIResponseDTO processQuestion(AIQuestionDTO questionDTO) {
        try {
            // Validação da entrada
            if (questionDTO.getQuestion() == null || questionDTO.getQuestion().trim().isEmpty()) {
                return AIResponseDTO.error("Pergunta não pode estar vazia");
            }
            
            // Obter o provedor de prompts apropriado via Factory
            AIPromptProvider promptProvider = promptProviderFactory.createProvider(
                questionDTO.getContextType() != null ? questionDTO.getContextType() : "pingou"
            );
            
            // Construir o prompt completo
            String systemPrompt = promptProvider.buildSystemPrompt();
            String userPrompt = promptProvider.buildUserPrompt(questionDTO.getQuestion());
            String fullPrompt = systemPrompt + userPrompt;
            
            logger.info("Processando pergunta para IA: {}", questionDTO.getQuestion());
            
            // Chamar a API do Gemini
            GenerateContentResponse response = aiClient.models.generateContent(
                modelName != null ? modelName : DEFAULT_MODEL,
                fullPrompt,
                null
            );
            
            String responseText = response.text();
            logger.info("Resposta da IA gerada com sucesso");
            
            return new AIResponseDTO(responseText);
            
        } catch (IllegalArgumentException e) {
            logger.error("Tipo de contexto inválido: {}", e.getMessage());
            return AIResponseDTO.error("Tipo de contexto inválido: " + e.getMessage());
            
        } catch (Exception e) {
            logger.error("Erro ao processar pergunta na IA: {}", e.getMessage(), e);
            return AIResponseDTO.error("Erro ao processar pergunta: " + e.getMessage());
        }
    }

    public String processSimpleQuestion(String question) {
        AIQuestionDTO dto = new AIQuestionDTO();
        dto.setQuestion(question);
        AIResponseDTO response = processQuestion(dto);
        
        if (response.isSuccess()) {
            return response.getResponse();
        } else {
            return "Erro: " + response.getError();
        }
    }
}

