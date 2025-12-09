package com.subscription.framework.api.exception.handler;

import com.subscription.framework.api.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Handler global de exceções do framework de assinaturas.
 * 
 * <p><b>Hotspot de Extensão:</b> Estenda esta classe para adicionar
 * handlers de exceções específicas do seu domínio.</p>
 * 
 * @author Subscription Framework
 * @version 1.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(PlanNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePlanNotFound(PlanNotFoundException ex) {
        return buildResponse(ex, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(SubscriptionNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleSubscriptionNotFound(SubscriptionNotFoundException ex) {
        return buildResponse(ex, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(PackageNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePackageNotFound(PackageNotFoundException ex) {
        return buildResponse(ex, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(PackageItemNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handlePackageItemNotFound(PackageItemNotFoundException ex) {
        return buildResponse(ex, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleProductNotFound(ProductNotFoundException ex) {
        return buildResponse(ex, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(FeatureNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleFeatureNotFound(FeatureNotFoundException ex) {
        return buildResponse(ex, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCustomerNotFound(CustomerNotFoundException ex) {
        return buildResponse(ex, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(DuplicatePlanNameException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicatePlanName(DuplicatePlanNameException ex) {
        return buildResponse(ex, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(DuplicateSubscriptionException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateSubscription(DuplicateSubscriptionException ex) {
        return buildResponse(ex, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
        return buildResponse(ex.getMessage(), "INVALID_ARGUMENT", HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(SubscriptionFrameworkException.class)
    public ResponseEntity<Map<String, Object>> handleFrameworkException(SubscriptionFrameworkException ex) {
        return buildResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    protected ResponseEntity<Map<String, Object>> buildResponse(
            SubscriptionFrameworkException ex, HttpStatus status) {
        return buildResponse(ex.getMessage(), ex.getErrorCode(), status);
    }
    
    protected ResponseEntity<Map<String, Object>> buildResponse(
            String message, String errorCode, HttpStatus status) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("errorCode", errorCode);
        body.put("message", message);
        
        return new ResponseEntity<>(body, status);
    }
}
