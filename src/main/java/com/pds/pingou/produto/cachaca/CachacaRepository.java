package com.pds.pingou.produto.cachaca;

import com.pds.pingou.produto.ProdutoRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CachacaRepository extends ProdutoRepository {
    
    @Query("SELECT c FROM Cachaca c WHERE c.regiao = :regiao AND c.ativo = true")
    List<Cachaca> findByRegiaoAndAtivoTrue(@Param("regiao") String regiao);
    
    @Query("SELECT c FROM Cachaca c WHERE c.tipoCachaca = :tipo AND c.ativo = true")
    List<Cachaca> findByTipoCachacaAndAtivoTrue(@Param("tipo") TipoCachaca tipo);
}