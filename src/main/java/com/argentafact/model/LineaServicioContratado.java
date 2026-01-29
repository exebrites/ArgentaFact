package com.argentafact.model;

import java.math.BigDecimal;
 

public class LineaServicioContratado {

    private Long idServicio;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;

    public LineaServicioContratado(){}
    

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
    
   
    public Long getIdServicio() {
        return idServicio;
    }
    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }


    @Override
    public String toString() {
        return "LineaServicioContratado [idServicio=" + idServicio + ", nombre=" + nombre + ", descripcion="
                + descripcion + ", precio=" + precio + "]";
    }
    

    
}
