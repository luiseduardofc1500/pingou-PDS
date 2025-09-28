package com.pds.pingou.pacote;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemPacoteRepository extends JpaRepository<ItemPacote, Long> {
    
    List<ItemPacote> findByPacote(Pacote pacote);
    
    List<ItemPacote> findByProdutoId(Long produtoId);
}