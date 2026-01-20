package com.argentafact.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "alicuotas")
public class Alicuota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlicuota;

    @Column(nullable = false)
    private String nombre; 

    @Column(nullable = false)
    private BigDecimal porcentaje; 

    public Alicuota() {
    }

    public Alicuota(String nombre, BigDecimal porcentaje) {
        this.nombre = nombre;
        this.porcentaje = porcentaje;
    }

    // Getters y Setters

    public Long getIdAlicuota() {
        return idAlicuota;
    }

    public void setIdAliquota(Long idAlicuota) {
        this.idAlicuota = idAlicuota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }
}
