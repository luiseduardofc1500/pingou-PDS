package com.pds.pingou.AI.prompt;

import com.pds.pingou.planos.Plano;
import com.pds.pingou.planos.PlanoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PingouPromptProvider implements AIPromptProvider {
    
    private final PlanoRepository planoRepository;
    
    public PingouPromptProvider(PlanoRepository planoRepository) {
        this.planoRepository = planoRepository;
    }
    
    @Override
    public String buildSystemPrompt() {
        StringBuilder prompt = new StringBuilder();
        
        // Introdução e contexto
        prompt.append("Você é a assistente oficial do aplicativo *Pingou*, um app de assinatura de cachaça artesanal.\n");
        prompt.append("Sua função é exclusivamente fornecer informações sobre o Pingou, seus planos de assinatura e\n");
        prompt.append("curiosidades sobre o mundo da cachaça ligadas ao aplicativo.\n");
        prompt.append("Não responda perguntas que não estejam relacionadas ao Pingou.\n");
        prompt.append("Se for questionada sobre outros assuntos, deixe claro que só pode falar sobre os planos e serviços do aplicativo.\n\n");
        
        // Descrição do aplicativo
        prompt.append("O *Pingou* é uma plataforma que conecta amantes da cachaça a experiências únicas de degustação,\n");
        prompt.append("cultura e assinatura mensal. Os planos disponíveis atualmente são:\n\n");
        
        // Buscar planos do banco de dados
        List<Plano> planos = planoRepository.findAll();
        
        if (planos.isEmpty()) {
            prompt.append("No momento, não temos planos ativos cadastrados. Por favor, verifique mais tarde.\n\n");
        } else {
            int contador = 1;
            for (Plano plano : planos) {
                if (plano.getAtivo()) {
                    prompt.append(contador).append(". **").append(plano.getNome()).append("**\n");
                    prompt.append("   ").append(plano.getDescricao()).append("\n");
                    prompt.append("   Preço: R$ ").append(plano.getPreco()).append(" por mês\n");
                    prompt.append("   Máximo de produtos por mês: ").append(plano.getMaxProdutosPorMes()).append("\n");
                    prompt.append("   Frequência de entrega: ").append(plano.getFrequenciaEntrega()).append("\n\n");
                    contador++;
                }
            }
        }
        
        // Instruções finais
        prompt.append("Lembre-se:\n");
        prompt.append("- Você só deve responder perguntas relacionadas ao aplicativo *Pingou* e seus planos.\n");
        prompt.append("- Se alguém perguntar algo fora desse contexto, diga:\n");
        prompt.append("  \"Sou a assistente do Pingou e só posso responder perguntas sobre nossos planos de assinatura de cachaça.\"\n");
        prompt.append("- Seja sempre educada, prestativa e entusiasta sobre cachaça artesanal brasileira.\n");
        prompt.append("- Se for perguntado sobre valores, sempre mencione os preços atualizados dos planos.\n");
        
        return prompt.toString();
    }
    
    @Override
    public String buildUserPrompt(String userQuestion) {
        return "\n\nPergunta do usuário: " + userQuestion;
    }
}

