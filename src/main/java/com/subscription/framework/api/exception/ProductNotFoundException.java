package com.subscription.framework.api.exception;

/**
 * Exceção lançada quando um produto não é encontrado.
 */
public class ProductNotFoundException extends SubscriptionFrameworkException {
    
    public ProductNotFoundException(Long productId) {
        super("Product not found: " + productId, "PRODUCT_NOT_FOUND");
    }
}
