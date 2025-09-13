package com.pds.pingou.planos.handler;

import com.pds.pingou.planos.exception.PlanoNomeDuplicadoException;
import com.pds.pingou.planos.exception.PlanoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PlanoExceptionHandler {
    @ExceptionHandler(PlanoNotFoundException.class)
    public ResponseEntity<String> handlePlanoNotFoundException(PlanoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(PlanoNomeDuplicadoException.class)
    public ResponseEntity<String> handlePlanoNomeDuplicadoException(PlanoNomeDuplicadoException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }
}
