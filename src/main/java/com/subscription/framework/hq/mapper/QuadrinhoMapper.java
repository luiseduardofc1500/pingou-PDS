package com.subscription.framework.hq.mapper;

import com.subscription.framework.hq.domain.Quadrinho;
import com.subscription.framework.hq.dto.QuadrinhoRequestDTO;
import com.subscription.framework.hq.dto.QuadrinhoResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class QuadrinhoMapper {

    public QuadrinhoResponseDTO toDTO(Quadrinho quadrinho) {
        if (quadrinho == null) {
            return null;
        }

        QuadrinhoResponseDTO dto = new QuadrinhoResponseDTO();
        dto.setId(quadrinho.getId());
        dto.setName(quadrinho.getName());
        dto.setDescription(quadrinho.getDescription());
        dto.setPrice(quadrinho.getPrice());
        dto.setImageUrl(quadrinho.getImageUrl());
        dto.setSku(quadrinho.getSku());
        dto.setActive(quadrinho.getActive());
        dto.setEditora(quadrinho.getEditora());
        dto.setTipoHQ(quadrinho.getTipoHQ());
        dto.setPontosGanho(quadrinho.getPontosGanho());
        dto.setEdicaoColecionador(quadrinho.getEdicaoColecionador());
        dto.setNumeroEdicao(quadrinho.getNumeroEdicao());
        dto.setAutor(quadrinho.getAutor());
        dto.setIlustrador(quadrinho.getIlustrador());
        dto.setDataPublicacao(quadrinho.getDataPublicacao());
        dto.setIsbn(quadrinho.getIsbn());
        dto.setNumeroPaginas(quadrinho.getNumeroPaginas());
        dto.setCategorias(quadrinho.getCategorias());
        dto.setSerie(quadrinho.getSerie());
        dto.setEstoque(quadrinho.getEstoque());

        return dto;
    }

    public Quadrinho toEntity(QuadrinhoRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Quadrinho quadrinho = new Quadrinho();
        quadrinho.setName(dto.getName());
        quadrinho.setDescription(dto.getDescription());
        quadrinho.setPrice(dto.getPrice());
        quadrinho.setImageUrl(dto.getImageUrl());
        quadrinho.setSku(dto.getSku());
        quadrinho.setEditora(dto.getEditora());
        quadrinho.setTipoHQ(dto.getTipoHQ());
        quadrinho.setEdicaoColecionador(dto.getEdicaoColecionador());
        quadrinho.setNumeroEdicao(dto.getNumeroEdicao());
        quadrinho.setAutor(dto.getAutor());
        quadrinho.setIlustrador(dto.getIlustrador());
        quadrinho.setDataPublicacao(dto.getDataPublicacao());
        quadrinho.setIsbn(dto.getIsbn());
        quadrinho.setNumeroPaginas(dto.getNumeroPaginas());
        quadrinho.setSerie(dto.getSerie());
        quadrinho.setEstoque(dto.getEstoque());

        if (dto.getCategorias() != null) {
            dto.getCategorias().forEach(quadrinho::adicionarCategoria);
        }

        return quadrinho;
    }

    public void updateEntity(QuadrinhoRequestDTO dto, Quadrinho quadrinho) {
        if (dto == null || quadrinho == null) {
            return;
        }

        quadrinho.setName(dto.getName());
        quadrinho.setDescription(dto.getDescription());
        quadrinho.setPrice(dto.getPrice());
        quadrinho.setImageUrl(dto.getImageUrl());
        quadrinho.setSku(dto.getSku());
        quadrinho.setEditora(dto.getEditora());
        quadrinho.setTipoHQ(dto.getTipoHQ());
        quadrinho.setEdicaoColecionador(dto.getEdicaoColecionador());
        quadrinho.setNumeroEdicao(dto.getNumeroEdicao());
        quadrinho.setAutor(dto.getAutor());
        quadrinho.setIlustrador(dto.getIlustrador());
        quadrinho.setDataPublicacao(dto.getDataPublicacao());
        quadrinho.setIsbn(dto.getIsbn());
        quadrinho.setNumeroPaginas(dto.getNumeroPaginas());
        quadrinho.setSerie(dto.getSerie());

        // Atualiza categorias
        quadrinho.getCategorias().clear();
        if (dto.getCategorias() != null) {
            dto.getCategorias().forEach(quadrinho::adicionarCategoria);
        }
    }
}
