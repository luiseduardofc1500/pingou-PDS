package com.pds.pingou.AI.service;

import com.pds.pingou.AI.dto.GeneratePackageRequestDTO;
import com.pds.pingou.AI.dto.PackageSuggestionResponseDTO;
import com.pds.pingou.pacote.ItemPacote;
import com.pds.pingou.pacote.ItemPacoteRequestDTO;
import com.pds.pingou.pacote.Pacote;
import com.pds.pingou.pacote.PacoteRepository;
import com.pds.pingou.planos.Plano;
import com.pds.pingou.planos.PlanoRepository;
import com.pds.pingou.planos.exception.PlanoNotFoundException;
import com.pds.pingou.produto.Produto;
import com.pds.pingou.produto.ProdutoRepository;
import com.pds.pingou.produto.cachaca.Cachaca;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AIPackageGeneratorService {

    private final PlanoRepository planoRepository;
    private final PacoteRepository pacoteRepository;
    private final ProdutoRepository produtoRepository;

    public AIPackageGeneratorService(PlanoRepository planoRepository,
                                     PacoteRepository pacoteRepository,
                                     ProdutoRepository produtoRepository) {
        this.planoRepository = planoRepository;
        this.pacoteRepository = pacoteRepository;
        this.produtoRepository = produtoRepository;
    }

    public PackageSuggestionResponseDTO suggest(GeneratePackageRequestDTO req) {
        Long planoId = req.getPlanoId();
        Plano plano = planoRepository.findById(planoId)
                .orElseThrow(() -> new PlanoNotFoundException(planoId));

        int limite = Optional.ofNullable(req.getTamanho())
                .filter(v -> v > 0)
                .map(v -> Math.min(v, Optional.ofNullable(plano.getMaxProdutosPorMes()).orElse(1)))
                .orElse(Optional.ofNullable(plano.getMaxProdutosPorMes()).orElse(1));

        // Lista de cachaças ativas (subtipo) ordenadas por id para estabilidade
        List<Long> cachacasAtivas = produtoRepository.findByAtivoTrue().stream()
                .filter(p -> p instanceof Cachaca)
                .map(Produto::getId)
                .sorted()
                .collect(Collectors.toList());
        if (cachacasAtivas.isEmpty()) {
            return new PackageSuggestionResponseDTO(planoId, limite, List.of());
        }

        // Conjuntos de combinações já usadas neste plano (como string de ids ordenados)
        Set<String> combinacoesUsadas = pacoteRepository.findByPlano(plano).stream()
                .map(Pacote::getItens)
                .filter(Objects::nonNull)
                .map(itens -> itens.stream()
                        .map(ItemPacote::getProduto)
                        .filter(Objects::nonNull)
                        .map(p -> p.getId())
                        .sorted()
                        .map(String::valueOf)
                        .collect(Collectors.joining("-")))
                .collect(Collectors.toSet());

        // Estratégia simples: usar um deslocamento baseado na quantidade de pacotes existentes
        int n = cachacasAtivas.size();
        int offsetBase = Math.floorMod(combinacoesUsadas.size(), Math.max(1, n));

        // Tentar até n deslocamentos diferentes para evitar repetir combinações
        for (int attempt = 0; attempt < n; attempt++) {
            int offset = (offsetBase + attempt) % n;
            List<Long> selecionados = new ArrayList<>(limite);
            for (int i = 0; i < n && selecionados.size() < limite; i++) {
                Long id = cachacasAtivas.get((offset + i) % n);
                if (!selecionados.contains(id)) selecionados.add(id);
            }

            List<Long> ordenados = selecionados.stream().sorted().toList();
            String assinatura = ordenados.stream().map(String::valueOf).collect(Collectors.joining("-"));
            if (!combinacoesUsadas.contains(assinatura)) {
                List<ItemPacoteRequestDTO> itens = selecionados.stream()
                        .map(pid -> {
                            ItemPacoteRequestDTO dto = new ItemPacoteRequestDTO();
                            dto.setProdutoId(pid);
                            dto.setQuantidade(1);
                            dto.setObservacoes("");
                            return dto;
                        })
                        .collect(Collectors.toList());
                return new PackageSuggestionResponseDTO(planoId, limite, itens);
            }
        }

        // Fallback: retornar a primeira combinação possível (mesmo que já usada)
        List<ItemPacoteRequestDTO> itens = cachacasAtivas.stream()
                .limit(limite)
                .map(id -> {
                    ItemPacoteRequestDTO dto = new ItemPacoteRequestDTO();
                    dto.setProdutoId(id);
                    dto.setQuantidade(1);
                    dto.setObservacoes("");
                    return dto;
                })
                .collect(Collectors.toList());

        return new PackageSuggestionResponseDTO(planoId, limite, itens);
    }
}


