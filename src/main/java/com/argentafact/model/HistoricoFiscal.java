package com.argentafact.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "historico_fiscal")
public class HistoricoFiscal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idHistorico;

    @Column(nullable = false)
    private LocalDateTime fechaOperacion;

    @ManyToOne
    @JoinColumn(name = "idEmpleado")
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String tipoOperacion;

    protected HistoricoFiscal() {}

    public HistoricoFiscal(LocalDateTime fechaOperacion,
                           Empleado empleado,
                           Cliente cliente,
                           String tipoOperacion,
                           String descripcion) {
        this.fechaOperacion = fechaOperacion;
        this.empleado = empleado;
        this.cliente = cliente;
        this.tipoOperacion = tipoOperacion;
        this.descripcion = descripcion;
    }

    // Getters
    public Long getIdHistorico() {
        return idHistorico;
    }

    public LocalDateTime getFechaOperacion() {
        return fechaOperacion;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getTipoOperacion() {
        return tipoOperacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    //Setters (los únicos que podrían modificarse a priori)

    public void setTipoOperacion(String tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "HistoricoFiscal{" +
                "idHistorico=" + idHistorico +
                ", fechaOperacion=" + fechaOperacion +
                ", tipoOperacion='" + tipoOperacion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", cliente=" + (cliente != null ? cliente.getIdCliente() : "null") +
                ", empleado=" + (empleado != null ? empleado.getIdEmpleado() : "null") +
                '}';
    }
}
