package com.API.WebServer.dto;

import java.math.BigDecimal;

public class ProductoResponse {

    private Integer idProducto;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private Integer idCategoria;

    public ProductoResponse() {
    }

    public ProductoResponse(Integer idProducto, String nombre, String descripcion,
                            BigDecimal precio, Integer stock, Integer idCategoria) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.idCategoria = idCategoria;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public Integer getStock() {
        return stock;
    }

    public Integer getIdCategoria() {
        return idCategoria;
    }

    public static class Builder {
        private Integer idProducto;
        private String nombre;
        private String descripcion;
        private BigDecimal precio;
        private Integer stock;
        private Integer idCategoria;

        public Builder idProducto(Integer idProducto) {
            this.idProducto = idProducto;
            return this;
        }

        public Builder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder descripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public Builder precio(BigDecimal precio) {
            this.precio = precio;
            return this;
        }

        public Builder stock(Integer stock) {
            this.stock = stock;
            return this;
        }

        public Builder idCategoria(Integer idCategoria) {
            this.idCategoria = idCategoria;
            return this;
        }

        public ProductoResponse build() {
            return new ProductoResponse(idProducto, nombre, descripcion, precio, stock, idCategoria);
        }
    }
}
