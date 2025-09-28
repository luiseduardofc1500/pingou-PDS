package com.pds.pingou.pacote;

import com.pds.pingou.produto.Produto;
import org.springframework.stereotype.Component;

/**
 * Classe utilitária para conversão entre ItemPacote e seus DTOs.
 * 
 * Esta classe fornece métodos estáticos para converter entidades ItemPacote
 * em DTOs de resposta e vice-versa, facilitando a transferência de dados
 * entre as camadas da aplicação.
 * 
 * @author Pingou Team
 * @version 1.0
 * @since 1.0
 */
@Component
public class ItemPacoteMapper {
    
    /**
     * Converte um ItemPacoteRequestDTO em uma entidade ItemPacote.
     * 
     * @param dto DTO contendo os dados do item
     * @param pacote Pacote ao qual o item pertencerá
     * @param produto Produto que será incluído no item
     * @return Entidade ItemPacote preenchida
     */
    public static ItemPacote toEntity(ItemPacoteRequestDTO dto, Pacote pacote, Produto produto) {
        ItemPacote item = new ItemPacote();
        item.setPacote(pacote);
        item.setProduto(produto);
        item.setQuantidade(dto.getQuantidade());
        item.setObservacoes(dto.getObservacoes());
        return item;
    }
    
    /**
     * Converte uma entidade ItemPacote em um ItemPacoteResponseDTO.
     * 
     * @param item Entidade ItemPacote a ser convertida
     * @return DTO de resposta preenchido
     */
    public static ItemPacoteResponseDTO toDTO(ItemPacote item) {
        ItemPacoteResponseDTO dto = new ItemPacoteResponseDTO();
        dto.setId(item.getId());
        dto.setProdutoId(item.getProduto().getId());
        dto.setProdutoNome(item.getProduto().getNome());
        dto.setProdutoPreco(item.getProduto().getPreco());
        dto.setProdutoImagem(item.getProduto().getUrlImagem());
        dto.setQuantidade(item.getQuantidade());
        dto.setObservacoes(item.getObservacoes());
        return dto;
    }
}