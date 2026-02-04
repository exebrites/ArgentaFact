package com.argentafact.model;

import java.math.BigDecimal;

public class Linea {

    private int id;
    private int cantidad;
    private int idFactura;
    private Long idServicio;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;

    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public BigDecimal getPrecio() {
        return precio;
    }
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    public Linea () {}
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public int getIdFactura() {
        return idFactura;
    }
    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }
   
    public Long getIdServicio() {
        return idServicio;
    }
    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }
    @Override
    public String toString() {
        return "Linea [id=" + id + ", cantidad=" + cantidad + ", idFactura=" + idFactura + ", idServicio=" + idServicio
                + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio + "]";
    }

    
}
