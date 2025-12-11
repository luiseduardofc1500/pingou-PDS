package com.subscription.framework.core.repository;

import com.subscription.framework.core.domain.PackageItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PackageItemRepository extends JpaRepository<PackageItem, Long> {
    
    /**
     * Busca itens por pacote.
     */
    List<PackageItem> findByPkgId(Long packageId);
    
    /**
     * Busca itens por produto.
     */
    List<PackageItem> findByProductId(Long productId);
    
    /**
     * Deleta itens por pacote.
     */
    void deleteByPkgId(Long packageId);
}
