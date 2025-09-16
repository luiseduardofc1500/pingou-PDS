package com.pds.pingou.security.exception;

public class UserDuplicatedException extends  RuntimeException {
    public UserDuplicatedException(String nome) {
        super("User with name " + nome + " already exists.");
    }
}
