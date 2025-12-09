package com.subscription.framework.core.contract;

import com.subscription.framework.core.domain.Package;
import com.subscription.framework.core.domain.PackageItem;

import java.util.List;
import java.util.Optional;

/**
 * Contrato para serviços de gerenciamento de pacotes.
 * 
 * @author Subscription Framework
 * @version 1.0
 */
public interface PackageService {
    
    /**
     * Lista todos os pacotes.
     */
    List<Package> findAll();
    
    /**
     * Busca um pacote pelo ID.
     */
    Optional<Package> findById(Long id);
    
    /**
     * Busca pacotes por plano.
     */
    List<Package> findByPlanId(Long planId);
    
    /**
     * Busca pacotes por mês e ano.
     */
    List<Package> findByMonthAndYear(Integer month, Integer year);
    
    /**
     * Busca pacotes para um assinante específico (baseado no plano da assinatura).
     */
    List<Package> findByCustomerId(Long customerId);
    
    /**
     * Cria um novo pacote.
     */
    Package create(Package pkg);
    
    /**
     * Atualiza um pacote existente.
     */
    Package update(Long id, Package pkg);
    
    /**
     * Deleta um pacote.
     */
    void delete(Long id);
    
    /**
     * Adiciona um item ao pacote.
     * 
     * @param packageId ID do pacote
     * @param productId ID do produto
     * @param quantity Quantidade
     * @return Pacote atualizado
     */
    Package addItem(Long packageId, Long productId, Integer quantity);
    
    /**
     * Remove um item do pacote.
     * 
     * @param packageId ID do pacote
     * @param itemId ID do item
     * @return Pacote atualizado
     */
    Package removeItem(Long packageId, Long itemId);
    
    /**
     * Atualiza a quantidade de um item.
     * 
     * @param packageId ID do pacote
     * @param itemId ID do item
     * @param newQuantity Nova quantidade
     * @return Pacote atualizado
     */
    Package updateItemQuantity(Long packageId, Long itemId, Integer newQuantity);
    
    /**
     * Lista os itens de um pacote.
     */
    List<PackageItem> getItems(Long packageId);
    
    /**
     * Registra o envio do pacote para todos os assinantes ativos do plano.
     */
    void registerDeliveryForSubscribers(Long packageId);
}
