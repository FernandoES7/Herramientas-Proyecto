package com.API.WebServer.exception;

public class RucDuplicadoException extends RuntimeException {

    public RucDuplicadoException(String ruc) {
        super("El RUC " + ruc + " ya se encuentra registrado");
    }
}
