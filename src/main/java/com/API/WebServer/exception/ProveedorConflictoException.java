package com.API.WebServer.exception;

public class ProveedorConflictoException extends RuntimeException {

    public ProveedorConflictoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
