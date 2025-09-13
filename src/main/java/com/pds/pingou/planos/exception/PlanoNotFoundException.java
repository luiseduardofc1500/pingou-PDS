package com.pds.pingou.planos.exception;

public class PlanoNotFoundException extends RuntimeException {
    public PlanoNotFoundException(Long id) {
        super("Plano não encontrado com id: " + id);
    }
}

