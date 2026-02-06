package com.argentafact.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.argentafact.utils.DateFormatterUtil;
import jakarta.persistence.*;

@Entity
@Table(name = "notas_debito")
public class NotaDebito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idNotaDebito;

    private LocalDate fechaEmision;
    private BigDecimal monto;
    private String motivo;

    @ManyToOne
    @JoinColumn(name = "id_factura", nullable = false)
    private Factura factura;

    public NotaDebito() {}

    public NotaDebito(LocalDate fechaEmision, BigDecimal monto, String motivo) {
        this.fechaEmision = fechaEmision == null ? LocalDate.now() : fechaEmision;
        this.monto = monto;
        this.motivo = motivo;
    }

    public Long getIdNotaDebito() {
        return idNotaDebito;
    }

    public void setIdNotaDebito(Long idNotaDebito) {
        this.idNotaDebito = idNotaDebito;
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

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public String getFechaFormateada() {
        return DateFormatterUtil.format(this.fechaEmision);
    }

    @Override
    public String toString() {
        return "NotaDebito [idNotaDebito=" + idNotaDebito + ", fechaEmision=" + fechaEmision +
                ", monto=" + monto + ", motivo=" + motivo + ", factura=" + factura.getIdFactura() + "]";
    }
}
