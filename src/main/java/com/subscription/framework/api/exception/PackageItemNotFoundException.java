package com.subscription.framework.api.exception;

/**
 * Exceção lançada quando um item de pacote não é encontrado.
 */
public class PackageItemNotFoundException extends SubscriptionFrameworkException {
    
    public PackageItemNotFoundException(Long itemId) {
        super("Package item not found: " + itemId, "PACKAGE_ITEM_NOT_FOUND");
    }
}
