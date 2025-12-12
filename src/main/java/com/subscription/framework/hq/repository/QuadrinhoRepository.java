package com.subscription.framework.hq.repository;

import com.subscription.framework.hq.domain.Quadrinho;
import com.subscription.framework.hq.domain.enums.CategoriaHQ;
import com.subscription.framework.hq.domain.enums.Editora;
import com.subscription.framework.hq.domain.enums.TipoHQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface QuadrinhoRepository extends JpaRepository<Quadrinho, Long> {

    Optional<Quadrinho> findByIsbn(String isbn);

    List<Quadrinho> findByEditora(Editora editora);

    List<Quadrinho> findByTipoHQ(TipoHQ tipoHQ);

    List<Quadrinho> findByEdicaoColecionador(Boolean edicaoColecionador);

    List<Quadrinho> findBySerie(String serie);

    @Query("SELECT q FROM Quadrinho q WHERE q.active = true AND q.estoque > 0")
    List<Quadrinho> findAllDisponiveis();

    @Query("SELECT q FROM Quadrinho q WHERE q.editora = :editora AND q.active = true AND q.estoque > 0")
    List<Quadrinho> findDisponiveisByEditora(@Param("editora") Editora editora);

    @Query("SELECT q FROM Quadrinho q WHERE q.tipoHQ = :tipo AND q.active = true AND q.estoque > 0")
    List<Quadrinho> findDisponiveisByTipo(@Param("tipo") TipoHQ tipo);

    @Query("SELECT q FROM Quadrinho q JOIN q.categorias c WHERE c IN :categorias AND q.active = true AND q.estoque > 0")
    List<Quadrinho> findDisponiveisByCategorias(@Param("categorias") Set<CategoriaHQ> categorias);

    @Query("SELECT q FROM Quadrinho q WHERE q.editora IN :editoras AND q.active = true AND q.estoque > 0")
    List<Quadrinho> findDisponiveisByEditoras(@Param("editoras") Set<Editora> editoras);

    @Query("SELECT q FROM Quadrinho q JOIN q.categorias c " +
           "WHERE c IN :categorias AND q.editora IN :editoras " +
           "AND q.tipoHQ = :tipo AND q.active = true AND q.estoque > 0")
    List<Quadrinho> findDisponiveisComFiltros(
        @Param("categorias") Set<CategoriaHQ> categorias,
        @Param("editoras") Set<Editora> editoras,
        @Param("tipo") TipoHQ tipo
    );

    @Query("SELECT q FROM Quadrinho q WHERE q.id NOT IN " +
           "(SELECT h.quadrinho.id FROM UsuarioHistoricoHQ h WHERE h.userId = :userId) " +
           "AND q.active = true AND q.estoque > 0")
    List<Quadrinho> findDisponiveisNaoRecebidosPorUsuario(@Param("userId") Long userId);

    @Query("SELECT q FROM Quadrinho q WHERE q.serie = :serie " +
           "AND q.id NOT IN (SELECT h.quadrinho.id FROM UsuarioHistoricoHQ h WHERE h.userId = :userId) " +
           "AND q.active = true AND q.estoque > 0")
    List<Quadrinho> findDisponiveisPorSerieNaoRecebidos(
        @Param("serie") String serie, 
        @Param("userId") Long userId
    );
}
