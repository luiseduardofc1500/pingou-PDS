package com.subscription.framework.core.service;

import com.subscription.framework.core.contract.PackageService;
import com.subscription.framework.core.domain.*;
import com.subscription.framework.core.domain.Package;
import com.subscription.framework.core.domain.enums.SubscriptionStatus;
import com.subscription.framework.core.repository.*;
import com.subscription.framework.api.exception.PackageNotFoundException;
import com.subscription.framework.api.exception.PlanNotFoundException;
import com.subscription.framework.api.exception.ProductNotFoundException;
import com.subscription.framework.api.exception.PackageItemNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementação base do serviço de pacotes.
 * 
 * <p><b>Hotspot de Extensão:</b> Estenda esta classe para customizar
 * o comportamento de pacotes no seu domínio.</p>
 * 
 * @author Subscription Framework
 * @version 1.0
 */
public abstract class AbstractPackageService implements PackageService {
    
    protected final PackageRepository packageRepository;
    protected final PackageItemRepository packageItemRepository;
    protected final PlanRepository planRepository;
    protected final SubscriptionRepository subscriptionRepository;
    protected final DeliveryRecordRepository deliveryRecordRepository;
    
    protected AbstractPackageService(PackageRepository packageRepository,
                                     PackageItemRepository packageItemRepository,
                                     PlanRepository planRepository,
                                     SubscriptionRepository subscriptionRepository,
                                     DeliveryRecordRepository deliveryRecordRepository) {
        this.packageRepository = packageRepository;
        this.packageItemRepository = packageItemRepository;
        this.planRepository = planRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.deliveryRecordRepository = deliveryRecordRepository;
    }
    
    @Override
    public List<Package> findAll() {
        return packageRepository.findAll();
    }
    
    @Override
    public Optional<Package> findById(Long id) {
        return packageRepository.findById(id);
    }
    
    @Override
    public List<Package> findByPlanId(Long planId) {
        return packageRepository.findByPlanId(planId);
    }
    
    @Override
    public List<Package> findByMonthAndYear(Integer month, Integer year) {
        return packageRepository.findByMonthAndYear(month, year);
    }
    
    @Override
    public List<Package> findByCustomerId(Long customerId) {
        Optional<Subscription> subscription = subscriptionRepository
                .findByCustomerIdAndStatus(customerId, SubscriptionStatus.ACTIVE);
        
        if (subscription.isEmpty()) {
            return List.of();
        }
        
        return packageRepository.findByPlanId(subscription.get().getPlan().getId());
    }
    
    @Override
    @Transactional
    public Package create(Package pkg) {
        validatePackage(pkg);
        beforeCreate(pkg);
        
        Package saved = packageRepository.save(pkg);
        
        afterCreate(saved);
        return saved;
    }
    
    @Override
    @Transactional
    public Package update(Long id, Package pkg) {
        Package existing = findOrThrow(id);
        
        validatePackage(pkg);
        beforeUpdate(existing, pkg);
        
        existing.setName(pkg.getName());
        existing.setDescription(pkg.getDescription());
        existing.setDeliveryDate(pkg.getDeliveryDate());
        existing.setMonth(pkg.getMonth());
        existing.setYear(pkg.getYear());
        existing.setTheme(pkg.getTheme());
        
        Package saved = packageRepository.save(existing);
        afterUpdate(saved);
        
        return saved;
    }
    
    @Override
    @Transactional
    public void delete(Long id) {
        Package pkg = findOrThrow(id);
        beforeDelete(pkg);
        packageRepository.delete(pkg);
        afterDelete(id);
    }
    
    @Override
    @Transactional
    public Package addItem(Long packageId, Long productId, Integer quantity) {
        Package pkg = findOrThrow(packageId);
        Product product = findProductOrThrow(productId);
        
        // Validar se pode adicionar mais itens
        if (!pkg.canAddItems(quantity)) {
            throw new IllegalArgumentException(
                    "Cannot add items. Maximum items per delivery exceeded: " + 
                    pkg.getPlan().getMaxItemsPerDelivery());
        }
        
        // Validar se o produto é válido para pacote
        if (!product.isValidForPackage()) {
            throw new IllegalArgumentException("Product is not valid for package: " + productId);
        }
        
        validateItemQuantity(quantity);
        
        PackageItem item = new PackageItem(pkg, product, quantity);
        pkg.addItem(item);
        
        return packageRepository.save(pkg);
    }
    
