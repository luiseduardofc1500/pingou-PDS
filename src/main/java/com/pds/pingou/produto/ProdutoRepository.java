package com.pds.pingou.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
    List<Produto> findByAtivoTrue();
    
    @Query("SELECT p FROM Produto p WHERE p.nome LIKE %:nome% AND p.ativo = true")
    List<Produto> findByNomeContainingAndAtivoTrue(@Param("nome") String nome);
}