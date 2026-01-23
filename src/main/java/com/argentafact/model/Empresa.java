package com.argentafact.model;

public class Empresa {
    // TODO definir atributos de la empresa
    private String razonSocial = "EMPRESA XXX";
    private final int puntoVenta = 1;

    public Empresa(){}
    public Empresa(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public int getPuntoVenta() {
        return puntoVenta;
    }

}
