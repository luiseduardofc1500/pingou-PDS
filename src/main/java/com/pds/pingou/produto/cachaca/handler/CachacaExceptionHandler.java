package com.pds.pingou.produto.cachaca.handler;

import com.pds.pingou.produto.cachaca.exception.CachacaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CachacaExceptionHandler {
    
    @ExceptionHandler(CachacaNotFoundException.class)
    public ResponseEntity<String> handleCachacaNotFoundException(CachacaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}