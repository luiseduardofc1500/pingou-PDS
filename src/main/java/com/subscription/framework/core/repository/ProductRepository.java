package com.subscription.framework.core.repository;

import com.subscription.framework.core.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Repositório base para produtos.
 * 
 * <p><b>Hotspot de Extensão:</b> Estenda esta interface para criar
 * um repositório específico do seu tipo de produto.</p>
 * 
 * @param <T> Tipo do produto (deve estender Product)
 * 
 * @author Subscription Framework
 * @version 1.0
 */
@NoRepositoryBean
public interface ProductRepository<T extends Product> extends JpaRepository<T, Long> {
    
    /**
     * Busca produtos ativos.
     */
    List<T> findByActiveTrue();
    
    /**
     * Busca produtos por categoria.
     */
    List<T> findByCategoryAndActiveTrue(String category);
    
    /**
     * Busca produto por SKU.
     */
    T findBySku(String sku);
    
    /**
     * Busca produtos por nome (parcial, case-insensitive).
     */
    @Query("SELECT p FROM #{#entityName} p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) AND p.active = true")
    List<T> searchByName(String name);
}
