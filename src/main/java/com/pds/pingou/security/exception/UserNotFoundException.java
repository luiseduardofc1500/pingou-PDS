package com.pds.pingou.security.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String nome) {
        super(" User not found:" + nome);
    }
}
