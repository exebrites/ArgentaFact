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

    @Override
    public String toString() {
        return "DetalleDeFacturaFormulario{" +
                "serviciosSeleccionados=" + serviciosSeleccionados +
                ", total=" + total +
                '}';
    }

    public void eliminarServicio(Long id) {
        Linea servicioAEliminar = null;
        for (Linea servicio : serviciosSeleccionados) {
            if (servicio.getIdServicio() == id) {
                servicioAEliminar = servicio;
                break;
            }
        }
        if (servicioAEliminar != null) {
            serviciosSeleccionados.remove(servicioAEliminar);
            total = total.subtract(servicioAEliminar.getPrecio());
        }
    }

    public boolean estaSeleccionado(Long id) {

        return false;
    }

    public void agregarServicio(Linea lineaFactura) {
        this.serviciosSeleccionados.add(lineaFactura);
        this.total = this.total.add(lineaFactura.getPrecio());
    }

    public void limpiar() {
        int cantidad = serviciosSeleccionados.size();
        serviciosSeleccionados.clear();
        System.out.printf("Limpiados %d servicios%n", cantidad);
    }
}
