package com.argentafact.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DetalleDeFacturaFormulario implements Serializable {
    private List<Linea> serviciosSeleccionados = new ArrayList<>();
    private BigDecimal total = BigDecimal.ZERO;

    
    public List<Linea> getServiciosSeleccionados() {
        return serviciosSeleccionados;
    }


    public void setServiciosSeleccionados(List<Linea> serviciosSeleccionados) {
        this.serviciosSeleccionados = serviciosSeleccionados;
    }


    public BigDecimal getTotal() {
        return total;
    }


    public void setTotal(BigDecimal total) {
        this.total = total;
    }


    // Getters, Setters, y métodos para añadir productos
    public void agregarServicio(Linea lineaFactura) {
        this.serviciosSeleccionados.add(lineaFactura);
        this.total = this.total.add(lineaFactura.getPrecio());
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
