package com.subscription.framework.hq.service;

import com.subscription.framework.hq.domain.UsuarioPreferencias;
import com.subscription.framework.hq.domain.enums.CategoriaHQ;
import com.subscription.framework.hq.domain.enums.Editora;
import com.subscription.framework.hq.dto.OnboardingRequestDTO;
import com.subscription.framework.hq.repository.UsuarioPreferenciasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Serviço responsável pelo gerenciamento de preferências do usuário
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PreferenciasService {

    private final UsuarioPreferenciasRepository preferenciasRepository;
    private final PontosService pontosService;

    /**
     * Cria preferências iniciais para um novo usuário
     */
    @Transactional
    public UsuarioPreferencias criarPreferenciasIniciais(Long userId) {
        if (preferenciasRepository.existsByUserId(userId)) {
            throw new IllegalStateException("Usuário já possui preferências cadastradas");
        }

        UsuarioPreferencias preferencias = new UsuarioPreferencias(userId);
        return preferenciasRepository.save(preferencias);
    }

    /**
     * Processa o onboarding do usuário com suas preferências
     */
    @Transactional
    public UsuarioPreferencias processarOnboarding(Long userId, OnboardingRequestDTO request) {
        UsuarioPreferencias preferencias = preferenciasRepository.findByUserId(userId)
                .orElseGet(() -> new UsuarioPreferencias(userId));

        // Define categorias favoritas
        if (request.getCategoriasFavoritas() != null) {
            request.getCategoriasFavoritas().forEach(preferencias::adicionarCategoriaFavorita);
        }

        // Define editoras favoritas
        if (request.getEditorasFavoritas() != null) {
            request.getEditorasFavoritas().forEach(preferencias::adicionarEditoraFavorita);
        }

        // Define preferência por clássicas
        if (request.getPreferenciaClassicas() != null) {
            preferencias.setPreferenciaClassicas(request.getPreferenciaClassicas());
        }

        // Define interesse em edições de colecionador
        if (request.getInteresseEdicoesColecionador() != null) {
            preferencias.setInteresseEdicoesColecionador(request.getInteresseEdicoesColecionador());
        }

        // Define séries acompanhadas
        if (request.getSeriesAcompanhadas() != null) {
            request.getSeriesAcompanhadas().forEach(preferencias::adicionarSerieAcompanhada);
        }

        preferencias.completarOnboarding();
        preferencias = preferenciasRepository.save(preferencias);

        // Adiciona pontos bonus por completar o onboarding
        pontosService.adicionarPontosBonus(userId, 100, "Conclusão do onboarding");

        log.info("Onboarding completado para usuário {}", userId);
        return preferencias;
    }

    /**
     * Busca as preferências de um usuário
     */
    @Transactional(readOnly = true)
    public UsuarioPreferencias buscarPreferencias(Long userId) {
        return preferenciasRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("Usuário não possui preferências cadastradas"));
    }

    /**
     * Atualiza categorias favoritas
     */
    @Transactional
    public UsuarioPreferencias atualizarCategoriasFavoritas(Long userId, Set<CategoriaHQ> categorias) {
        UsuarioPreferencias preferencias = buscarPreferencias(userId);
        
        preferencias.getCategoriasFavoritas().clear();
        categorias.forEach(preferencias::adicionarCategoriaFavorita);
        
        return preferenciasRepository.save(preferencias);
    }

    /**
     * Atualiza editoras favoritas
     */
    @Transactional
    public UsuarioPreferencias atualizarEditorasFavoritas(Long userId, Set<Editora> editoras) {
        UsuarioPreferencias preferencias = buscarPreferencias(userId);
        
        preferencias.getEditorasFavoritas().clear();
        editoras.forEach(preferencias::adicionarEditoraFavorita);
        
        return preferenciasRepository.save(preferencias);
    }

    /**
     * Atualiza preferência de clássicas vs modernas
     */
    @Transactional
    public UsuarioPreferencias atualizarPreferenciaClassicas(Long userId, Integer percentual) {
        if (percentual < 0 || percentual > 100) {
            throw new IllegalArgumentException("Percentual deve estar entre 0 e 100");
        }

        UsuarioPreferencias preferencias = buscarPreferencias(userId);
        preferencias.setPreferenciaClassicas(percentual);
        
        return preferenciasRepository.save(preferencias);
    }

    /**
     * Atualiza interesse em edições de colecionador
     */
    @Transactional
    public UsuarioPreferencias atualizarInteresseEdicoesColecionador(Long userId, Boolean interesse) {
        UsuarioPreferencias preferencias = buscarPreferencias(userId);
        preferencias.setInteresseEdicoesColecionador(interesse);
        
        return preferenciasRepository.save(preferencias);
    }

    /**
     * Adiciona série para acompanhar
     */
    @Transactional
    public UsuarioPreferencias adicionarSerieAcompanhada(Long userId, String serie) {
        UsuarioPreferencias preferencias = buscarPreferencias(userId);
        preferencias.adicionarSerieAcompanhada(serie);
        
        return preferenciasRepository.save(preferencias);
    }

    /**
     * Remove série acompanhada
     */
    @Transactional
    public UsuarioPreferencias removerSerieAcompanhada(Long userId, String serie) {
        UsuarioPreferencias preferencias = buscarPreferencias(userId);
        preferencias.removerSerieAcompanhada(serie);
        
        return preferenciasRepository.save(preferencias);
    }

    /**
     * Verifica se usuário completou o onboarding
     */
    @Transactional(readOnly = true)
    public boolean verificarOnboardingCompleto(Long userId) {
        return preferenciasRepository.findByUserId(userId)
                .map(UsuarioPreferencias::getOnboardingCompleto)
                .orElse(false);
    }
}
