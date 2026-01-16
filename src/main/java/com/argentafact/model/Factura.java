package com.argentafact.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "facturas")
public class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFactura;
    private String numeroFactura;
    private LocalDate fechaEmision;
    private TipoFactura tipoFactura;
    private BigDecimal total;
    private EstadoFactura estado;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado empleado;

    public Factura() {
    }

    public Factura(String numeroFactura, LocalDate fechaEmision, TipoFactura tipoFactura, BigDecimal total,
            EstadoFactura estado) {
        this.numeroFactura = numeroFactura;
        this.fechaEmision = fechaEmision;
        this.tipoFactura = tipoFactura;
        this.total = total;
        this.estado = estado;
    }

    public Long getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Long idFactura) {
        this.idFactura = idFactura;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public TipoFactura getTipoFactura() {
        return tipoFactura;
    }

    public void setTipoFactura(TipoFactura tipoFactura) {
        this.tipoFactura = tipoFactura;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public EstadoFactura getEstado() {
        return estado;
    }

    public void setEstado(EstadoFactura estado) {
        this.estado = estado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

}
