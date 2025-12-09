package com.subscription.framework.core.service;

import com.subscription.framework.core.contract.ProductService;
import com.subscription.framework.core.domain.Product;
import com.subscription.framework.core.repository.ProductRepository;
import com.subscription.framework.api.exception.ProductNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementação base abstrata do serviço de produtos.
 * 
 * <p><b>Hotspot de Extensão:</b> Estenda esta classe para criar
 * um serviço específico do seu tipo de produto. Você DEVE fornecer
 * as implementações dos métodos de mapeamento (toDTO, toEntity).</p>
 * 
 * @param <T> Tipo do produto (deve estender Product)
 * @param <D> Tipo do DTO de resposta
 * @param <R> Tipo do DTO de requisição
 * 
 * @author Subscription Framework
 * @version 1.0
 */
public abstract class AbstractProductService<T extends Product, D, R> implements ProductService<T, D, R> {
    
    protected final ProductRepository<T> productRepository;
    
    protected AbstractProductService(ProductRepository<T> productRepository) {
        this.productRepository = productRepository;
    }
    
    @Override
    public List<D> findAll() {
        return productRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }
    
    @Override
    public List<D> findAllActive() {
        return productRepository.findByActiveTrue().stream()
                .map(this::toDTO)
                .toList();
    }
    
    @Override
    public Optional<D> findById(Long id) {
        return productRepository.findById(id)
                .map(this::toDTO);
    }
    
    @Override
    @Transactional
    public D create(R request) {
        validateRequest(request);
        beforeCreate(request);
        
        T entity = toEntity(request);
        T saved = productRepository.save(entity);
        
        afterCreate(saved);
        return toDTO(saved);
    }
    
    @Override
    @Transactional
    public D update(Long id, R request) {
        T existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        
        validateRequest(request);
        beforeUpdate(existing, request);
        
        updateEntity(existing, request);
        T saved = productRepository.save(existing);
        
        afterUpdate(saved);
        return toDTO(saved);
    }
    
    @Override
    @Transactional
    public void deactivate(Long id) {
        T product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        
        product.setActive(false);
        productRepository.save(product);
    }
    
    @Override
    @Transactional
    public void activate(Long id) {
        T product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        
        product.setActive(true);
        productRepository.save(product);
    }
    
    @Override
    public List<D> findByCategory(String category) {
        return productRepository.findByCategoryAndActiveTrue(category).stream()
                .map(this::toDTO)
                .toList();
    }
    
    @Override
    public boolean canAddToPackage(Long productId) {
        return productRepository.findById(productId)
                .map(Product::isValidForPackage)
                .orElse(false);
    }
    
    // ========== Métodos abstratos para mapeamento ==========
    
    /**
     * Converte uma entidade de produto para DTO de resposta.
     * DEVE ser implementado pela subclasse.
     */
    protected abstract D toDTO(T entity);
    
    /**
     * Converte um DTO de requisição para entidade de produto.
     * DEVE ser implementado pela subclasse.
     */
    protected abstract T toEntity(R request);
    
    /**
     * Atualiza uma entidade existente com os dados do DTO de requisição.
     * DEVE ser implementado pela subclasse.
     */
    protected abstract void updateEntity(T entity, R request);
    
    // ========== Hooks para extensão ==========
    
    /**
     * Valida os dados da requisição.
     * Pode ser sobrescrito para adicionar validações customizadas.
     */
    protected void validateRequest(R request) {
        // Default: nenhuma validação
    }
    
    /**
     * Hook chamado antes de criar um produto.
     */
    protected void beforeCreate(R request) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado após criar um produto.
     */
    protected void afterCreate(T product) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado antes de atualizar um produto.
     */
    protected void beforeUpdate(T existing, R request) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado após atualizar um produto.
     */
    protected void afterUpdate(T product) {
        // Default: nenhuma ação
    }
}
