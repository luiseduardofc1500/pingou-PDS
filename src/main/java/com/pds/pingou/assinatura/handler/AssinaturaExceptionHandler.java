package com.pds.pingou.assinatura.handler;

import com.pds.pingou.assinatura.exception.AssinaturaDuplicadaException;
import com.pds.pingou.assinatura.exception.AssinaturaNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AssinaturaExceptionHandler {
    @ExceptionHandler(AssinaturaNotFoundException.class)
    public ResponseEntity<String> handleAssinaturaNotFoundException(AssinaturaNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(AssinaturaDuplicadaException.class)
    public ResponseEntity<String> handleAssinaturaDuplicadaException(AssinaturaDuplicadaException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
