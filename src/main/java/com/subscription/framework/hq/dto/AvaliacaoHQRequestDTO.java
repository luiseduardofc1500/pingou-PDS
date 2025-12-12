package com.subscription.framework.hq.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoHQRequestDTO {
    
    @NotNull(message = "ID do histórico é obrigatório")
    private Long historicoId;
    
    @NotNull(message = "Nota é obrigatória")
    @Min(value = 1, message = "Nota deve estar entre 1 e 5")
    @Max(value = 5, message = "Nota deve estar entre 1 e 5")
    private Integer nota;
    
    @Size(max = 1000, message = "Comentário deve ter no máximo 1000 caracteres")
    private String comentario;
}
