package com.argentafact.model;

public enum TipoPago {

    EFECTIVO("Efectivo"),
    TRANSFERENCIA("Transferencia"),
    TARJETA_CREDITO("Tarjeta Crédito"),
    TARJETA_DEBITO("Tarjeta Débito"),
    BILLETERA_VIRTUAL("Billetera Virtual");

    private final String descripcion;

    TipoPago(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
