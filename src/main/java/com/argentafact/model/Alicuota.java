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
    private BigDecimal porcentaje; 

    public Alicuota() {
    }

    public Alicuota(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    // Getters y Setters

    public Long getIdAlicuota() {
        return idAlicuota;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }
}
