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
@Table(name = "servicios_contratados")
public class ServicioContratado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_servicio", nullable = false)
    private Servicio servicio;

    private LocalDate fechaAlta;
    private LocalDate fechaBaja;
    private EstadoServicioContratado estado;
    private BigDecimal precioAcordado;

    // periodicidad (mensual, anual)
    public ServicioContratado() {
    }
    public ServicioContratado(Cliente cliente, Servicio servicio) {
        this.cliente = cliente;
        this.servicio = servicio;
        this.fechaAlta = LocalDate.now();
        this.estado = EstadoServicioContratado.ACTIVO;
        this.precioAcordado = servicio.getPrecio();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public LocalDate getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }

    public EstadoServicioContratado getEstado() {
        return estado;
    }

    public void setEstado(EstadoServicioContratado estado) {
        this.estado = estado;
    }

    public BigDecimal getPrecioAcordado() {
        return precioAcordado;
    }

    public void setPrecioAcordado(BigDecimal precioAcordado) {
        this.precioAcordado = precioAcordado;
    }

    @Override
    public String toString() {
        return "ServicioContratado{" +
                "id=" + id +
                ", servicio=" + servicio.getIdServicio() +
                ", cliente=" + cliente.getIdCliente() +
                ", fechaAlta=" + fechaAlta +
                ", fechaBaja=" + fechaBaja +
                ", estado=" + estado +
                ", precioAcordado=" + precioAcordado +
                '}';
    }

}