    @Override
    @Transactional
    public Package removeItem(Long packageId, Long itemId) {
        Package pkg = findOrThrow(packageId);
        PackageItem item = packageItemRepository.findById(itemId)
                .orElseThrow(() -> new PackageItemNotFoundException(itemId));
        
        if (!item.getPkg().getId().equals(packageId)) {
            throw new IllegalArgumentException("Item does not belong to this package");
        }
        
        pkg.removeItem(item);
        packageItemRepository.delete(item);
        
        return packageRepository.save(pkg);
    }
    
    @Override
    @Transactional
    public Package updateItemQuantity(Long packageId, Long itemId, Integer newQuantity) {
        Package pkg = findOrThrow(packageId);
        PackageItem item = packageItemRepository.findById(itemId)
                .orElseThrow(() -> new PackageItemNotFoundException(itemId));
        
        if (!item.getPkg().getId().equals(packageId)) {
            throw new IllegalArgumentException("Item does not belong to this package");
        }
        
        validateItemQuantity(newQuantity);
        
        // Calcular total sem o item atual
        int totalWithoutItem = pkg.getTotalItemCount() - item.getQuantity();
        if (totalWithoutItem + newQuantity > pkg.getPlan().getMaxItemsPerDelivery()) {
            throw new IllegalArgumentException(
                    "Cannot update quantity. Maximum items per delivery exceeded: " + 
                    pkg.getPlan().getMaxItemsPerDelivery());
        }
        
        item.updateQuantity(newQuantity);
        packageItemRepository.save(item);
        pkg.recalculateTotalValue();
        
        return packageRepository.save(pkg);
    }
    
    @Override
    public List<PackageItem> getItems(Long packageId) {
        findOrThrow(packageId); // Validar se existe
        return packageItemRepository.findByPkgId(packageId);
    }
    
    @Override
    @Transactional
    public void registerDeliveryForSubscribers(Long packageId) {
        Package pkg = findOrThrow(packageId);
        
        List<Subscription> activeSubscriptions = subscriptionRepository
                .findByPlanIdAndStatus(pkg.getPlan().getId(), SubscriptionStatus.ACTIVE);
        
        if (activeSubscriptions.isEmpty() || pkg.getItems().isEmpty()) {
            return;
        }
        
        for (Subscription subscription : activeSubscriptions) {
            for (PackageItem item : pkg.getItems()) {
                DeliveryRecord record = new DeliveryRecord(
                        subscription.getCustomerId(),
                        pkg,
                        item.getProduct(),
                        item.getQuantity()
                );
                deliveryRecordRepository.save(record);
                onDeliveryRecordCreated(record);
            }
        }
    }
    
    // ========== Métodos auxiliares ==========
    
    protected Package findOrThrow(Long packageId) {
        return packageRepository.findById(packageId)
                .orElseThrow(() -> new PackageNotFoundException(packageId));
    }
    
    /**
     * Busca um produto pelo ID. Deve ser implementado pela aplicação concreta
     * pois o repositório de produtos é genérico.
     */
    protected abstract Product findProductOrThrow(Long productId);
    
    protected void validateItemQuantity(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
    }
    
    protected void validatePackage(Package pkg) {
        if (pkg.getName() == null || pkg.getName().isBlank()) {
            throw new IllegalArgumentException("Package name is required");
        }
        if (pkg.getDeliveryDate() == null) {
            throw new IllegalArgumentException("Delivery date is required");
        }
        if (pkg.getMonth() == null || pkg.getMonth() < 1 || pkg.getMonth() > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        if (pkg.getYear() == null) {
            throw new IllegalArgumentException("Year is required");
        }
        if (pkg.getPlan() == null) {
            throw new IllegalArgumentException("Plan is required");
        }
    }
    
    // ========== Hooks para extensão ==========
    
    /**
     * Hook chamado antes de criar um pacote.
     */
    protected void beforeCreate(Package pkg) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado após criar um pacote.
     */
    protected void afterCreate(Package pkg) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado antes de atualizar um pacote.
     */
    protected void beforeUpdate(Package existing, Package updated) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado após atualizar um pacote.
     */
    protected void afterUpdate(Package pkg) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado antes de deletar um pacote.
     */
    protected void beforeDelete(Package pkg) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado após deletar um pacote.
     */
    protected void afterDelete(Long packageId) {
        // Default: nenhuma ação
    }
    
    /**
     * Hook chamado quando um registro de entrega é criado.
     */
    protected void onDeliveryRecordCreated(DeliveryRecord record) {
        // Default: nenhuma ação
    }
}
