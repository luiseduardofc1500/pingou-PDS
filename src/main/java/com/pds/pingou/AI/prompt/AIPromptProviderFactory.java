package com.pds.pingou.AI.prompt;

import org.springframework.stereotype.Component;

/**
 * Factory para criação de provedores de prompts de IA.
 * 
 * Este factory implementa o padrão Factory Method, permitindo
 * a criação flexível de diferentes tipos de provedores de prompts
 * conforme necessário.
 * 
 * @author Pingou Team
 * @version 1.0
 * @since 1.0
 */
@Component
public class AIPromptProviderFactory {
    
    private final PingouPromptProvider pingouPromptProvider;
    
    public AIPromptProviderFactory(PingouPromptProvider pingouPromptProvider) {
        this.pingouPromptProvider = pingouPromptProvider;
    }
    
    /**
     * Cria um provedor de prompts baseado no tipo solicitado.
     * 
     * @param type Tipo do provedor (atualmente suporta "pingou")
     * @return Instância do provedor de prompts
     * @throws IllegalArgumentException se o tipo não for reconhecido
     */
    public AIPromptProvider createProvider(String type) {
        if ("pingou".equalsIgnoreCase(type)) {
            return pingouPromptProvider;
        }
        throw new IllegalArgumentException("Tipo de provedor não reconhecido: " + type);
    }
    
    /**
     * Retorna o provedor padrão (Pingou).
     * 
     * @return Provedor de prompts padrão
     */
    public AIPromptProvider createDefaultProvider() {
        return pingouPromptProvider;
    }
}

