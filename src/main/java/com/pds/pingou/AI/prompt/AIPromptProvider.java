package com.pds.pingou.AI.prompt;

/**
 * Interface que define um provedor de prompts para IA.
 * 
 * Esta interface faz parte do padrão Factory e permite diferentes
 * implementações de construção de prompts para o sistema de IA.
 * 
 * @author Pingou Team
 * @version 1.0
 * @since 1.0
 */
public interface AIPromptProvider {
    
    /**
     * Constrói o prompt do sistema baseado nos dados atuais.
     * 
     * @return String contendo o prompt completo do sistema
     */
    String buildSystemPrompt();
    
    /**
     * Constrói o prompt da pergunta do usuário.
     * 
     * @param userQuestion Pergunta feita pelo usuário
     * @return String contendo o prompt completo incluindo a pergunta
     */
    String buildUserPrompt(String userQuestion);
}

