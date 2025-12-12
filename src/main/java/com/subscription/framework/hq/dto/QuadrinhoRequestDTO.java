package com.subscription.framework.hq.dto;

import com.subscription.framework.hq.domain.enums.CategoriaHQ;
import com.subscription.framework.hq.domain.enums.Editora;
import com.subscription.framework.hq.domain.enums.TipoHQ;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuadrinhoRequestDTO {
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String name;
    
    @Size(max = 2000, message = "Descrição deve ter no máximo 2000 caracteres")
    private String description;
    
    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que zero")
    private BigDecimal price;
    
    private String imageUrl;
    
    private String sku;
    
    @NotNull(message = "Editora é obrigatória")
    private Editora editora;
    
    @NotNull(message = "Tipo de HQ é obrigatório")
    private TipoHQ tipoHQ;
    
    private Boolean edicaoColecionador = false;
    
    private String numeroEdicao;
    
    private String autor;
    
    private String ilustrador;
    
    private LocalDate dataPublicacao;
    
    private String isbn;
    
    @Min(value = 1, message = "Número de páginas deve ser maior que zero")
    private Integer numeroPaginas;
    
    @NotEmpty(message = "Pelo menos uma categoria deve ser informada")
    private Set<CategoriaHQ> categorias;
    
    private String serie;
    
    @NotNull(message = "Estoque inicial é obrigatório")
    @Min(value = 0, message = "Estoque não pode ser negativo")
    private Integer estoque = 0;
}
