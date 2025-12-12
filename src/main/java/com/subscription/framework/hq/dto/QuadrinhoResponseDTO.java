package com.subscription.framework.hq.dto;

import com.subscription.framework.hq.domain.enums.CategoriaHQ;
import com.subscription.framework.hq.domain.enums.Editora;
import com.subscription.framework.hq.domain.enums.TipoHQ;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuadrinhoResponseDTO {
    
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private String sku;
    private Boolean active;
    private Editora editora;
    private TipoHQ tipoHQ;
    private Integer pontosGanho;
    private Boolean edicaoColecionador;
    private String numeroEdicao;
    private String autor;
    private String ilustrador;
    private LocalDate dataPublicacao;
    private String isbn;
    private Integer numeroPaginas;
    private Set<CategoriaHQ> categorias;
    private String serie;
    private Integer estoque;
}
