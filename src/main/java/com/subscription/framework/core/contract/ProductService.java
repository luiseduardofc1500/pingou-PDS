package com.subscription.framework.core.contract;

import com.subscription.framework.core.domain.Product;

import java.util.List;
import java.util.Optional;

/**
 * Contrato para serviços de gerenciamento de produtos.
 * 
 * <p><b>Hotspot de Extensão:</b> Implemente esta interface para criar
 * um serviço de produtos específico do seu domínio.</p>
 * 
 * @param <T> Tipo do produto (deve estender Product)
 * @param <D> Tipo do DTO de resposta
 * @param <R> Tipo do DTO de requisição
 * 
 * @author Subscription Framework
 * @version 1.0
 */
public interface ProductService<T extends Product, D, R> {
    
    /**
     * Lista todos os produtos.
     */
    List<D> findAll();
    
    /**
     * Lista todos os produtos ativos.
     */
    List<D> findAllActive();
    
    /**
     * Busca um produto pelo ID.
     */
    Optional<D> findById(Long id);
    
    /**
     * Cria um novo produto.
     */
    D create(R request);
    
    /**
     * Atualiza um produto existente.
     */
    D update(Long id, R request);
    
    /**
     * Desativa um produto (soft delete).
     */
    void deactivate(Long id);
    
    /**
     * Ativa um produto.
     */
    void activate(Long id);
    
    /**
     * Busca produtos por categoria.
     */
    List<D> findByCategory(String category);
    
    /**
     * Verifica se um produto pode ser adicionado a um pacote.
     */
    boolean canAddToPackage(Long productId);
}
