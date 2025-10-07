package com.pds.pingou.pacote.handler;

import com.pds.pingou.pacote.exception.PacoteNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PacoteExceptionHandler {
    
    @ExceptionHandler(PacoteNotFoundException.class)
    public ResponseEntity<String> handlePacoteNotFoundException(PacoteNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}