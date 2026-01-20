package com.argentafact.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "servicios")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicio;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @Column(nullable = false)
    private BigDecimal precio;

    // Relaciones 
    
    @ManyToOne
    @JoinColumn(name = "id_alicuota")
    private Alicuota alicuota;

    // Getters y Setters

    public Long getId() {
        return idServicio;
    }

    public void setId(Long id) {
        this.idServicio = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "servicios")
public class Servicio {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idServicio;
    private String nombreServicio;
    private String descripcion;
    private BigDecimal precio;

    public Servicio() {
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombreServicio() {
        return nombreServicio;
    }

    public void setNombreServicio(String nombreServicio) {
        this.nombreServicio = nombreServicio;
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

}
    @Override
    public String toString() {
        return "Servicio [idServicio=" + idServicio + ", nombreServicio=" + nombreServicio + ", descripcion="
                + descripcion + ", precio=" + precio + "]";
    }

}
