package com.argentafact.model;

public enum Departamento {
    POSADAS("POSADAS"),
    OBERA("OBERA"),
    IGUAZU("IGUAZU"),
    Eldorado("ELDORADO"),
    GUARANI("GUARANI"),
    SAN_IGNACIO("SAN IGNACIO"),
    CAINGUAS("CAINGUAS"),
    LIBERTADOR_GRAL_SAN_MARTIN("LIBERTADOR GRAL. SAN MARTIN"),
    APOSTOLES("APOSTOLES"),
    LEANDRO_N_ALEM("LEANDRO N. ALEM"),
    GENERAL_MANUEL_BELGRANO("GENERAL MANUEL BELGRANO"),
    MONTECERLO("MONTECERLO"),
    CANDELARIA("CANDELARIA"),
    SAN_PEDRO("SAN PEDRO"),
    _25_DE_MAYO("25 DE MAYO"),
    SAN_JAVIER("SAN JAVIER"),
    CONCEPCION("CONCEPCIÓN");

    private String descripcion;

    Departamento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}