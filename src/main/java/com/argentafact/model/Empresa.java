package com.argentafact.model;

public class Empresa {
    // TODO definir atributos de la empresa
    private String razonSocial;

    public Empresa(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

}
