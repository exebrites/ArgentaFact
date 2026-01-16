package com.argentafact.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DetalleDeFacturaFormulario implements Serializable {
    private List<String> serviciosSeleccionados = new ArrayList<>();
    private double total;

    
    public List<String> getServiciosSeleccionados() {
        return serviciosSeleccionados;
    }


    public void setServiciosSeleccionados(List<String> serviciosSeleccionados) {
        this.serviciosSeleccionados = serviciosSeleccionados;
    }


    public double getTotal() {
        return total;
    }


    public void setTotal(double total) {
        this.total = total;
    }


    // Getters, Setters, y métodos para añadir productos
    public void agregarServicio(String nombreServicio, double price) {
        this.serviciosSeleccionados.add(nombreServicio);
        this.total += price;
    }
    @Override
    public String toString() {
        return "DetalleDeFacturaFormulario{" +
                "serviciosSeleccionados=" + serviciosSeleccionados +
                ", total=" + total +
                '}';
    }
    // ... otros métodos
}
