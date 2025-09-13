package com.pds.pingou.planos.exception;

public class PlanoNomeDuplicadoException extends RuntimeException {
    public PlanoNomeDuplicadoException(String nome) {
        super("Já existe um plano com o nome: " + nome);
    }
}
