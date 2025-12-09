package com.subscription.framework.api.exception;

/**
 * Exceção lançada quando um pacote não é encontrado.
 */
public class PackageNotFoundException extends SubscriptionFrameworkException {
    
    public PackageNotFoundException(Long packageId) {
        super("Package not found: " + packageId, "PACKAGE_NOT_FOUND");
    }
}
