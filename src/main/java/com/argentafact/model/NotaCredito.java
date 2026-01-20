package com.argentafact.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "notas_credito")
public class NotaCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotaCredito;
    
    private LocalDate fechaEmision;
    private BigDecimal monto;
    private String motivo;

    public NotaCredito() {
    }

    public NotaCredito(LocalDate fechaEmision, BigDecimal monto, String motivo) {
        this.fechaEmision = fechaEmision;
        this.monto = monto;
        this.motivo = motivo;
    }

    public Long getIdNotaCredito() {
        return idNotaCredito;
    }

    public void setIdNotaCredito(Long idNotaCredito) {
        this.idNotaCredito = idNotaCredito;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        return "NotaCredito [idNotaCredito=" + idNotaCredito + ", fechaEmision=" + fechaEmision + ", monto=" + monto
                + ", motivo=" + motivo + "]";
    }

}
