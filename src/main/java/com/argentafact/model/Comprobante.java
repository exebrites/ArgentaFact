package com.argentafact.model;

import java.math.BigDecimal;

public class Comprobante {
    private Long idComprobante;
    // TODO relacionar con factura
    // idFactura
    private BigDecimal subtotal;

    public Comprobante() {
    }

    public Comprobante(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Long getIdComprobante() {
        return idComprobante;
    }

    public void setIdComprobante(Long idComprobante) {
        this.idComprobante = idComprobante;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

}
