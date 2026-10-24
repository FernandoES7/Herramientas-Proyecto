package com.API.WebServer.exception;

public class ProveedorNoEncontradoException extends RuntimeException {

    public ProveedorNoEncontradoException(Integer idProveedor) {
        super("No existe el proveedor con id " + idProveedor);
    }

    public ProveedorNoEncontradoException(String ruc) {
        super("No existe un proveedor con RUC " + ruc);
    }
}
