package com.argentafact.model;

public class ServicioContratado {
    private Long id;
    private Cliente cliente;
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
