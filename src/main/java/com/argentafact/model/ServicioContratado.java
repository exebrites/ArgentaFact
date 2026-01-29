package com.argentafact.model;

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
    @JoinColumn(name="id_cliente", nullable=false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name="id_servicio", nullable=false)
    private Servicio servicio;
    
    public ServicioContratado(){
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

    @Override
    public String toString() {
        return "ServicioContratado{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", servicio=" + servicio +
                '}';
    }
    
}
