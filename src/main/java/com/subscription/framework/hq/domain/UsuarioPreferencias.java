package com.subscription.framework.hq.domain;

import com.subscription.framework.hq.domain.enums.CategoriaHQ;
import com.subscription.framework.hq.domain.enums.Editora;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidade que armazena as preferências de um usuário em relação a HQs.
 * Utilizado para curadoria personalizada dos pacotes.
 */
@Entity
@Table(name = "usuario_preferencias")
@Getter
@Setter
public class UsuarioPreferencias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ID do usuário (referência ao sistema de autenticação)
     */
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    /**
     * Categorias favoritas do usuário
     */
    @ElementCollection(targetClass = CategoriaHQ.class)
    @CollectionTable(name = "usuario_categorias_favoritas", 
                    joinColumns = @JoinColumn(name = "preferencia_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private Set<CategoriaHQ> categoriasFavoritas = new HashSet<>();

    /**
     * Editoras favoritas do usuário
     */
    @ElementCollection(targetClass = Editora.class)
    @CollectionTable(name = "usuario_editoras_favoritas", 
                    joinColumns = @JoinColumn(name = "preferencia_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "editora")
    private Set<Editora> editorasFavoritas = new HashSet<>();

    /**
     * Preferência por clássicas (0-100)
     * 0 = apenas modernas, 100 = apenas clássicas
     */
    @Column(name = "preferencia_classicas", nullable = false)
    private Integer preferenciaClassicas = 50;

    /**
     * Indica se o usuário deseja receber edições de colecionador
     */
    @Column(name = "interesse_edicoes_colecionador", nullable = false)
    private Boolean interesseEdicoesColecionador = false;

    /**
     * Séries que o usuário está acompanhando
     */
    @ElementCollection
    @CollectionTable(name = "usuario_series_acompanhadas", 
                    joinColumns = @JoinColumn(name = "preferencia_id"))
    @Column(name = "serie")
    private Set<String> seriesAcompanhadas = new HashSet<>();

    /**
     * Data de criação das preferências
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Data da última atualização
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Indica se o onboarding foi completado
     */
    @Column(name = "onboarding_completo", nullable = false)
    private Boolean onboardingCompleto = false;

    public UsuarioPreferencias() {
        this.createdAt = LocalDateTime.now();
    }

    public UsuarioPreferencias(Long userId) {
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Adiciona uma categoria favorita
     */
    public void adicionarCategoriaFavorita(CategoriaHQ categoria) {
        this.categoriasFavoritas.add(categoria);
    }

    /**
     * Remove uma categoria favorita
     */
    public void removerCategoriaFavorita(CategoriaHQ categoria) {
        this.categoriasFavoritas.remove(categoria);
    }

    /**
     * Adiciona uma editora favorita
     */
    public void adicionarEditoraFavorita(Editora editora) {
        this.editorasFavoritas.add(editora);
    }

    /**
     * Remove uma editora favorita
     */
    public void removerEditoraFavorita(Editora editora) {
        this.editorasFavoritas.remove(editora);
    }

    /**
     * Adiciona uma série para acompanhar
     */
    public void adicionarSerieAcompanhada(String serie) {
        this.seriesAcompanhadas.add(serie);
    }

    /**
     * Remove uma série acompanhada
     */
    public void removerSerieAcompanhada(String serie) {
        this.seriesAcompanhadas.remove(serie);
    }

    /**
     * Verifica se o usuário tem preferência por uma categoria
     */
    public boolean temPreferenciaPorCategoria(CategoriaHQ categoria) {
        return this.categoriasFavoritas.contains(categoria);
    }

    /**
     * Verifica se o usuário tem preferência por uma editora
     */
    public boolean temPreferenciaPorEditora(Editora editora) {
        return this.editorasFavoritas.contains(editora);
    }

    /**
     * Marca o onboarding como completo
     */
    public void completarOnboarding() {
        this.onboardingCompleto = true;
        this.updatedAt = LocalDateTime.now();
    }
}
