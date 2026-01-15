package com.argentafact.model;

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
    private String nombre;

    public Servicio() {
    }

    public Servicio(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
