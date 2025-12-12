package com.subscription.framework.hq.dto;

import com.subscription.framework.hq.domain.enums.CategoriaHQ;
import com.subscription.framework.hq.domain.enums.Editora;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnboardingRequestDTO {
    
    @NotEmpty(message = "Selecione pelo menos uma categoria favorita")
    private Set<CategoriaHQ> categoriasFavoritas;
    
    @NotEmpty(message = "Selecione pelo menos uma editora favorita")
    private Set<Editora> editorasFavoritas;
    
    @NotNull(message = "Defina sua preferência por HQs clássicas")
    @Min(value = 0, message = "Preferência deve estar entre 0 e 100")
    @Max(value = 100, message = "Preferência deve estar entre 0 e 100")
    private Integer preferenciaClassicas;
    
    private Boolean interesseEdicoesColecionador = false;
    
    private Set<String> seriesAcompanhadas;
}
